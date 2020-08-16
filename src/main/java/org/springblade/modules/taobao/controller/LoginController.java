package org.springblade.modules.taobao.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.secure.AuthInfo;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.LoginUserDTO;
import org.springblade.modules.taobao.service.IBladeUserService;
import org.springblade.modules.taobao.utils.CheckObjAllFieldsIsNullUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springblade.modules.taobao.config.MethodConfig.FIELD_MISSING;
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


}
