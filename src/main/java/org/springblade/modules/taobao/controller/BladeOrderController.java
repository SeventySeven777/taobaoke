package org.springblade.modules.taobao.controller;

import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.entity.BladeOrder;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

import static org.springblade.modules.taobao.config.TaobaoURLConfig.BLADE_ORDER_URL;

/**
 * @author SeventySeven
 * @since 2020-08-14
 */
@Api(tags = "订单表接口管理")
@RestController
@RequestMapping(BLADE_ORDER_URL)
public class BladeOrderController {

	public R<BladeOrder> getBladeOrderList() {
		return null;
	}

}
