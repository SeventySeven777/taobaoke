package org.springblade.modules.taobao.config;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * method配置
 * 方便找统一放一起
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@ApiModel(value = "method配置")
@Accessors(chain = true)
public interface MethodConfig {
	String FIELD_MISSING = "参数为空或不全,请检查参数";
	String USER_PHONE_OR_ACCOUNT_REPETITION = "用户账号或手机号已经存在";
	String SAVE_ERROR = "保存失败";
	String USER_INIT_ERROR = "注册用户失败,请重试";
	String USER_INIT_OK = "注册成功,请登录";
	String NO_MANAGER = "未查询到该经理,不能进行注册";
	String STORE_INIT_ERROR = "创建店铺失败,请重试";
	String CREATE_LINE_ERROR = "关联店铺失败";
	String STORE_INIT_OK = "店铺创建成功";
	String NO_QUERY_USER = "未查询到该用户,检查ID";
	String PASSWORD_ERROR = "密码错误";
	String SAVE_OK = "保存成功";
	String DELETE_OK = "删除成功";
	String MONEY_ERROR = "余额不足";
	String INIT_OK = "管理员初始化成功";
	String NO_USER_OR_PASSWORD_ERROR = "用户名或密码错误";
	String NO_TIME = "NULL";


}
