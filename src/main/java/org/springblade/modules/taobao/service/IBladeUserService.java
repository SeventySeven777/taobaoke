package org.springblade.modules.taobao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.LoginUserDTO;
import org.springblade.modules.taobao.entity.BladeUser;
import org.springblade.modules.taobao.entity.BladeUserBash;
import org.springblade.modules.taobao.entity.BladeUserStore;

/**
 * <p>
 * 平台用户表 服务类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
public interface IBladeUserService extends IService<BladeUser> {

	/**
	 * 用户登录 用户登录返回用户信息+token 管理员登录返回token
	 * @param loginUserDTO
	 * @return
	 */
	R login(LoginUserDTO loginUserDTO);

	/**
	 * 判断用户手机号是否存在
	 * @param phone
	 * @return
	 */
    Boolean examineUserPhone(String phone);

	/**
	 * 初始化用户相关表数据
	 * 包括账号 基础信息 钱包 等
	 * @param bladeUserBash
	 * @return
	 */
	R<BladeUser> initUserAll(BladeUserBash bladeUserBash);

	/**
	 * 判断用户是否存在通过userID
	 * @param userId 用户id
	 * @return
	 */
	Boolean examineUser(String userId);

	/**
	 * 初始化店铺
	 * @param bladeUserStore
	 * @return
	 */
	R<BladeUserStore> initStore(BladeUserStore bladeUserStore);
}
