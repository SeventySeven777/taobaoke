package org.springblade.modules.taobao.controller;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.ManagerAllVO;
import org.springblade.modules.taobao.dto.ManagerVO;
import org.springblade.modules.taobao.entity.BladeUser;
import org.springblade.modules.taobao.entity.BladeUserBash;
import org.springblade.modules.taobao.service.IBladeRateService;
import org.springblade.modules.taobao.service.IBladeStoreUserMiddleService;
import org.springblade.modules.taobao.service.IBladeUserBashService;
import org.springblade.modules.taobao.service.IBladeUserService;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

import static org.springblade.modules.taobao.config.TaobaoURLConfig.*;

/**
 * @author SeventySeven
 * @since 2020-08-14
 */
@Api(tags = "平台工作人员基础信息表接口管理")
@RestController
@AllArgsConstructor
@RequestMapping(BLADE_USER_BASH_URL)
public class BladeUserBashController {
	private final IBladeUserBashService iBladeUserBashService;
	private final IBladeUserService iBladeUserService;
	private final IBladeRateService iBladeRateService;
	private final IBladeStoreUserMiddleService iBladeStoreUserMiddleService;

	/**
	 * 获取区域经理管理页面数据
	 *
	 * @param size    分页
	 * @param current 分页
	 * @return 分页结果
	 */
	@RequestMapping(value = GET_MANAGER_PAGE, method = RequestMethod.GET)
	@ApiOperation(value = "获取区域经理管理页面数据", notes = "平台工作人员基础信息表接口管理")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "size", value = "分页", required = true),
		@ApiImplicitParam(name = "current", value = "分页", required = true)
	})
	public R<ManagerAllVO> getManagerPage(@RequestParam("size") @NotNull Integer size,
										  @RequestParam("current") @NotNull Integer current) {
		R<Page<BladeUser>> managerPage = iBladeUserService.getManagerPage(size, current);
		ManagerAllVO managerAllVO = new ManagerAllVO();
		List<ManagerVO> list = new ArrayList<>();
		managerPage.getData().getRecords().forEach(item -> {
			ManagerVO managerVO = new ManagerVO().setUserId(item.getId()).setPassword(SecureUtil.sha1(item.getPassword()))
				.setPhone(item.getPhone()).setRate(iBladeRateService.getManagerRate(item.getId()))
				.setUserName(iBladeUserBashService.getById(item.getId()).getUserName()).setCreateTime(item.getCreateDate().getTime())
				.setStoreNumber(iBladeStoreUserMiddleService.getMiddleNumber(item.getId()));
			list.add(managerVO);
		});
		return R.data(managerAllVO.setCurrent(current).setSize(size).setTotal(managerPage.getData().getTotal()).setList(list));
	}

	/**
	 * 查看个人资料
	 *
	 * @param userId userID
	 * @return userBash
	 */
	@RequestMapping(value = GET_MANAGER_INFO, method = RequestMethod.GET)
	@ApiOperation(value = "获取区域经理个人资料", notes = "平台工作人员基础信息表接口管理")
	public R<BladeUserBash> getManagerInfo(@RequestParam("user-id") @NotNull String userId) {
		return R.data(iBladeUserBashService.getById(userId));
	}

	@RequestMapping(value = UPLOAD_IMAGE, method = RequestMethod.POST)
	@ApiOperation(value = "上传任何东西都在这返回URL", notes = "平台工作人员基础信息表接口管理")
	public R<String> uploadSomething(@RequestParam("userId") @NotNull String userId,
									 @RequestBody @NotNull MultipartFile file) {
		return null;
	}
}
