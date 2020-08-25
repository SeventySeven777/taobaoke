package org.springblade.modules.taobao.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.entity.BladeWalletHistory;
import org.springblade.modules.taobao.service.IBladeWalletHistoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springblade.modules.taobao.config.TaobaoURLConfig.BLADE_WALLET_HISTORY_URL;
import static org.springblade.modules.taobao.config.TaobaoURLConfig.GET_USER_MONEY_HISTORY;

/**
 * @author SeventySeven
 * @since 2020-08-14
 */
@Api(tags = "钱变动历史表接口管理")
@RestController
@RequestMapping(BLADE_WALLET_HISTORY_URL)
@AllArgsConstructor
public class BladeWalletHistoryController {
	private final IBladeWalletHistoryService iBladeWalletHistoryService;

	/**
	 * 获取钱包历史
	 *
	 * @param userId userID
	 * @param status 0 未到账 1 到账
	 * @return 钱包历史
	 */
	@RequestMapping(value = GET_USER_MONEY_HISTORY, method = RequestMethod.GET)
	@ApiOperation(value = "钱包历史", notes = "用户钱包接口管理")
	@ApiImplicitParam(name = "status", value = "0 未到账,1到账 ", required = true)
	public R<List<BladeWalletHistory>> getUserWalletHistory(@RequestParam("user-Id") @NotNull String userId,
															@RequestParam("status") @NotNull Integer status) {
		return R.data(iBladeWalletHistoryService.list(Wrappers.<BladeWalletHistory>query().lambda().eq(BladeWalletHistory::getUserId, userId)
			.eq(BladeWalletHistory::getStatus, status)));
	}

}
