package org.springblade.modules.taobao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.exception.SqlException;
import org.springblade.modules.taobao.entity.BladeWallet;
import org.springblade.modules.taobao.mapper.BladeWalletMapper;
import org.springblade.modules.taobao.service.IBladeWalletHistoryService;
import org.springblade.modules.taobao.service.IBladeWalletService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.springblade.modules.taobao.config.MethodConfig.MONEY_ERROR;
import static org.springblade.modules.taobao.config.MethodConfig.SAVE_ERROR;

/**
 * <p>
 * 用户钱包 服务实现类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@Service
@AllArgsConstructor
public class BladeWalletServiceImpl extends ServiceImpl<BladeWalletMapper, BladeWallet> implements IBladeWalletService {
	private final IBladeWalletHistoryService iBladeWalletHistoryService;

	@Override
	public R<String> addMoney(BladeWallet userBladeWallet, BigDecimal moneyChange, Integer reason) {
		userBladeWallet.setMoney(userBladeWallet.getMoney().add(moneyChange));
		userBladeWallet.setHistoryMoneyAll(userBladeWallet.getHistoryMoneyAll().add(moneyChange));
		if (!this.updateById(userBladeWallet)) {
			throw new SqlException(SAVE_ERROR);
		}
		return iBladeWalletHistoryService.addMoneyHistory(userBladeWallet.getId(), moneyChange, reason, userBladeWallet.getMoney());
	}

	@Override
	public R<String> subMoney(BladeWallet userBladeWallet, BigDecimal moneyChange, Integer reason) {
		if (userBladeWallet.getMoney().subtract(moneyChange.abs()).compareTo(BigDecimal.ZERO) < 0) {
			return R.fail(MONEY_ERROR);
		}
		userBladeWallet.setMoney(userBladeWallet.getMoney().subtract(moneyChange.abs()));
		userBladeWallet.setConversionMoneyAll(userBladeWallet.getConversionMoneyAll().add(moneyChange.abs()));
		if (!this.updateById(userBladeWallet)) {
			throw new SqlException(SAVE_ERROR);
		}
		return iBladeWalletHistoryService.subMoneyHistory(userBladeWallet.getId(), moneyChange, reason, userBladeWallet.getMoney());
	}
}
