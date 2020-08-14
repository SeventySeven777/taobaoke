package org.springblade.modules.taobao.controller;

import io.swagger.annotations.ApiOperation;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springblade.modules.taobao.config.TaobaoURLConfig.BLADE_USER_URL;
import static org.springblade.modules.taobao.config.TaobaoURLConfig.INIT_USER;

/**
* @author SeventySeven
* @since 2020-08-14
*/
@Api(tags = "平台用户表接口管理")
@RestController
@RequestMapping(BLADE_USER_URL)
    public class BladeUserController {

	/**
	 * 用户注册,如果手机号重复将不能注册
	 * @param r
	 * @return
	 */
	@RequestMapping(value = INIT_USER,method = RequestMethod.POST)
	@ApiOperation(value = "用户或管理员登录", notes = "登录")
	public R initUser(@RequestBody R r){
		return null;
	}
}
