package org.springblade.modules.taobao.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.BladeOrderPageVO;
import org.springblade.modules.taobao.dto.BladeStoreRatePageVO;
import org.springblade.modules.taobao.entity.BladeOrder;
import org.springblade.modules.taobao.service.IBladeOrderService;
import org.springblade.modules.taobao.service.IBladeUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springblade.modules.taobao.config.BashNumberInterface.ZERO_NUMBER;
import static org.springblade.modules.taobao.config.TaobaoURLConfig.*;

/**
 * @author SeventySeven
 * @since 2020-08-14
 */
@Api(tags = "订单表接口管理")
@RestController
@RequestMapping(BLADE_ORDER_URL)
@AllArgsConstructor
public class BladeOrderController {
	private final IBladeOrderService iBladeOrderService;
	private final IBladeUserService iBladeUserService;


	/**
	 * 获取订单分页详情 by status  orderBy createTime
	 *
	 * @param status  order status
	 * @param size    size
	 * @param current current
	 * @param orderId orderId
	 * @param filter  时间条件
	 * @return 分页对象
	 */
	@RequestMapping(value = GET_ORDER_PAGE_INFO, method = RequestMethod.GET)
	@ApiOperation(value = "获取订单分页详情", notes = "订单表接口管理")
	@ApiImplicitParams({
		@ApiImplicitParam(value = "完成状态", name = "status", required = true),
		@ApiImplicitParam(value = "size", name = "size", required = true),
		@ApiImplicitParam(value = "current", name = "current", required = true),
	})
	public R<BladeOrderPageVO> getBladeOrderList(@RequestParam("status") Integer status,
												 @RequestParam("size") Integer size,
												 @RequestParam("current") Integer current,
												 @RequestParam(value = "store_id", defaultValue = "", required = false) String storeId,
												 @RequestParam(value = "order_id", defaultValue = "", required = false) String orderId,
												 @RequestParam(value = "filter", defaultValue = "", required = false) String filter) {
		LambdaQueryWrapper<BladeOrder> wrappers = Wrappers.<BladeOrder>query().lambda().eq(BladeOrder::getStatus, status).orderByDesc(BladeOrder::getCreateTime);
		if (!StrUtil.isEmpty(orderId)) {
			wrappers = Wrappers.<BladeOrder>query().lambda().eq(BladeOrder::getOrderAliId, orderId)
				.eq(BladeOrder::getStatus, status).orderByDesc(BladeOrder::getCreateTime);
		}
		if (!StrUtil.isEmpty(filter) && !StrUtil.isEmpty(storeId)) {
			wrappers = Wrappers.<BladeOrder>query().lambda().between(BladeOrder::getCreateTime,
				DateUtil.beginOfMonth(new Date(Long.valueOf(filter))),
				DateUtil.endOfMonth(new Date(Long.valueOf(filter)))).orderByDesc(BladeOrder::getCreateTime);
		}
		if (!StrUtil.isEmpty(storeId) && StrUtil.isEmpty(filter)) {
			wrappers = Wrappers.<BladeOrder>query().lambda().eq(BladeOrder::getStoreId, storeId).orderByDesc(BladeOrder::getCreateTime);
		}
		List<String> list = new ArrayList<>();
		Page<BladeOrder> page = iBladeOrderService.page(new Page<BladeOrder>().setCurrent(current).setSize(size), wrappers);
		page.getRecords().forEach(item -> list.add(item.getId()));
		return iBladeOrderService.getOrderPageInfo(list, size, current, page.getTotal());
	}

	@RequestMapping(value = GET_STORE_PAGE_RATE, method = RequestMethod.GET)
	@ApiOperation(value = "小程序使用获取STORE列表(没分页别传了)", notes = "订单表接口管理")
	public R<BladeStoreRatePageVO> getStoreList(@RequestParam(value = "manager-id", defaultValue = "", required = true) String managerId) {

		return null;
	}

	/**
	 * @RequestParam(value = "size", defaultValue = "0", required = false) Integer size,
	 * @RequestParam(value = "current", defaultValue = "0", required = false) Integer current,
	 */
}
