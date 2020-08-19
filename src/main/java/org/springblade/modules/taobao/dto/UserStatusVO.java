package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 *
 * @author SeventySeven
 * @since 2020-08-15
 */
@ApiModel(value = "登录前判断VO")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserStatusVO {
	@ApiModelProperty(value = "主页图片URl", name = "homeImage", required = true)
	private Integer role;
	@ApiModelProperty(value = "主页图片URl", name = "homeImage", required = true)
	private String roleName;
	@ApiModelProperty(value = "主页图片URl", name = "homeImage", required = true)
	private Integer status;
	@ApiModelProperty(value = "主页图片URl", name = "homeImage", required = true)
	private Integer statusName;
	@ApiModelProperty(value = "主页图片URl", name = "homeImage", required = true)
	private String userId;
	@ApiModelProperty(value = "主页图片URl", name = "homeImage", required = true)
	private String userName;
}
