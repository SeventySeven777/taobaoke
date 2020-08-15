package org.springblade.modules.taobao.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.exception.SqlException;
import org.springblade.modules.taobao.config.BashNumberInterface;
import org.springblade.modules.taobao.dto.LoginUserDTO;
import org.springblade.modules.taobao.entity.BladeUser;
import org.springblade.modules.taobao.entity.BladeUserBash;
import org.springblade.modules.taobao.entity.BladeUserStore;
import org.springblade.modules.taobao.entity.BladeWallet;
import org.springblade.modules.taobao.mapper.BladeUserBashMapper;
import org.springblade.modules.taobao.mapper.BladeUserMapper;
import org.springblade.modules.taobao.mapper.BladeUserStoreMapper;
import org.springblade.modules.taobao.mapper.BladeWalletMapper;
import org.springblade.modules.taobao.service.IBladeUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	/**
	 * 用户登录 用户登录返回用户信息+token 管理员登录返回token
	 *
	 * @param loginUserDTO 登录信息
	 * @return token
	 */
	@Override
	public R login(LoginUserDTO loginUserDTO) {

		return null;
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
		if (bladeUserBashMapper.insert(bladeUserBash) != 1) {
			throw new SqlException(SAVE_ERROR);
		}
		//initUserWallet
		BladeWallet bladeWallet = new BladeWallet().setId(userId);
		initUserWallet(bladeWallet, INIT_USER_MONEY);
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
	public R<BladeUserStore> initStore(BladeUserStore bladeUserStore) {
		String userId = UUID.randomUUID().toString();
		//initUser
		BladeUser bladeUser = new BladeUser().setCreateDate(new Date()).setId(userId).setPassword(SecureUtil.md5(bladeUserStore.getPhone()))
			.setPhone(bladeUserStore.getPhone());
		initUser(bladeUser, STORE_NUMBER, STATUS_OK_CHECK_NO_RATE_NUMBER);
		//initStore
		bladeUserStore.setId(userId).setQRCode(INIT_QR_CODE);
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
	public List<String> getAllUserIds(Integer size, Integer current) {
		Page<BladeUser> bladeUserPage = bladeUserMapper.selectPage(new Page<BladeUser>().setSize(size).setCurrent(current),
			Wrappers.<BladeUser>query().lambda().isNotNull(BladeUser::getId));
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
