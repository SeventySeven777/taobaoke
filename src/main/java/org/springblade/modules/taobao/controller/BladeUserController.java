package org.springblade.modules.taobao.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.*;
import org.springblade.modules.taobao.entity.BladeRate;
import org.springblade.modules.taobao.entity.BladeUser;
import org.springblade.modules.taobao.entity.BladeUserBash;
import org.springblade.modules.taobao.entity.BladeUserStore;
import org.springblade.modules.taobao.service.*;
import org.springblade.modules.taobao.utils.DoDecodeAliPayCode;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springblade.modules.taobao.config.BashNumberInterface.STORE_NUMBER;
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
@CrossOrigin
public class BladeUserController {
	private final IBladeUserService iBladeUserService;
	private final IBladeStoreUserMiddleService iBladeStoreUserMiddleService;
	private final IBladeUserBashService iBladeUserBashService;
	private final IBladeRateService iBladeRateService;

	/**
	 * 创建店铺 手机号重复 经理ID无人则不能注册
	 *
	 * @param initStoreDTO 注册DTO
	 * @return isok
	 */
	@RequestMapping(value = INIT_STORE, method = RequestMethod.POST)
	@ApiOperation(value = "门店注册", notes = "平台用户表接口管理")
	public R<String> initStore(@RequestBody InitStoreDTO initStoreDTO) {
		if (iBladeUserService.examineUser(initStoreDTO.getManagerId())) {
			return R.fail(NO_MANAGER);
		}

		if (null != iBladeUserService.getOne(Wrappers.<BladeUser>query().lambda().eq(BladeUser::getPhone, initStoreDTO.getPhone()))) {
			//判断手机号是否注册
			return R.fail(USER_PHONE_OR_ACCOUNT_REPETITION);
		}
		BladeUserStore bladeUserStore = iBladeUserService.deCode(initStoreDTO);
		R<BladeUserStore> bladeUserStoreR = iBladeUserService.initStore(bladeUserStore, DoDecodeAliPayCode.deCode(initStoreDTO.getProvince()));
		if (!bladeUserStoreR.isSuccess()) {
			return R.fail(STORE_INIT_ERROR);
			//initStore ok
		}
		if (!iBladeStoreUserMiddleService.createLine(initStoreDTO.getManagerId(), bladeUserStoreR.getData().getId())) {
			return R.fail(CREATE_LINE_ERROR);
		}
		//initRate
		iBladeRateService.save(new BladeRate().setUserId(bladeUserStoreR.getData().getId()).setRate(new BigDecimal(0)));
		return R.success(STORE_INIT_OK);
	}

	/**
	 * 用户修改密码
	 *
	 * @param updateUserPasswordDTO 修改密码FTO
	 * @return isOK
	 */
	@RequestMapping(value = UPDATE_USER_PASSWORD, method = RequestMethod.POST)
	@ApiOperation(value = "修改密码", notes = "平台用户表接口管理")
	public R<String> updateUserPassword(@RequestBody UpdateUserPasswordDTO updateUserPasswordDTO) {
		BladeUser bladeUser = iBladeUserService.getById(updateUserPasswordDTO.getUserId());
		if (null == bladeUser) {
			return R.fail(NO_QUERY_USER);
		}
		if (iBladeUserService.whetherPassword(bladeUser.getPassword(), updateUserPasswordDTO.getPasswordOld())) {
			return iBladeUserService.setPassword(bladeUser, updateUserPasswordDTO.getPasswordNew());
		}
		return R.fail(PASSWORD_ERROR);
	}

	/**
	 * 模糊搜索
	 *
	 * @param what    条件
	 * @param size    分页
	 * @param current 分页
	 * @return 分页结果
	 */
	@RequestMapping(value = SEARCH_USER_BY_PHONE_OR_NAME, method = RequestMethod.GET)
	@ApiOperation(value = "搜索用户", notes = "平台用户表接口管理")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "size", value = "分页数据传0为默认", required = true),
		@ApiImplicitParam(name = "current", value = "分页数据传0为默认", required = true),
		@ApiImplicitParam(name = "filter", value = "条件传空格查所有", required = true),
		@ApiImplicitParam(name = "role", value = "角色 -1 admin 3 manager 2 store", required = true),
		@ApiImplicitParam(name = "status", value = "4个状态", required = true)
	})
	public R<Object> searchUserByPhoneOrName(@RequestParam("filter") @NotNull String what,
											 @RequestParam("size") @NotNull Integer size,
											 @RequestParam("current") @NotNull Integer current,
											 @RequestParam("role") @NotNull Integer role,
											 @RequestParam("status") @NotNull Integer status) {
		if (size == null || current == null || size.equals(0) || current.equals(0)) {
			size = 50;
			current = 1;
		}
		List<BladeUser> list = iBladeUserService.list(Wrappers.<BladeUser>query().lambda().eq(BladeUser::getRole, role)
			.eq(BladeUser::getStatus, status));
		List<String> ids = new ArrayList<>();
		list.forEach(item -> ids.add(item.getId()));
		List<String> userIds;
		if (!StrUtil.isBlank(what) && ids.size() != 0) {
			userIds = iBladeUserBashService.getUserIdBySomething(what, size, current, role, status, ids);
			//获取到用户ids
		} else {
			userIds = iBladeUserService.getAllUserIds(size, current, role, status);
		}
		return iBladeUserBashService.getUserByIds(userIds, size, current, role);
	}

	/**
	 * 删除账户
	 * 此处默认删除后 店铺删除userID 置为 1 方便分配或者其他
	 *
	 * @param userId userID
	 * @return 返回删除成功
	 */
	@RequestMapping(value = DELETE_USER, method = RequestMethod.DELETE)
	@ApiOperation(value = "删除账户", notes = "平台用户表接口管理")
	public R<String> deleteUser(@RequestParam("user-id") @NotNull String userId) {
		return iBladeUserBashService.deleteUser(userId);
	}

	/**
	 * 修改账号
	 *
	 * @param userId  id
	 * @param account phone
	 * @return isOk
	 */
	@RequestMapping(value = UPDATE_ACCOUNT, method = RequestMethod.PUT)
	@ApiOperation(value = "修改账号登录帐号", notes = "平台用户表接口管理")
	public R<String> updateAccount(@RequestParam("account") @NotNull String account,
								   @RequestParam("user-id") @NotNull String userId) {
		if (!iBladeUserService.examineUserPhone(account)) {
			BladeUser bladeUser = iBladeUserService.getById(userId);
			iBladeUserService.updateById(bladeUser.setPhone(account));
			return R.success(SAVE_OK);
		}
		return R.fail(USER_PHONE_OR_ACCOUNT_REPETITION);
	}

	/**
	 * 获取经理名称List
	 *
	 * @return list
	 */
	@RequestMapping(value = GET_MANAGER_LIST, method = RequestMethod.GET)
	@ApiOperation(value = "获取经理名称List", notes = "平台用户表接口管理")
	public R<List<ManagerNameVO>> getManagerNameList() {
		List<BladeUserBash> list = iBladeUserBashService.list();
		List<ManagerNameVO> result = new ArrayList<>();
		if (list.size() > 0) {
			list.forEach(item -> result.add(new ManagerNameVO().setManagerName(item.getUserName()).setManagerId(item.getId())));
		}
		return R.data(result);
	}
}
