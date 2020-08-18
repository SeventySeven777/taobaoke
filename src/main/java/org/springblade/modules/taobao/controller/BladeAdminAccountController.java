package org.springblade.modules.taobao.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.UploadHomeImageDTO;
import org.springblade.modules.taobao.entity.BladeAdminAccount;
import org.springblade.modules.taobao.service.IBladeAdminAccountService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springblade.modules.taobao.config.BashNumberInterface.ADMIN_ID;
import static org.springblade.modules.taobao.config.MethodConfig.SAVE_OK;
import static org.springblade.modules.taobao.config.TaobaoURLConfig.*;

/**
 * @author SeventySeven
 * @since 2020-08-14
 */
@Api(tags = "平台帐号表接口管理")
@RestController
@RequestMapping(BLADE_ADMIN_ACCOUNT_URL)
@AllArgsConstructor
public class BladeAdminAccountController {
	private IBladeAdminAccountService iBladeAdminAccountService;

	/**
	 * 设置主页图片
	 *
	 * @param uploadHomeImage 主页图片
	 * @return ok
	 */
	@RequestMapping(value = SET_IMAGES, method = RequestMethod.POST)
	@ApiOperation(value = "设置主页图片", notes = "平台帐号表接口管理")
	public R<String> setHomeImageOne(@RequestBody UploadHomeImageDTO uploadHomeImage) {
		BladeAdminAccount bladeAdminAccount = iBladeAdminAccountService.getById(ADMIN_ID);
		bladeAdminAccount.setHomeImage(uploadHomeImage.getHomeImage().toString());
		iBladeAdminAccountService.updateById(bladeAdminAccount);
		return R.success(SAVE_OK);
	}

	/**
	 * 获取主页图片
	 *
	 * @return 主页图片
	 */
	@RequestMapping(value = GET_IMAGES, method = RequestMethod.GET)
	@ApiOperation(value = "获取设置主页图片", notes = "平台工作人员基础信息表接口管理")
	public R<String> getHomeImage() {
		return R.data(iBladeAdminAccountService.getById(ADMIN_ID).getHomeImage());
	}

}
