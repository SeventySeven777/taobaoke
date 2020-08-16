package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 提现DTO
 *
 * @author SeventySeven
 * @since 2020-08-15
 */
@ApiModel(value = "提现DTO")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SubMoneyDTO {
	@ApiModelProperty(value = "userID 管理员传admin", name = "userId", required = true)
	private String userId;
	@ApiModelProperty(value = "提现多少 传正数", name = "moneyChange", required = true)
	private BigDecimal moneyChange;
	@ApiModelProperty(value = "登录密码", name = "password", required = true)
	private String password;
}
