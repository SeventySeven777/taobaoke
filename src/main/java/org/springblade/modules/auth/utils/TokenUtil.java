/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.modules.auth.utils;

import org.springblade.core.launch.constant.TokenConstant;
import org.springblade.core.secure.AuthInfo;
import org.springblade.core.secure.TokenInfo;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.entity.UserInfo;
import org.springblade.modules.taobao.entity.BladeAdminAccount;
import org.springblade.modules.taobao.entity.BladeUser;
import org.springblade.modules.taobao.utils.SaveToken;

import java.util.HashMap;
import java.util.Map;

import static org.springblade.modules.taobao.config.BashNumberInterface.ADMIN_ID;

/**
 * 认证工具类
 *
 * @author Chill
 */
public class TokenUtil {

	public final static String CAPTCHA_HEADER_KEY = "Captcha-Key";
	public final static String CAPTCHA_HEADER_CODE = "Captcha-Code";
	public final static String CAPTCHA_NOT_CORRECT = "验证码不正确";
	public final static String TENANT_HEADER_KEY = "Tenant-Id";
	public final static String DEFAULT_TENANT_ID = "000000";
	public final static String USER_TYPE_HEADER_KEY = "User-Type";
	public final static String DEFAULT_USER_TYPE = "web";
	public final static String USER_NOT_FOUND = "用户名或密码错误";
	public final static String HEADER_KEY = "Authorization";
	public final static String HEADER_PREFIX = "Basic ";
	public final static String DEFAULT_AVATAR = "https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png";

	/**
	 * 创建认证token
	 *
	 * @param userInfo 用户信息
	 * @return token
	 */
	public static AuthInfo createAuthInfo(UserInfo userInfo) {
		User user = userInfo.getUser();

		//设置jwt参数
		Map<String, String> param = new HashMap<>(16);
		param.put(TokenConstant.TOKEN_TYPE, TokenConstant.ACCESS_TOKEN);
		param.put(TokenConstant.TENANT_ID, user.getTenantId());
		param.put(TokenConstant.USER_ID, Func.toStr(user.getId()));
		param.put(TokenConstant.ROLE_ID, user.getRoleId());
		param.put(TokenConstant.ACCOUNT, user.getAccount());
		param.put(TokenConstant.USER_NAME, user.getAccount());
		param.put(TokenConstant.ROLE_NAME, Func.join(userInfo.getRoles()));

		TokenInfo accessToken = SecureUtil.createJWT(param, "audience", "issuser", TokenConstant.ACCESS_TOKEN);
		AuthInfo authInfo = new AuthInfo();
		authInfo.setAccount(user.getAccount());
		authInfo.setUserName(user.getRealName());
		authInfo.setAuthority(Func.join(userInfo.getRoles()));
		authInfo.setAccessToken(accessToken.getToken());
		authInfo.setExpiresIn(accessToken.getExpire());
		authInfo.setRefreshToken(createRefreshToken(userInfo).getToken());
		authInfo.setTokenType(TokenConstant.BEARER);
		authInfo.setLicense(TokenConstant.LICENSE_NAME);

		return authInfo;
	}

	/**
	 * 创建认证token
	 *
	 * @param bladeUser 用户信息
	 * @return token
	 */
	public static AuthInfo createAuthInfo(BladeUser bladeUser, String userName, String roleName) {

		//设置jwt参数
		Map<String, String> param = new HashMap<>(16);
		param.put(TokenConstant.TOKEN_TYPE, TokenConstant.ACCESS_TOKEN);
		param.put(TokenConstant.USER_ID, Func.toStr(bladeUser.getId()));
		param.put(TokenConstant.ROLE_ID, bladeUser.getRole().toString());
		param.put(TokenConstant.ACCOUNT, bladeUser.getPhone());
		param.put(TokenConstant.USER_NAME, userName);
		param.put(TokenConstant.ROLE_NAME, roleName);

		TokenInfo accessToken = SecureUtil.createJWT(param, "audience", "issuser", TokenConstant.ACCESS_TOKEN);
		AuthInfo authInfo = new AuthInfo();
		authInfo.setAccount(bladeUser.getPhone());
		authInfo.setUserName(userName);
		authInfo.setAuthority(roleName);
		authInfo.setAccessToken(accessToken.getToken());
		authInfo.setExpiresIn(accessToken.getExpire());
		authInfo.setRefreshToken(createRefreshToken(bladeUser).getToken());
		authInfo.setTokenType(TokenConstant.BEARER);
		authInfo.setStatus(bladeUser.getStatus().toString());
		authInfo.setRoleId(bladeUser.getRole().toString());
		authInfo.setUserId(bladeUser.getId());
		authInfo.setLicense(TokenConstant.LICENSE_NAME);
		authInfo.setPasswordIsInitPassword(isInitPassword(bladeUser));
		SaveToken.addToken(accessToken.getToken());

		return authInfo;
	}

	private static Integer isInitPassword(BladeUser bladeUser) {
		return bladeUser.getPassword().equals(cn.hutool.crypto.SecureUtil.md5(bladeUser.getPhone())) ? 0 : 1;

	}

	/**
	 * 创建refreshToken
	 *
	 * @param userInfo 用户信息
	 * @return refreshToken
	 */
	private static TokenInfo createRefreshToken(UserInfo userInfo) {
		User user = userInfo.getUser();
		Map<String, String> param = new HashMap<>(16);
		param.put(TokenConstant.TOKEN_TYPE, TokenConstant.REFRESH_TOKEN);
		param.put(TokenConstant.USER_ID, Func.toStr(user.getId()));
		return SecureUtil.createJWT(param, "audience", "issuser", TokenConstant.REFRESH_TOKEN);
	}

	/**
	 * 创建refreshToken
	 *
	 * @param bladeUser 用户信息
	 * @return refreshToken
	 */
	private static TokenInfo createRefreshToken(BladeUser bladeUser) {

		Map<String, String> param = new HashMap<>(16);
		param.put(TokenConstant.TOKEN_TYPE, TokenConstant.REFRESH_TOKEN);
		param.put(TokenConstant.USER_ID, Func.toStr(bladeUser.getId()));
		return SecureUtil.createJWT(param, "audience", "issuser", TokenConstant.REFRESH_TOKEN);
	}

	public static AuthInfo createAuthInfo(BladeAdminAccount bladeAdminAccount) {
		//设置jwt参数
		Map<String, String> param = new HashMap<>(16);
		param.put(TokenConstant.TOKEN_TYPE, TokenConstant.ACCESS_TOKEN);
		param.put(TokenConstant.USER_ID, Func.toStr(bladeAdminAccount.getId()));
		param.put(TokenConstant.ACCOUNT, bladeAdminAccount.getAccount());
		param.put(TokenConstant.USER_NAME, ADMIN_ID);
		param.put(TokenConstant.ROLE_NAME, ADMIN_ID);

		TokenInfo accessToken = SecureUtil.createJWT(param, "audience", "issuser", TokenConstant.ACCESS_TOKEN);
		AuthInfo authInfo = new AuthInfo();
		authInfo.setAccessToken(accessToken.getToken());
		authInfo.setExpiresIn(accessToken.getExpire());
		authInfo.setTokenType(TokenConstant.BEARER);
		authInfo.setUserId(bladeAdminAccount.getId());
		authInfo.setLicense(TokenConstant.LICENSE_NAME);
		SaveToken.addToken(accessToken.getToken());
		return authInfo;
	}
}
