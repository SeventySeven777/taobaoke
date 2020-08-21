package org.springblade.modules.taobao.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.CheckUserDTO;
import org.springblade.modules.taobao.dto.UserStatusVO;
import org.springblade.modules.taobao.entity.BladeUserCheck;
import org.springblade.modules.taobao.service.IBladeUserBashService;
import org.springblade.modules.taobao.service.IBladeUserCheckService;
import org.springblade.modules.taobao.service.IBladeUserService;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

import static org.springblade.modules.taobao.config.BashNumberInterface.*;
import static org.springblade.modules.taobao.config.MethodConfig.NO_QUERY_USER;
import static org.springblade.modules.taobao.config.TaobaoURLConfig.*;

/**
 * @author SeventySeven
 * @since 2020-08-14
 */
@Api(tags = "审核意见表接口管理")
@RestController
@RequestMapping(BLADE_USER_CHECK_URL)
@AllArgsConstructor
public class BladeUserCheckController {
	private final IBladeUserService iBladeUserService;
	private final IBladeUserBashService iBladeUserBashService;
	private final IBladeUserCheckService iBladeUserCheckService;

	@RequestMapping(value = MAYBE_NEED, method = RequestMethod.GET)
	@ApiOperation(value = "也许用得上的接口,未写完勿调", notes = "审核意见表接口管理")
	public R<UserStatusVO> maybeNeed(@RequestParam("user-id") String userId) {
		return null;
	}

	/**
	 * 获取用户审核状态
	 *
	 * @param status  审核状态
	 * @param size    分页
	 * @param current 分页
	 * @return 手动分页对象
	 */
	@RequestMapping(value = GET_USER_CHECK_BY_STATUS, method = RequestMethod.GET)
	@ApiOperation(value = "获取用户审核(分页)", notes = "获取用户审核(分页)")
	public R<Object> getUserCheckByStatus(@RequestParam("status") @NotNull Integer status,
										  @RequestParam("size") @NotNull Integer size,
										  @RequestParam("current") @NotNull Integer current) {
		List<String> userIds = iBladeUserService.getUserIdsByStatus(status, size, current, MANAGER_NUMBER);
		return iBladeUserBashService.getUserByIds(userIds, size, current, MANAGER_NUMBER);
	}

	/**
	 * 进行审核
	 *
	 * @param checkUserDTO 审核信息
	 * @return 是否成功
	 */
	@RequestMapping(value = CHECK_USER_STATUS, method = RequestMethod.PUT)
	@ApiOperation(value = "进行审核", notes = "进行审核")
	public R<String> checkUserStatus(@RequestBody CheckUserDTO checkUserDTO) {
		if (iBladeUserService.examineUser(checkUserDTO.getUserId())) {
			return R.fail(NO_QUERY_USER);
		}
		BladeUserCheck bladeUserCheck = new BladeUserCheck().setUserId(checkUserDTO.getUserId())
			.setCreateTime(new Date()).setCheckOpinion(checkUserDTO.getCheckOpinion());
		if (checkUserDTO.getStatus()) {
			return iBladeUserCheckService.checkUserStatus(bladeUserCheck, STATUS_OK_CHECK_NO_RATE_NUMBER);
		}
		return iBladeUserCheckService.checkUserStatus(bladeUserCheck, STATUS_ERROR_CHECK_NUMBER);
	}

	/**
	 * 查看不通过原因
	 *
	 * @param userId 用户ID
	 * @return 原因
	 */
	@RequestMapping(value = GET_MANAGER_OPINION, method = RequestMethod.GET)
	@ApiOperation(value = "查看不通过原因", notes = "进行审核")
	public R<String> getCheckOpinion(@RequestParam("user-id") @NotNull String userId) {
		return iBladeUserCheckService.getUserCheckOpinion(userId);
	}

}
