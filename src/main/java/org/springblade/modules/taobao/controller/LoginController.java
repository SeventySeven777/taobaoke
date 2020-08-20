package org.springblade.modules.taobao.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.secure.AuthInfo;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.InitUserDTO;
import org.springblade.modules.taobao.dto.LoginUserDTO;
import org.springblade.modules.taobao.entity.BladeUser;
import org.springblade.modules.taobao.entity.BladeUserBash;
import org.springblade.modules.taobao.service.IBladeAdminAccountService;
import org.springblade.modules.taobao.service.IBladeUserService;
import org.springblade.modules.taobao.utils.CheckObjAllFieldsIsNullUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springblade.modules.taobao.config.BashNumberInterface.ADMIN_ID;
import static org.springblade.modules.taobao.config.BashNumberInterface.MANAGER_NUMBER;
import static org.springblade.modules.taobao.config.MethodConfig.*;
import static org.springblade.modules.taobao.config.TaobaoURLConfig.*;

/**
 * 登录页面controller
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@RestController
@RequestMapping(BLADE_LOGIN_URL)
@AllArgsConstructor
@Api(value = "登录", tags = "登录")
public class LoginController {
	private final IBladeUserService iBladeUserService;
	private final IBladeAdminAccountService iBladeAdminAccountService;

	/**
	 * 用户登录 用户登录返回用户信息+token 管理员登录返回token
	 *
	 * @param loginUserDTO 登录数据
	 * @return token包
	 */
	@RequestMapping(value = LOGIN_USER, method = RequestMethod.POST)
	@ApiOperation(value = "任何角色登录", notes = "登录")
	public R<AuthInfo> loginTaoBao(@RequestBody LoginUserDTO loginUserDTO) {
		if (CheckObjAllFieldsIsNullUtils.checkObjAllFieldsIsNull(loginUserDTO)) {
			return R.fail(FIELD_MISSING);
		}
		return iBladeUserService.login(loginUserDTO);
	}

	/**
	 * admin登录 返回token
	 *
	 * @param loginUserDTO 登录数据
	 * @return token包
	 */
	@RequestMapping(value = LOGIN_ADMIN, method = RequestMethod.POST)
	@ApiOperation(value = "admin登录admin,admin", notes = "登录")
	public R<AuthInfo> loginTaoBaoAdmin(@RequestBody LoginUserDTO loginUserDTO) {
		if (CheckObjAllFieldsIsNullUtils.checkObjAllFieldsIsNull(loginUserDTO)) {
			return R.fail(FIELD_MISSING);
		}
		return iBladeUserService.loginAdmin(loginUserDTO);
	}

	/**
	 * 用户注册,如果手机号重复将不能注册
	 *
	 * @param initUserDTO 注册DTO
	 * @return isOk
	 */
	@RequestMapping(value = INIT_USER, method = RequestMethod.POST)
	@ApiOperation(value = "经理注册", notes = "登录")
	public R initUser(@RequestBody InitUserDTO initUserDTO) {
		if (iBladeUserService.examineUserPhone(initUserDTO.getPhone())) {
			//判断手机号是否注册
			return R.fail(USER_PHONE_OR_ACCOUNT_REPETITION);
		}
		BladeUserBash bladeUserBash = iBladeUserService.deCode(initUserDTO);
		//BeanUtil.copyProperties(initUserDTO, bladeUserBash);
		R<BladeUser> bladeUserR = iBladeUserService.initUserAll(bladeUserBash);
		//初始user完成
		if (!bladeUserR.isSuccess()) {
			return R.fail(USER_INIT_ERROR);
		}
		return iBladeUserService.login(new LoginUserDTO().setPhone(initUserDTO.getPhone()).setPassword(SecureUtil.md5(initUserDTO.getPhone())));
		//return R.success(USER_INIT_OK);
	}

	/**
	 * 获取主页图片
	 *
	 * @return 主页图片
	 */
	@RequestMapping(value = GET_IMAGES, method = RequestMethod.GET)
	@ApiOperation(value = "获取设置主页图片", notes = "登录")
	public R<String> getHomeImage() {
		return R.data(iBladeAdminAccountService.getById(ADMIN_ID).getHomeImage());
	}


}
