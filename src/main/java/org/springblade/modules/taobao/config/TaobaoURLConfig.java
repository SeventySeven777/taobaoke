package org.springblade.modules.taobao.config;


/**
 * 淘宝客项目url配置
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
public interface TaobaoURLConfig {
	String BASH_API = "/taobao";
	String BLADE_ADMIN_ACCOUNT_URL = BASH_API + "/blade-admin-account";
	String BLADE_ORDER_URL = BASH_API + "/blade-order";
	String BLADE_RATE_URL = BASH_API + "/blade-rate";
	String BLADE_STORE_USER_MIDDLE_URL = BASH_API + "/blade-store-user-middle";
	String BLADE_USER_BASH_URL = BASH_API + "/blade-user-bash";
	String BLADE_USER_CHECK_URL = BASH_API + "/blade-user-check";
	String BLADE_USER_URL = BASH_API + "/blade-user";
	String BLADE_USER_STORE_URL = BASH_API + "/blade-user-store";
	String BLADE_WALLET_URL = BASH_API + "/blade-wallet";
	String BLADE_WALLET_HISTORY_URL = BASH_API + "/blade-wallet-history";
	String BLADE_LOGIN_URL = BASH_API + "/login";
	String FILE_LOAD_URL = BASH_API + "/file";

	/**
	 * 登录controller
	 */
	String LOGIN_USER = "/login-user";
	String LOGIN_ADMIN = "/login-admin";
	/**
	 * 用户controller
	 */
	String INIT_USER = "/init-user";
	String INIT_STORE = "/init-store";
	String UPDATE_USER_PASSWORD = "/update-password";
	String SEARCH_USER_BY_PHONE_OR_NAME = "/search-user-by-something";
	String DELETE_USER = "/delete-user";
	String UPDATE_ACCOUNT = "/update-account";
	String GET_MANAGER_LIST = "/get-manager-list";

	/**
	 * 店铺controller
	 */
	String GET_STORE_INFO = "/get-store-info";
	String GET_STORE_PAGE = "/get-store-page-info";
	String PUT_STORE_MANAGER = "/update-store-manager";
	String DELETE_STORE_MANAGER = "/delete-store";

	/**
	 * 审核状态controller
	 */
	String GET_USER_CHECK_BY_STATUS = "/get-user-check-by-status";
	String CHECK_USER_STATUS = "/check-user";
	String GET_MANAGER_OPINION = "/get-user-check-opinion";
	String MAYBE_NEED = "/get-about-user-something";

	/**
	 * 分红controller
	 */
	String UPDATE_MANAGER_RATE = "/update-manager-rate";
	String UPDATE_STORE_RATE = "/update-store-rate";

	/**
	 * manager Controller
	 */
	String GET_MANAGER_PAGE = "/get-manager-page";
	String GET_MANAGER_INFO = "/get-manager-info";
	String UPLOAD_IMAGE = "/upload-images";

	/**
	 * wallet Controller
	 */
	String GET_USER_MONEY = "/get-user-wallet";
	String SUB_USER_MONEY = "/sub-user-wallet";
	String ADD_ADMIN_MONEY = "/add_admin_money";
	String ADMIN_INIT_WALLET = "/";
	String GET_USER_MONEY_HISTORY = "/get-user-wallet-history";

	/**
	 * admin controller
	 */
	String SET_IMAGES = "/set-images";
	String GET_IMAGES = "/get-images";

	/**
	 * order controller
	 */
	String GET_ORDER_PAGE_INFO = "/get-order-info";
}
