package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用于接收用户修改密码
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@ApiModel(value = "修改密码DTO")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UpdateUserPasswordDTO {
	@ApiModelProperty(value = "用户ID", name = "userId", required = true)
	private String userId;
	@ApiModelProperty(value = "旧密码MD5加密", name = "passwordOld", required = true)
	private String passwordOld;
	@ApiModelProperty(value = "新密码不加密", name = "passwordNew", required = true)
	private String passwordNew;
}
