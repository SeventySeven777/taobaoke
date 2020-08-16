package org.springblade.modules.taobao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.tool.api.R;
import org.springblade.modules.exception.SqlException;
import org.springblade.modules.taobao.entity.BladeWalletHistory;
import org.springblade.modules.taobao.mapper.BladeWalletHistoryMapper;
import org.springblade.modules.taobao.service.IBladeWalletHistoryService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

import static org.springblade.modules.taobao.config.BashNumberInterface.MONEY_STATUS_NO;
import static org.springblade.modules.taobao.config.BashNumberInterface.MONEY_STATUS_OK;
import static org.springblade.modules.taobao.config.MethodConfig.SAVE_ERROR;
import static org.springblade.modules.taobao.config.MethodConfig.SAVE_OK;

/**
 * <p>
 * 钱变动历史表 服务实现类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@Service
public class BladeWalletHistoryServiceImpl extends ServiceImpl<BladeWalletHistoryMapper, BladeWalletHistory> implements IBladeWalletHistoryService {

	@Override
	public R<String> addMoneyHistory(String userId, BigDecimal moneyChange, Integer reason, BigDecimal walletMoney) {
		BladeWalletHistory bladeWalletHistory = new BladeWalletHistory().setCreateTime(new Date()).setUserId(userId).setMoneyChange(moneyChange)
			.setThenMoney(walletMoney).setReason(reason).setStatus(MONEY_STATUS_OK);
		if (!this.save(bladeWalletHistory)) {
			throw new SqlException(SAVE_ERROR);
		}
		return R.success(SAVE_OK);
	}

	@Override
	public R<String> subMoneyHistory(String userId, BigDecimal moneyChange, Integer reason, BigDecimal walletMoney) {
		BladeWalletHistory bladeWalletHistory = new BladeWalletHistory().setStatus(MONEY_STATUS_NO).setReason(reason).setUserId(userId)
			.setThenMoney(walletMoney).setMoneyChange(moneyChange).setCreateTime(new Date());
		if (!this.save(bladeWalletHistory)) {
			throw new SqlException(SAVE_ERROR);
		}
		return R.success(SAVE_OK);
	}
}
