package org.springblade.modules.taobao.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.secure.AuthInfo;
import org.springblade.core.tool.api.R;
import org.springblade.modules.auth.utils.TokenUtil;
import org.springblade.modules.exception.SqlException;
import org.springblade.modules.taobao.config.BashNumberInterface;
import org.springblade.modules.taobao.dto.InitStoreDTO;
import org.springblade.modules.taobao.dto.InitUserDTO;
import org.springblade.modules.taobao.dto.LoginUserDTO;
import org.springblade.modules.taobao.entity.*;
import org.springblade.modules.taobao.mapper.*;
import org.springblade.modules.taobao.service.IBladeUserService;
import org.springblade.modules.taobao.utils.DoDecodeAliPayCode;
import org.springblade.modules.taobao.utils.MyRedisUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springblade.modules.taobao.config.BashNumberInterface.*;
import static org.springblade.modules.taobao.config.MethodConfig.*;

/**
 * <p>
 * 平台用户表 服务实现类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@Service
@AllArgsConstructor
public class BladeUserServiceImpl extends ServiceImpl<BladeUserMapper, BladeUser> implements IBladeUserService {
	private final BladeUserBashMapper bladeUserBashMapper;
	private final BashNumberInterface bashNumberInterface;
	private final BladeUserMapper bladeUserMapper;
	private final BladeWalletMapper bladeWalletMapper;
	private final BladeUserStoreMapper bladeUserStoreMapper;
	private final BladeUserCheckMapper bladeUserCheckMapper;
	private final BladeAdminAccountMapper bladeAdminAccountMapper;
	private final RedisTemplate<String, Object> redisTemplate;
	private final MyRedisUtil myRedisUtil;

	/**
	 * 用户登录 用户登录返回用户信息+token 管理员登录返回token
	 *
	 * @param loginUserDTO 登录信息
	 * @return token
	 */
	@Override
	public R<AuthInfo> login(LoginUserDTO loginUserDTO) {
		BladeUser bladeUser = bladeUserMapper.selectOne(Wrappers.<BladeUser>query().lambda()
			.eq(BladeUser::getPhone, loginUserDTO.getPhone()));
		if (null == bladeUser || !bladeUser.getPassword().equals(loginUserDTO.getPassword())) {
			return R.fail(NO_USER_OR_PASSWORD_ERROR);
		}
		String userName;
		BladeUserBash bladeUserBash = bladeUserBashMapper.selectById(bladeUser.getId());
		if (bladeUserBash==null){
			userName = bladeUserStoreMapper.selectById(bladeUser.getId()).getStoreName();
		}else {
			userName=bladeUserBash.getUserName();
		}
		String userRole = bashNumberInterface.getUserRole(bladeUser.getRole());
		AuthInfo authInfo = TokenUtil.createAuthInfo(bladeUser, userName,userRole
			);
		myRedisUtil.set(REDIS_TOKEN + authInfo.getAccessToken(), bladeUser.getId(), 3000L);
		return R.data(authInfo);

	}

	/**
	 * 判断用户手机号是否存在
	 * true 存在  false 不存在
	 *
	 * @param phone 手机
	 * @return 是否存在用户
	 */
	@Override
	public Boolean examineUserPhone(String phone) {
		return null != bladeUserBashMapper.selectOne(new LambdaQueryWrapper<BladeUserBash>().eq(BladeUserBash::getPhone, phone));
	}

	/**
	 * 初始化用户所有基础信息
	 *
	 * @param bladeUserBash 基础用户对象
	 * @return 初始完成的用户对象
	 */
	@Override
	@Transactional(rollbackFor = SqlException.class)
	public R<BladeUser> initUserAll(BladeUserBash bladeUserBash) {
		String userId = UUID.randomUUID().toString();
		//initUserAccount
		BladeUser bladeUser = new BladeUser().setCreateDate(new Date()).setId(userId).setPassword(SecureUtil.md5(bladeUserBash.getPhone()))
			.setPhone(bladeUserBash.getPhone());
		initUser(bladeUser, MANAGER_NUMBER, STATUS_NO_CHECK_NUMBER);
		//initUserBash
		bladeUserBash.setId(userId);
		myRedisUtil.set(REDIS_USER+userId,bladeUser);
		if (bladeUserBashMapper.insert(bladeUserBash) != 1) {
			throw new SqlException(SAVE_ERROR);
		}
		myRedisUtil.set(REDIS_BASH + userId, bladeUserBash);
		//initUserWallet
		BladeWallet bladeWallet = new BladeWallet().setId(userId);
		initUserWallet(bladeWallet, INIT_USER_MONEY);
		//initCheck
		bladeUserCheckMapper.insert(new BladeUserCheck().setUserId(userId).setCreateTime(new Date()));
		return R.data(bladeUser);
	}

	/**
	 * 判断用户ID是否存在 存在 false 不存在 true
	 *
	 * @param userId 用户id
	 * @return isOk
	 */
	@Override
	public Boolean examineUser(String userId) {
		return null == bladeUserMapper.selectById(userId);
	}

	/**
	 * 初始化店铺
	 *
	 * @param bladeUserStore 店铺
	 * @return isOK?
	 */
	@Override
	@Transactional(rollbackFor = SqlException.class)
	public R<BladeUserStore> initStore(BladeUserStore bladeUserStore, String province) {
		String userId = UUID.randomUUID().toString();
		//initUser
		BladeUser bladeUser = new BladeUser().setCreateDate(new Date()).setId(userId).setPassword(SecureUtil.md5(bladeUserStore.getPhone()))
			.setPhone(bladeUserStore.getPhone());
		initUser(bladeUser, STORE_NUMBER, STATUS_OK_CHECK_NO_RATE_NUMBER);
		//initStore
		//format = 0001
		Integer o = myRedisUtil.get(REDIS_STORE_CODE);
		String format = String.format("%05d", o);
		bladeUserStore.setId(userId).setQRCode(INIT_QR_CODE).setStoreCode(province + format);
		myRedisUtil.incr(REDIS_STORE_CODE);
		if (bladeUserStoreMapper.insert(bladeUserStore) != 1) {
			throw new SqlException(SAVE_ERROR);
		}
		//initUserWallet
		BladeWallet bladeWallet = new BladeWallet().setId(userId);
		initUserWallet(bladeWallet, INIT_USER_MONEY);
		return R.data(bladeUserStore);
	}

	@Override
	public Boolean whetherPassword(String password, String passwordOld) {
		return password.equals(passwordOld);
	}

	/**
	 * 修改密码
	 *
	 * @param bladeUser   user
	 * @param passwordNew password
	 * @return isOk?
	 */
	@Override
	@Transactional(rollbackFor = SqlException.class)
	public R<String> setPassword(BladeUser bladeUser, String passwordNew) {
		bladeUser.setPassword(SecureUtil.md5(passwordNew));
		if (bladeUserMapper.updateById(bladeUser) != 1) {
			throw new SqlException(SAVE_ERROR);
		}
		return R.success(SAVE_OK);
	}

	/**
	 * 获取 用户ids
	 *
	 * @param status        审核状态
	 * @param size          分页
	 * @param current       分页
	 * @param managerNumber 查什么role
	 * @return idsList 最后一位为total
	 */
	@Override
	public List<String> getUserIdsByStatus(Integer status, Integer size, Integer current, Integer managerNumber) {
		Page<BladeUser> bladeUserPage = bladeUserMapper.selectPage(new Page<BladeUser>().setSize(size).setCurrent(current),
			Wrappers.<BladeUser>query().lambda().eq(BladeUser::getStatus, status).eq(BladeUser::getRole, managerNumber)
				.orderByDesc(BladeUser::getCreateDate));
		//为了方便将total装入List最后一位 拿到后进行删除即可
		List<String> result = new ArrayList<>();
		bladeUserPage.getRecords().forEach(item -> result.add(item.getId()));
		result.add(String.valueOf(bladeUserPage.getTotal()));
		return result;
	}

	@Override
	public List<String> getUserIdsByStatus(Integer status, Integer size, Integer current, Integer managerNumber, String filter) {
		Page<BladeUser> bladeUserPageOne = bladeUserMapper.selectPage(new Page<BladeUser>().setSize(size).setCurrent(current),
			Wrappers.<BladeUser>query().lambda().eq(BladeUser::getStatus, status).eq(BladeUser::getRole, managerNumber)
				.like(BladeUser::getPhone, "%" + filter + "%").orderByDesc(BladeUser::getCreateDate));
		List<String> result = new ArrayList<>();
		if (null == bladeUserPageOne.getRecords() || bladeUserPageOne.getRecords().size() == 0) {
			List<String> ids = new ArrayList<>();
			Page<BladeUserStore> bladeUserStorePage = bladeUserStoreMapper.selectPage(new Page<BladeUserStore>().setSize(size).setCurrent(current),
				Wrappers.<BladeUserStore>query().lambda().like(BladeUserStore::getStoreHuman, "%" + filter + "%"));
			bladeUserStorePage.getRecords().forEach(item -> ids.add(item.getId()));
			if (ids.size() != 0) {
				bladeUserPageOne = bladeUserMapper.selectPage(new Page<BladeUser>().setSize(size).setCurrent(current),
					Wrappers.<BladeUser>query().lambda().eq(BladeUser::getStatus, status).eq(BladeUser::getRole, managerNumber)
						.in(BladeUser::getId, ids).orderByDesc(BladeUser::getCreateDate));
			}
		}
		//为了方便将total装入List最后一位 拿到后进行删除即可
		bladeUserPageOne.getRecords().forEach(item -> result.add(item.getId()));
		result.add(String.valueOf(bladeUserPageOne.getTotal()));
		return result;
	}

	/**
	 * 修改用户审核状态
	 *
	 * @param userId 用户ID
	 * @param status 审核状态
	 * @return true 修改失败 false 修改成功
	 */
	@Override
	public Boolean updateStatus(String userId, Integer status) {
		BladeUser bladeUser = bladeUserMapper.selectById(userId);
		if (null == bladeUser) {
			return true;
		}
		return bladeUserMapper.updateById(bladeUser.setStatus(status)) != 1;
	}

	@Override
	public List<String> getAllUserIds(Integer size, Integer current, Integer role, Integer status) {
		Page<BladeUser> bladeUserPage = bladeUserMapper.selectPage(new Page<BladeUser>().setSize(size).setCurrent(current),
			Wrappers.<BladeUser>query().lambda().eq(BladeUser::getRole, role).eq(BladeUser::getStatus, status));
		List<String> result = new ArrayList<>();
		bladeUserPage.getRecords().forEach(item -> result.add(item.getId()));
		result.add(String.valueOf(bladeUserPage.getTotal()));
		return result;
	}

	@Override
	public R<Page<BladeUser>> getManagerPage(Integer size, Integer current) {
		return R.data(bladeUserMapper.selectPage(new Page<BladeUser>().setCurrent(current).setSize(size),
			Wrappers.<BladeUser>query().lambda().eq(BladeUser::getRole, MANAGER_NUMBER).eq(BladeUser::getStatus, STATUS_OK_CHECK_NUMBER)));
	}

	@Override
	public R<AuthInfo> loginAdmin(LoginUserDTO loginUserDTO) {
		BladeAdminAccount bladeAdminAccount = bladeAdminAccountMapper.selectById(loginUserDTO.getPhone());
		if (null == bladeAdminAccount || !bladeAdminAccount.getPassword().equals(loginUserDTO.getPassword())) {
			return R.fail(NO_USER_OR_PASSWORD_ERROR);
		}
		AuthInfo authInfo = TokenUtil.createAuthInfo(bladeAdminAccount);
		myRedisUtil.set(REDIS_TOKEN + authInfo.getAccessToken(), bladeAdminAccount.getId(), 30L);
		return R.data(authInfo);
	}

	@Override
	public BladeUserStore deCode(InitStoreDTO initStoreDTO) {
		return new BladeUserStore().setStoreName(DoDecodeAliPayCode.deCode(initStoreDTO.getStoreName()))
			.setPhone(DoDecodeAliPayCode.deCode(initStoreDTO.getPhone()))
			.setPayNumber(DoDecodeAliPayCode.deCode(initStoreDTO.getPayNumber()))
			.setLongitude(DoDecodeAliPayCode.deCode(initStoreDTO.getLongitude()))
			.setLatitude(DoDecodeAliPayCode.deCode(initStoreDTO.getLatitude()))
			.setStoreHuman(DoDecodeAliPayCode.deCode(initStoreDTO.getStoreHuman()))
			.setAddress(DoDecodeAliPayCode.deCode(initStoreDTO.getAddress()))
			.setImage(DoDecodeAliPayCode.deCode(initStoreDTO.getImage()))
			.setProvince(DoDecodeAliPayCode.deCode(initStoreDTO.getProvince()));
	}

	@Override
	public BladeUserBash deCode(InitUserDTO initUserDTO) {
		return new BladeUserBash().setAddress(DoDecodeAliPayCode.deCode(initUserDTO.getAddress()))
			.setAlipay(DoDecodeAliPayCode.deCode(initUserDTO.getAlipay()))
			.setEducation(DoDecodeAliPayCode.deCode(initUserDTO.getEducation()))
			.setEducationImage(DoDecodeAliPayCode.deCode(initUserDTO.getEducationImage()))
			.setPhone(DoDecodeAliPayCode.deCode(initUserDTO.getPhone()))
			.setIdentityImageFront(DoDecodeAliPayCode.deCode(initUserDTO.getIdentityImageFront()))
			.setIdentityImageVerso(DoDecodeAliPayCode.deCode(initUserDTO.getIdentityImageVerso()))
			.setIndividualResume(DoDecodeAliPayCode.deCode(initUserDTO.getIndividualResume()))
			.setResumeUrl(DoDecodeAliPayCode.deCode(initUserDTO.getResumeUrl()))
			.setSchool(DoDecodeAliPayCode.deCode(initUserDTO.getSchool()))
			.setUserAge(initUserDTO.getUserAge())
			.setUserName(DoDecodeAliPayCode.deCode(initUserDTO.getUserName()))
			.setUserSex(initUserDTO.getUserSex())
			.setWorkYear(DoDecodeAliPayCode.deCode(initUserDTO.getWorkYear()));
	}


	/**
	 * initUser
	 *
	 * @param bladeUser user
	 * @param role      角色
	 * @param status    审核状态
	 */
	private void initUser(BladeUser bladeUser, Integer role, Integer status) {
		bladeUser.setRole(role).setStatus(status);
		if (bladeUserMapper.insert(bladeUser) != 1) {
			throw new SqlException(SAVE_ERROR);
		}
		myRedisUtil.set(REDIS_USER + bladeUser.getId(), bladeUser);
	}

	/**
	 * initWallet
	 *
	 * @param bladeWallet 钱包
	 * @param money       初始钱
	 */
	private void initUserWallet(BladeWallet bladeWallet, BigDecimal money) {
		bladeWallet.setMoney(money).setHistoryMoneyAll(money).setConversionMoneyAll(money);
		if (bladeWalletMapper.insert(bladeWallet) != 1) {
			throw new SqlException(SAVE_ERROR);
		}

	}
}
