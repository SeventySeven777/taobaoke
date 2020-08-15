package org.springblade.modules.taobao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.modules.exception.SqlException;
import org.springblade.modules.taobao.entity.BladeRate;
import org.springblade.modules.taobao.mapper.BladeRateMapper;
import org.springblade.modules.taobao.service.IBladeRateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springblade.modules.taobao.config.MethodConfig.SAVE_ERROR;

/**
 * <p>
 * 比率表 服务实现类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@Service
@AllArgsConstructor
public class BladeRateServiceImpl extends ServiceImpl<BladeRateMapper, BladeRate> implements IBladeRateService {
	private BladeRateMapper bladeRateMapper;

	/**
	 * 修改用户分红 老逻辑先删在存
	 *
	 * @param userId 用户ID
	 * @param rate   分红
	 */
	@Override
	@Transactional(rollbackFor = SqlException.class)
	public void updateManagerRate(String userId, BigDecimal rate) {
		bladeRateMapper.delete(Wrappers.<BladeRate>query().lambda().eq(BladeRate::getUserId, rate));
		if (bladeRateMapper.insert(new BladeRate().setRate(rate).setUserId(userId)) != 1) {
			throw new SqlException(SAVE_ERROR);
		}
	}
}
