package org.springblade.modules.taobao.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author SeventySeven
 * @since 2020-08-18
 */
@Configuration
@EnableScheduling
public class SaticScheduleTask {

	@Scheduled(cron = "0 0 5 * * ? ")
	private void configureTasks() {
		SaveToken.deleteTokenMap();
	}
}
