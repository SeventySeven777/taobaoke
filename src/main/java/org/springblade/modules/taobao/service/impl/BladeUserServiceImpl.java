package org.springblade.modules.taobao.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import java.util.Date;

import static org.springblade.modules.taobao.config.BashNumberInterface.*;
import static org.springblade.modules.taobao.config.MethodConfig.SAVE_ERROR;

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
	private BladeUserBashMapper bladeUserBashMapper;
	private BashNumberInterface bashNumberInterface;
	private BladeUserMapper bladeUserMapper;
	private BladeWalletMapper bladeWalletMapper;
	private BladeUserStoreMapper bladeUserStoreMapper;

	/**
	 * 用户登录 用户登录返回用户信息+token 管理员登录返回token
	 *
	 * @param loginUserDTO
	 * @return
	 */
	@Override
	public R login(LoginUserDTO loginUserDTO) {

		return null;
	}

	/**
	 * 判断用户手机号是否存在
	 * true 存在  false 不存在
	 *
	 * @param phone
	 * @return
	 */
	@Override
	public Boolean examineUserPhone(String phone) {
		if (null != bladeUserBashMapper.selectOne(new LambdaQueryWrapper<BladeUserBash>().eq(BladeUserBash::getPhone, phone))) {
			return true;
		}
		return false;
	}

	/**
	 * 初始化用户所有基础信息
	 *
	 * @param bladeUserBash
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
	 * @return
	 */
	@Override
	public Boolean examineUser(String userId) {
		if (null != bladeUserMapper.selectById(userId)) {
			return false;
		}
		return true;
	}

	/**
	 * 初始化店铺
	 *
	 * @param bladeUserStore
	 * @return
	 */
	@Override
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

	/**
	 * initUser
	 *
	 * @param bladeUser
	 * @param role
	 * @param status
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
	 * @param bladeWallet
	 * @param money
	 */
	private void initUserWallet(BladeWallet bladeWallet, BigDecimal money) {
		bladeWallet.setMoney(money).setHistoryMoneyAll(money).setConversionMoneyAll(money);
		if (bladeWalletMapper.insert(bladeWallet) != 1) {
			throw new SqlException(SAVE_ERROR);
		}

	}
}
