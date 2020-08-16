package org.springblade.modules.taobao.controller;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.StoreAllVO;
import org.springblade.modules.taobao.dto.StoreVO;
import org.springblade.modules.taobao.entity.BladeUser;
import org.springblade.modules.taobao.entity.BladeUserStore;
import org.springblade.modules.taobao.service.IBladeRateService;
import org.springblade.modules.taobao.service.IBladeStoreUserMiddleService;
import org.springblade.modules.taobao.service.IBladeUserService;
import org.springblade.modules.taobao.service.IBladeUserStoreService;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
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
	private final IBladeStoreUserMiddleService iBladeStoreUserMiddleService;
	private final IBladeRateService iBladeRateService;

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
	public R<StoreAllVO> getStorePageByStatus(@RequestParam("size") @NotNull Integer size,
											  @RequestParam("current") @NotNull Integer current,
											  @RequestParam("status") @NotNull Integer status) {
		List<String> userIdsByStatus = iBladeUserService.getUserIdsByStatus(status, size, current, STORE_NUMBER);
		StoreAllVO storeAllVO = new StoreAllVO();
		List<StoreVO> list = new ArrayList<>();
		userIdsByStatus.forEach(item -> {
			BladeUser bladeUser = iBladeUserService.getById(item);
			BladeUserStore bladeUserStore = iBladeUserStoreService.getById(item);
			String manageName = iBladeStoreUserMiddleService.getManageName(item);
			BigDecimal managerRate = iBladeRateService.getManagerRate(item);
			StoreVO storeVO = new StoreVO();
			BeanUtil.copyProperties(storeVO, bladeUserStore);
			storeVO.setManagerName(manageName).setAccount(bladeUser.getPhone()).setRate(managerRate);
			list.add(storeVO);
		});
		return R.data(storeAllVO.setList(list).setTotal(Long.valueOf(userIdsByStatus.get(userIdsByStatus.size() - 1))).setSize(size).setCurrent(current));
	}

	/**
	 * 修改负责人
	 *
	 * @param userId  1
	 * @param storeId 1
	 * @return 成功
	 */
	@RequestMapping(value = PUT_STORE_MANAGER, method = RequestMethod.PUT)
	@ApiOperation(value = "修改负责人", notes = "查看店铺信息")
	public R<String> updateManager(@RequestParam("manager-id") @NotNull String userId,
								   @RequestParam("store-id") @NotNull String storeId) {
		return iBladeStoreUserMiddleService.updateManager(userId, storeId);
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
