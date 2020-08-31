package org.springblade.modules.taobao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.entity.BladeWallet;

import java.math.BigDecimal;

/**
 * <p>
 * 用户钱包 服务类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
public interface IBladeWalletService extends IService<BladeWallet> {
	/**
	 * 用户加钱,订单分红
	 *
	 * @param userBladeWallet 用户钱包
	 * @param moneyChange     钱变动数量
	 * @param reason          原因
	 * @return 是否成功
	 */
	public R<String> addMoney(BladeWallet userBladeWallet, BigDecimal moneyChange, Integer reason);

	/**
	 * 用户扣钱,提现
	 *
	 * @param userBladeWallet 用户钱包
	 * @param moneyChange     钱变动数量
	 * @param reason          原因
	 * @return 是否成功
	 */
	public R<String> subMoney(BladeWallet userBladeWallet, BigDecimal moneyChange, Integer reason);


	/**
	 * 用来发钱
	 * @return
	 */
	R<String> subMoneyNew();
}
