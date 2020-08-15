package org.springblade.modules.taobao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.taobao.entity.BladeRate;

import java.math.BigDecimal;

/**
 * <p>
 * 比率表 服务类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
public interface IBladeRateService extends IService<BladeRate> {

	/**
	 * 修改区域经理分红
	 *
	 * @param userId 用户ID
	 * @param rate   分红
	 */
	void updateManagerRate(String userId, BigDecimal rate);
}
