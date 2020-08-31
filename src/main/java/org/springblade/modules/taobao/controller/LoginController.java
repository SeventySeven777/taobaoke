package org.springblade.modules.taobao.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.secure.AuthInfo;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.InitUserDTO;
import org.springblade.modules.taobao.dto.LoginUserDTO;
import org.springblade.modules.taobao.entity.BladeUser;
import org.springblade.modules.taobao.entity.BladeUserBash;
import org.springblade.modules.taobao.entity.BladeUserStore;
import org.springblade.modules.taobao.service.*;
import org.springblade.modules.taobao.utils.CheckObjAllFieldsIsNullUtils;
import org.springblade.modules.taobao.utils.MyRedisUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

import java.util.List;

import static org.springblade.modules.taobao.config.BashNumberInterface.*;
import static org.springblade.modules.taobao.config.MethodConfig.*;
import static org.springblade.modules.taobao.config.TaobaoURLConfig.*;

/**
 * 登录页面controller
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@RestController
@RequestMapping(BLADE_LOGIN_URL)
@AllArgsConstructor
@Api(value = "登录", tags = "登录")
public class LoginController {
	private final IBladeUserService iBladeUserService;
	private final IBladeAdminAccountService iBladeAdminAccountService;
	private final MyRedisUtil myRedisUtil;
	private final IBladeUserBashService iBladeUserBashService;
	private final IBladeUserStoreService iBladeUserStoreService;
	private final SessionService sessionService;

	/**
	 * 用户登录 用户登录返回用户信息+token 管理员登录返回token
	 *
	 * @param loginUserDTO 登录数据
	 * @return token包
	 */
	@RequestMapping(value = LOGIN_USER, method = RequestMethod.POST)
	@ApiOperation(value = "任何角色登录", notes = "登录")
	public R<AuthInfo> loginTaoBao(@RequestBody LoginUserDTO loginUserDTO) {
		if (CheckObjAllFieldsIsNullUtils.checkObjAllFieldsIsNull(loginUserDTO)) {
			return R.fail(FIELD_MISSING);
		}
		return iBladeUserService.login(loginUserDTO);
	}

	/**
	 * admin登录 返回token
	 *
	 * @param loginUserDTO 登录数据
	 * @return token包
	 */
	@RequestMapping(value = LOGIN_ADMIN, method = RequestMethod.POST)
	@ApiOperation(value = "admin登录admin,admin", notes = "登录")
	public R<AuthInfo> loginTaoBaoAdmin(@RequestBody LoginUserDTO loginUserDTO) {
		if (CheckObjAllFieldsIsNullUtils.checkObjAllFieldsIsNull(loginUserDTO)) {
			return R.fail(FIELD_MISSING);
		}
		return iBladeUserService.loginAdmin(loginUserDTO);
	}

	/**
	 * 用户注册,如果手机号重复将不能注册
	 *
	 * @param initUserDTO 注册DTO
	 * @return isOk
	 */
	@RequestMapping(value = INIT_USER, method = RequestMethod.POST)
	@ApiOperation(value = "经理注册", notes = "登录")
	public R<AuthInfo> initUser(@RequestBody InitUserDTO initUserDTO) {
		if (iBladeUserService.examineUserPhone(initUserDTO.getPhone())) {
			//判断手机号是否注册
			return R.fail(USER_PHONE_OR_ACCOUNT_REPETITION);
		}
		BladeUserBash bladeUserBash = iBladeUserService.deCode(initUserDTO);
		R<BladeUser> bladeUserR = iBladeUserService.initUserAll(bladeUserBash);
		//初始user完成
		if (!bladeUserR.isSuccess()) {
			return R.fail(USER_INIT_ERROR);
		}
		return iBladeUserService.login(new LoginUserDTO().setPhone(initUserDTO.getPhone()).setPassword(SecureUtil.md5(initUserDTO.getPhone())));
	}

	/**
	 * 再次发起审核
	 *
	 * @param initUserDTO 注册参数覆写
	 * @return 登录token
	 */
	@RequestMapping(value = TO_CHECK_AGAIN, method = RequestMethod.POST)
	@ApiOperation(value = "再次发起审核", notes = "登录")
	public R<AuthInfo> toCheckAgain(@RequestBody InitUserDTO initUserDTO, @RequestParam("id")String id) {
		BladeUser bladeUser = myRedisUtil.get(REDIS_USER+id);
		if (!bladeUser.getPhone().equals(initUserDTO.getPhone())){
			return R.fail("手机号不准改!!!");
		}
		BladeUserBash bladeUserBash = iBladeUserService.deCode(initUserDTO);
		//R<BladeUser> user = sessionService.getUser();
		//todo:暂时这个地方定没有token 故注释掉
		R<BladeUser> user = R.data(bladeUser);
		if (!user.isSuccess()) {
			return R.fail(LOGIN_PLEASE);
		}
		BladeUserBash bladeUserBashOld = myRedisUtil.get(REDIS_BASH + user.getData().getId());
		BeanUtil.copyProperties(bladeUserBash, bladeUserBashOld);
		iBladeUserBashService.updateById(bladeUserBashOld);
		myRedisUtil.deleteAndSet(REDIS_BASH + user.getData().getId(), bladeUserBashOld);
		return iBladeUserService.login(new LoginUserDTO().setPhone(initUserDTO.getPhone()).setPassword(user.getData().getPassword()));
	}

	/**
	 * 获取主页图片
	 *
	 * @return 主页图片
	 */
	@RequestMapping(value = GET_IMAGES, method = RequestMethod.GET)
	@ApiOperation(value = "获取设置主页图片", notes = "登录")
	public R<String> getHomeImage() {
		System.out.println(sessionService.getUser());
		return R.data(iBladeAdminAccountService.getById(ADMIN_ID).getHomeImage());
	}

	/**
	 * 进行一点redis的加载
	 * 暂定所有数据
	 * 封装格式如下↓
	 */
	@PostConstruct
	public void initRedis() {
		//initStoreCodeNumber
		if (null == myRedisUtil.get(REDIS_STORE_CODE)) {
			myRedisUtil.set(REDIS_STORE_CODE, 0);
		}
		//initUser
		List<BladeUser> userList = iBladeUserService.list();
		userList.forEach(item -> {
			myRedisUtil.set(REDIS_USER + item.getId(), item);
		});
		//initUserBash
		List<BladeUserBash> bashList = iBladeUserBashService.list();
		bashList.forEach(item -> {
			myRedisUtil.set(REDIS_BASH + item.getId(), item);
		});
		//initStore
		List<BladeUserStore> storeList = iBladeUserStoreService.list();
		storeList.forEach(item -> {
			myRedisUtil.set(USER_STORE + item.getId(), item);
		});
	}
}
