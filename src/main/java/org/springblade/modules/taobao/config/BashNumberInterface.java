package org.springblade.modules.taobao.config;

import java.math.BigDecimal;

/**
 * 基础数字转换配置
 *
 * @author SeventySeven
 * @since 2020-08-25
 */
public interface BashNumberInterface {
	String ADMIN = "管理员";
	String MANAGER = "区域经理";
	String STORE = "店铺";
	String STATUS_NO_CHECK = "未审核";
	String STATUS_ADMIN = "管理员";
	String STATUS_ERROR_CHECK = "审核不通过";
	String STATUS_OK_CHECK = "审核通过";
	String STATUS_OK_CHECK_NO_RATE = "审核通过未设置分红";
	String ERROR_DATA = "数据错误,请清除数据库垃圾数据";
	String INIT_QR_CODE = "请初始化二维码";
	String NUMBER_ONE = "1";
	String NO_MANAGER = "暂无区域经理";
	String ADMIN_ID = "admin";
	String ORDER_OK = "已过期订单";
	String ORDER_NO = "还未过期订单";
	String REASON_ADD = "提成";
	String REASON_SUB = "提现";


	Integer ADMIN_NUMBER = -1;
	Integer MANAGER_NUMBER = 3;
	Integer STORE_NUMBER = 2;
	Integer STATUS_NO_CHECK_NUMBER = 0;
	Integer STATUS_ADMIN_NUMBER = -1;
	Integer STATUS_ERROR_CHECK_NUMBER = 3;
	Integer STATUS_OK_CHECK_NUMBER = 1;
	Integer STATUS_OK_CHECK_NO_RATE_NUMBER = 2;
	BigDecimal INIT_USER_MONEY = new BigDecimal(0);
	Integer MONEY_STATUS_NO = 0;
	Integer MONEY_STATUS_OK = 1;
	Integer WITHDRAW = 10;
	Integer ADMIN_ADD_MONEY = -1;
	Integer ORDER_OK_NUMBER = 1;
	Integer ORDER_NO_NUMBER = 0;
	Integer ONE_DAY = 1000 * 60 * 60 * 24;
	Integer FOURTEEN_DAY = ONE_DAY * 14;
	Integer REASON_ADD_NUMBER = 0;
	Integer REASON_SUB_NUMBER = 1;

	/**
	 * 接受角色数字返回实际角色String
	 *
	 * @param roleNumber
	 * @return
	 */
	String getUserRole(Integer roleNumber);

	/**
	 * 返回用户状态String
	 *
	 * @param statusNumber
	 * @return
	 */
	String getUserAccountStatus(Integer statusNumber);
}
