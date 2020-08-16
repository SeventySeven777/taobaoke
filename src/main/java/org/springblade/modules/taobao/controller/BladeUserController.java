package org.springblade.modules.taobao.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.CheckUserResultVO;
import org.springblade.modules.taobao.dto.InitStoreDTO;
import org.springblade.modules.taobao.dto.InitUserDTO;
import org.springblade.modules.taobao.dto.UpdateUserPasswordDTO;
import org.springblade.modules.taobao.entity.BladeUser;
import org.springblade.modules.taobao.entity.BladeUserBash;
import org.springblade.modules.taobao.entity.BladeUserStore;
import org.springblade.modules.taobao.service.*;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

import javax.validation.constraints.NotNull;
import java.util.List;

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
	private final IBladeUserService iBladeUserService;
	private final IBladeStoreUserMiddleService iBladeStoreUserMiddleService;
	private final IBladeUserBashService iBladeUserBashService;

	/**
	 * 用户注册,如果手机号重复将不能注册
	 *
	 * @param initUserDTO 注册DTO
	 * @return isOk
	 */
	@RequestMapping(value = INIT_USER, method = RequestMethod.POST)
	@ApiOperation(value = "经理注册", notes = "平台用户表接口管理")
	public R<String> initUser(@RequestBody InitUserDTO initUserDTO) {
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
	 * @return isok
	 */
	@RequestMapping(value = INIT_STORE, method = RequestMethod.POST)
	@ApiOperation(value = "门店注册", notes = "平台用户表接口管理")
	public R<String> initStore(@RequestBody InitStoreDTO initStoreDTO) {
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
		@ApiImplicitParam(name = "what", value = "条件传空格查所有(ps此处查所有会把店铺,一起查出来)", required = true)
	})
	public R<CheckUserResultVO> searchUserByPhoneOrName(@RequestParam("what") @NotNull String what,
														@RequestParam("size") @NotNull Integer size,
														@RequestParam("current") @NotNull Integer current) {
		if (size == null || current == null || size.equals(0) || current.equals(0)) {
			size = 50;
			current = 1;
		}
		List<String> userIds;
		if (!StrUtil.isBlank(what)) {
			userIds = iBladeUserBashService.getUserIdBySomething(what, size, current);
			//获取到用户ids
		} else {
			userIds = iBladeUserService.getAllUserIds(size, current);
		}
		return iBladeUserBashService.getUserByIds(userIds, size, current);
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
	@ApiOperation(value = "修改账号", notes = "平台用户表接口管理")
	public R<String> updateAccount(@RequestParam("user-id") @NotNull String userId,
								   @RequestParam("account") @NotNull String account) {
		if (iBladeUserService.examineUserPhone(account)) {
			BladeUser bladeUser = iBladeUserService.getById(userId);
			iBladeUserService.updateById(bladeUser.setPhone(account));
			return R.success(SAVE_OK);
		}
		return R.fail(USER_PHONE_OR_ACCOUNT_REPETITION);
	}


}
