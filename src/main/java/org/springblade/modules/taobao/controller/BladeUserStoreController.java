package org.springblade.modules.taobao.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.UpdateManagerDTO;
import org.springblade.modules.taobao.entity.BladeUserStore;
import org.springblade.modules.taobao.service.*;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

import javax.validation.constraints.NotNull;

import java.util.List;

import static org.springblade.modules.taobao.config.BashNumberInterface.STORE_NUMBER;
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
	private final IBladeUserStoreService iBladeUserStoreService;
	private final IBladeUserService iBladeUserService;
	private final IBladeUserBashService iBladeUserBashService;

	/**
	 * 查看店铺信息
	 *
	 * @param storeId 注册DTO
	 * @return BladeUserStore
	 */
	@RequestMapping(value = GET_STORE_INFO, method = RequestMethod.GET)
	@ApiOperation(value = "查看店铺信息", notes = "查看店铺信息")
	public R<BladeUserStore> getStoreInfo(@RequestParam("store-id") @NotNull String storeId) {
		return R.data(iBladeUserStoreService.getById(storeId));
	}

	/**
	 * 通过状态查看店铺
	 *
	 * @param size    分页
	 * @param current 分页
	 * @param status  状态
	 * @return result
	 */
	@RequestMapping(value = GET_STORE_PAGE, method = RequestMethod.GET)
	@ApiOperation(value = "通过状态查看店铺信息", notes = "查看店铺信息")
	@ApiImplicitParam(name = "status", value = "状态码 1 已配置抽成,2 未配置抽成")
	public R<Object> getStorePageByStatus(@RequestParam("size") @NotNull Integer size,
										  @RequestParam("current") @NotNull Integer current,
										  @RequestParam("status") @NotNull Integer status,
										  @RequestParam("filter")  String filter) {
		List<String> userIdsByStatus = iBladeUserService.getUserIdsByStatus(status, size, current, STORE_NUMBER, filter);
		//todo
		return iBladeUserBashService.getUserByIds(userIdsByStatus, size, current, STORE_NUMBER);
	}

	/**
	 * 修改负责人
	 *
	 * @param updateManagerDTO 1
	 * @return 成功
	 */
	@RequestMapping(value = PUT_STORE_MANAGER, method = RequestMethod.PUT)
	@ApiOperation(value = "改负责人改body、实际为修改负责人及支付宝", notes = "查看店铺信息")
	public R updateManager(@RequestBody UpdateManagerDTO updateManagerDTO) {
		return R.data(iBladeUserStoreService.updateById(iBladeUserStoreService.getById(updateManagerDTO.getStoreId())
			.setStoreHuman(updateManagerDTO.getStoreHuman()).setPayNumber(updateManagerDTO.getPayNumber())));
	}


	/**
	 * 删除门店
	 *
	 * @param storeId 门店ID
	 * @return 成功
	 */
	@RequestMapping(value = DELETE_STORE_MANAGER, method = RequestMethod.DELETE)
	@ApiOperation(value = "删除门店", notes = "查看店铺信息")
	public R<String> deleteStore(@RequestParam("store-id") @NotNull String storeId) {
		return iBladeUserStoreService.deleteStore(storeId);
	}



}
