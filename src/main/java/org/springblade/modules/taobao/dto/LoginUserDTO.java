package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用于接收用户登录
 * @author SeventySeven
 * @since 2020-08-14
 */
@ApiModel(value = "用户登录DTO")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginUserDTO {
	@ApiModelProperty(value = "用户账号同手机号",name = "phone",required = true)
	private String phone;
	@ApiModelProperty(value = "密码需加密MD5 32小写",name = "password",required = true)
	private String password;
}
