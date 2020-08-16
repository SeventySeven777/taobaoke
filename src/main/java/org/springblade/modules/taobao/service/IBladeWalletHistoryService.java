package org.springblade.modules.taobao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.entity.BladeWalletHistory;

import java.math.BigDecimal;

/**
 * <p>
 * 钱变动历史表 服务类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
public interface IBladeWalletHistoryService extends IService<BladeWalletHistory> {
	/**
	 * 添加增加钱history
	 *
	 * @param userId      userID
	 * @param moneyChange 钱改变数量
	 * @param reason      原因
	 * @param walletMoney 钱包当前金额
	 * @return 是否成功
	 */
	R<String> addMoneyHistory(String userId, BigDecimal moneyChange, Integer reason, BigDecimal walletMoney);

	/**
	 * 扣钱history
	 *
	 * @param userId      userID
	 * @param moneyChange 钱改变数量
	 * @param reason      原因
	 * @param walletMoney 钱包当前金额
	 * @return 是否成功
	 */
	R<String> subMoneyHistory(String userId, BigDecimal moneyChange, Integer reason, BigDecimal walletMoney);

}
