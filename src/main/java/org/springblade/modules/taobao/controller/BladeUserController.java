package org.springblade.modules.taobao.controller;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.InitStoreDTO;
import org.springblade.modules.taobao.dto.InitUserDTO;
import org.springblade.modules.taobao.entity.BladeUser;
import org.springblade.modules.taobao.entity.BladeUserBash;
import org.springblade.modules.taobao.entity.BladeUserStore;
import org.springblade.modules.taobao.service.IBladeStoreUserMiddleService;
import org.springblade.modules.taobao.service.IBladeUserCheckService;
import org.springblade.modules.taobao.service.IBladeUserService;
import org.springblade.modules.taobao.service.IBladeUserStoreService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springblade.modules.taobao.config.MethodConfig.*;
import static org.springblade.modules.taobao.config.TaobaoURLConfig.*;

/**
 * @author SeventySeven
 * @since 2020-08-14
 */
@Api(tags = "平台用户表接口管理")
@RestController
@RequestMapping(BLADE_USER_URL)
@AllArgsConstructor
public class BladeUserController {
	private IBladeUserService iBladeUserService;
	private IBladeUserCheckService iBladeUserCheckService;
	private IBladeUserStoreService iBladeUserStoreService;
	private IBladeStoreUserMiddleService iBladeStoreUserMiddleService;

	/**
	 * 用户注册,如果手机号重复将不能注册
	 *
	 * @param initUserDTO 注册DTO
	 * @return
	 */
	@RequestMapping(value = INIT_USER, method = RequestMethod.POST)
	@ApiOperation(value = "经理注册", notes = "注册")
	public R initUser(@RequestBody InitUserDTO initUserDTO) {
		if (iBladeUserService.examineUserPhone(initUserDTO.getPhone())) {
			//判断手机号是否注册
			return R.fail(USER_PHONE_OR_ACCOUNT_REPETITION);
		}
		BladeUserBash bladeUserBash = new BladeUserBash();
		BeanUtil.copyProperties(initUserDTO, bladeUserBash);
		R<BladeUser> bladeUserR = iBladeUserService.initUserAll(bladeUserBash);
		//初始user完成
		if (!bladeUserR.isSuccess()) {
			return R.fail(USER_INIT_ERROR);
		}
		return R.success(USER_INIT_OK);
	}

	/**
	 * 创建店铺 手机号重复 经理ID无人则不能注册
	 *
	 * @param initStoreDTO 注册DTO
	 * @return
	 */
	@RequestMapping(value = INIT_STORE, method = RequestMethod.POST)
	@ApiOperation(value = "门店注册", notes = "注册")
	public R initStore(@RequestBody InitStoreDTO initStoreDTO) {
		if (iBladeUserService.examineUser(initStoreDTO.getManagerId())) {
			return R.fail(NO_MANAGER);
		}
		if (iBladeUserService.examineUserPhone(initStoreDTO.getPhone())) {
			//判断手机号是否注册
			return R.fail(USER_PHONE_OR_ACCOUNT_REPETITION);
		}
		BladeUserStore bladeUserStore = new BladeUserStore();
		BeanUtil.copyProperties(initStoreDTO, bladeUserStore);
		//此时QRCode id 为空
		R<BladeUserStore> bladeUserStoreR = iBladeUserService.initStore(bladeUserStore);
		if (!bladeUserStoreR.isSuccess()) {
			return R.fail(STORE_INIT_ERROR);
			//initStore ok
		}
		if (iBladeStoreUserMiddleService.createLine(initStoreDTO.getManagerId(), bladeUserStoreR.getData().getId())) {
			return R.fail(CREATE_LINE_ERROR);
		}
		return R.success(STORE_INIT_OK);
	}

}
