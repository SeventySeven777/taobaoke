package org.springblade.modules.taobao.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.service.IBladeRateService;
import org.springblade.modules.taobao.service.IBladeUserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

import static org.springblade.modules.taobao.config.BashNumberInterface.STATUS_OK_CHECK_NUMBER;
import static org.springblade.modules.taobao.config.MethodConfig.SAVE_OK;
import static org.springblade.modules.taobao.config.TaobaoURLConfig.*;

/**
 * @author SeventySeven
 * @since 2020-08-14
 */
@Api(tags = "比率表接口管理")
@RestController
@RequestMapping(BLADE_RATE_URL)
@AllArgsConstructor
public class BladeRateController {
	private final IBladeRateService iBladeRateService;
	private final IBladeUserService iBladeUserService;

	/**
	 * 修改区所有分红比率
	 *
	 * @param map 用户KV
	 * @return 都返回成功
	 */
	@RequestMapping(value = UPDATE_MANAGER_RATE, method = RequestMethod.POST)
	@ApiOperation(value = "修改所有分红", notes = "修改分红")
	@ApiImplicitParam(name = "map", value = "key为userId,value放分红比率不得超过100", dataType = "String", required = true)
	public R<String> initUserManagerRate(@RequestBody Map<String, String> map) {
		map.forEach((k, v) -> {
			BigDecimal rate = new BigDecimal(v);
			iBladeRateService.updateManagerRate(k, rate);
			//此处修改userStatus为完全审核通过由于为判断K是否存在用户故不在使用下方的return
			iBladeUserService.updateStatus(k, STATUS_OK_CHECK_NUMBER);
		});
		return R.success(SAVE_OK);
	}

}
