package org.springblade.modules.taobao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.BladeOrderAliDTO;
import org.springblade.modules.taobao.dto.BladeOrderPageVO;
import org.springblade.modules.taobao.entity.BladeOrder;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
public interface IBladeOrderService extends IService<BladeOrder> {
	/**
	 * 通过ids进行查询前面将数据查好ids再使用
	 *
	 * @param orderIds 需要查询的订单ids 不是aliOrderId 本地id
	 * @param size     1
	 * @param current  1
	 * @param total    1
	 * @return 返回分页对象
	 */
	R<BladeOrderPageVO> getOrderPageInfo(List<String> orderIds, Integer size, Integer current, Long total);

	/**
	 * 修改订单状态 比如拉取完订单后进行自动调用操作
	 * 本method会自动调用分红接口进行订单分红 如果status等于完成的话 暂定 0 下单中 1 订单完成 -1 退货了
	 *
	 * @param orderId 订单id
	 * @param status  status
	 * @return 是否成功
	 */
	R<String> updateOrderStatus(String orderId, Integer status);

	/**
	 * 新增订单，拉取后调用
	 *
	 * @param bladeOrder 订单新
	 * @return isOk
	 */
	R insertOrder(BladeOrder bladeOrder);

	/**
	 * 将 阿里拉的数据转成 bladeOrder返回
	 *
	 * @param bladeOrderAliDTO ali订单数据
	 * @return bladeOrder
	 */
	R<BladeOrder> conversionBladeOrder(BladeOrderAliDTO bladeOrderAliDTO);

	/**
	 * 用于定时任务 每天定时将 pastTime到期的 订单提出来
	 *
	 * @return 需要分账的ids
	 */
	R<List<String>> checkOrderPastTime();

	/**
	 * 接受checkOrderPastTime();返回的订单进行分账
	 *
	 * @param orderIds 待分账订单
	 * @return isok
	 */
	R<String> rateMoneyToManagerAndStore(List<String> orderIds);

}
