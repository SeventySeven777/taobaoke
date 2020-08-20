package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 单个区域经理VO
 *
 * @author SeventySeven
 * @since 2020-08-15
 */
@ApiModel(value = "单个区域经理VO")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ManagerVO {
	@ApiModelProperty(value = "用户ID")
	private String userId;
	@ApiModelProperty(value = "姓名")
	private String userName;
	@ApiModelProperty(value = "手机号")
	private String phone;
	@ApiModelProperty(value = "分红")
	private BigDecimal rate;
	@ApiModelProperty(value = "门店数")
	private Integer storeNumber;
	@ApiModelProperty(value = "密码")
	private String password;
	@ApiModelProperty(value = "创建时间")
	private Long createTime;
}
