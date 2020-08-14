package org.springblade.modules.taobao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.LoginUserDTO;
import org.springblade.modules.taobao.entity.BladeUser;

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
}
