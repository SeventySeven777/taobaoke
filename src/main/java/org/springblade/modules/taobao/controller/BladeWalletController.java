package org.springblade.modules.taobao.controller;

import cn.hutool.crypto.SecureUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.SubMoneyDTO;
import org.springblade.modules.taobao.entity.BladeAdminAccount;
import org.springblade.modules.taobao.entity.BladeUser;
import org.springblade.modules.taobao.entity.BladeWallet;
import org.springblade.modules.taobao.service.IBladeAdminAccountService;
import org.springblade.modules.taobao.service.IBladeUserService;
import org.springblade.modules.taobao.service.IBladeWalletService;
import org.springblade.modules.taobao.service.SessionService;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

import static org.springblade.modules.taobao.config.BashNumberInterface.*;
import static org.springblade.modules.taobao.config.MethodConfig.*;
import static org.springblade.modules.taobao.config.TaobaoURLConfig.*;

/**
 * @author SeventySeven
 * @since 2020-08-14
 */
@Api(tags = "用户钱包接口管理")
@RestController
@RequestMapping(BLADE_WALLET_URL)
@AllArgsConstructor
public class BladeWalletController {
	private final IBladeWalletService iBladeWalletService;
	private final IBladeUserService iBladeUserService;
	private final IBladeAdminAccountService iBladeAdminAccountService;
	private final SessionService sessionService;

	/**
	 * 获取用户钱包数据
	 *
	 * @param userId userID
	 * @return 钱包
	 */
	@RequestMapping(value = GET_USER_MONEY, method = RequestMethod.GET)
	@ApiOperation(value = "获取用户钱包数据", notes = "用户钱包接口管理")
	public R<BladeWallet> getUserWallet(@RequestParam("user-id") @NotNull String userId) {
		return R.data(iBladeWalletService.getById(userId));
	}

	/**
	 * 提现 管理员传admin
	 *
	 * @param subMoneyDTO 提现DTO
	 * @return 是否成功
	 */
	@RequestMapping(value = SUB_USER_MONEY, method = RequestMethod.POST)
	@ApiOperation(value = "提现 管理员传admin", notes = "用户钱包接口管理")
	public R<String> subMoney(@RequestBody SubMoneyDTO subMoneyDTO) {
		BladeUser bladeUser = iBladeUserService.getById(subMoneyDTO.getUserId());
		if (null == bladeUser || !bladeUser.getPassword().equals(subMoneyDTO.getPassword())) {
			return R.fail(PASSWORD_ERROR);
		}
		return iBladeWalletService.subMoney(iBladeWalletService.getById(subMoneyDTO.getUserId())
			, subMoneyDTO.getMoneyChange().multiply(new BigDecimal(-1)), WITHDRAW);
	}

	/**
	 * 提现新
	 *
	 * @return 是否成功
	 */
	@RequestMapping(value = SUB_USER_MONEY_NEW, method = RequestMethod.GET)
	@ApiOperation(value = "提现新,请求一下开始发钱返回是否成功", notes = "用户钱包接口管理")
	public R<String> subMoneyNew() {
		if (!sessionService.getAdmin().getData()) {
			return R.fail(JUST_ADMIN_CAN_SUB);
		}
		return iBladeWalletService.subMoneyNew();
	}


	/**
	 * 管理员加钱,目前为直接钱包加钱 之后情况再改
	 *
	 * @param money 钱
	 * @return 管理员钱包
	 */
	@RequestMapping(value = ADD_ADMIN_MONEY, method = RequestMethod.PUT)
	@ApiOperation(value = "管理员充值,暂定,之后随情况改", notes = "用户钱包接口管理")
	public R<String> addMoneyByAdmin(@RequestParam("money") @NotNull BigDecimal money) {
		BladeWallet adminWallet = iBladeWalletService.getById(ADMIN_ID);
		return iBladeWalletService.addMoney(adminWallet, money, ADMIN_ADD_MONEY);
	}

	/**
	 * 初始化管理员账号,管理员钱包
	 *
	 * @return initOk
	 */
	@ApiIgnore
	@RequestMapping(value = ADMIN_INIT_WALLET, method = RequestMethod.POST)
	@PostConstruct
	public R<String> initAdminWallet() {
		BladeAdminAccount admin = iBladeAdminAccountService.getById(ADMIN_ID);
		if (null != admin) {
			return R.success(INIT_OK);
		}
		iBladeAdminAccountService.save(new BladeAdminAccount().setId(ADMIN_ID).setAccount(ADMIN_ID)
			.setPassword(SecureUtil.md5(ADMIN_ID)).setAllMoney(new BigDecimal(0)).setRateMoney(new BigDecimal(0)));
		iBladeWalletService.save(new BladeWallet().setId(ADMIN_ID).setConversionMoneyAll(new BigDecimal(0))
			.setHistoryMoneyAll(new BigDecimal(0)).setMoney(new BigDecimal(0)));
		return R.success(INIT_OK);
	}


}
