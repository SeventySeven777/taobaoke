package org.springblade.modules.taobao.config;


/**
 * 淘宝客项目url配置
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
public interface TaobaoURLConfig {
	String bashAPI = "/taobao";
	String BLADE_ADMIN_ACCOUNT_URL = bashAPI + "/blade-admin-account";
	String BLADE_ORDER_URL = bashAPI + "/blade-order";
	String BLADE_RATE_URL = bashAPI + "/blade-rate";
	String BLADE_STORE_USER_MIDDLE_URL = bashAPI + "/blade-store-user-middle";
	String BLADE_USER_BASH_URL = bashAPI + "/blade-user-bash";
	String BLADE_USER_CHECK_URL = bashAPI + "/blade-user-check";
	String BLADE_USER_URL = bashAPI + "/blade-user";
	String BLADE_USER_STORE_URL = bashAPI + "/blade-user-store";
	String BLADE_WALLET_URL = bashAPI + "/blade-wallet";
	String BLADE_WALLET_HISTORY_URL = bashAPI + "/blade-wallet-history";
	String BLADE_LOGIN_URL = bashAPI + "/login";

	/**
	 * 登录controller
	 */
	String LOGIN_USER = "/login-user";
	String LOGIN_ADMIN = "/login-admin";
	/**
	 * 用户controller
	 */
	String INIT_USER = "/init-user";
}
