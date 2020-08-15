package org.springblade.modules.taobao.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.InitUserDTO;
import org.springblade.modules.taobao.service.IBladeUserStoreService;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

import javax.validation.constraints.NotNull;

import static org.springblade.modules.taobao.config.TaobaoURLConfig.*;

/**
 * @author SeventySeven
 * @since 2020-08-14
 */
@Api(tags = "店铺详细表接口管理")
@RestController
@RequestMapping(BLADE_USER_STORE_URL)
@AllArgsConstructor
public class BladeUserStoreController {
	private IBladeUserStoreService iBladeUserStoreService;

	/**
	 * 用户注册,如果手机号重复将不能注册
	 *
	 * @param storeId 注册DTO
	 * @return
	 */
	@RequestMapping(value = GET_STORE_INFO, method = RequestMethod.GET)
	@ApiOperation(value = "查看店铺信息", notes = "查看店铺信息")
	public R getStoreInfo(@RequestParam("store-id") @NotNull String storeId) {
		return R.data(iBladeUserStoreService.getById(storeId));
	}



}
