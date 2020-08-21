package org.springblade.modules.taobao.utils;

import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.service.IBladeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * @author SeventySeven
 * @since 2020-08-18
 */
@Configuration
@EnableScheduling
public class SaticScheduleTask {
	@Autowired
	private IBladeOrderService iBladeOrderService;

	@Scheduled(cron = "0 0 5 * * ? ")
	private void configureTasks() {
		SaveToken.deleteTokenMap();
	}

	@Scheduled(cron = "0 0 5 * * ? ")
	private void cloneOrder() {
		//todo 定时拉订单
	}

	@Scheduled(cron = "0 0 5 * * ? ")
	private void updateOrder() {
		//todo  定时更改订单 定时分账
		R<List<String>> listR = iBladeOrderService.checkOrderPastTime();
		iBladeOrderService.rateMoneyToManagerAndStore(listR.getData());
	}
}
