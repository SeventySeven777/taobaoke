package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 进行审核用户DTO
 *
 * @author SeventySeven
 * @since 2020-08-15
 */
@ApiModel(value = "进行审核用户DTO")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CheckUserDTO {
	@ApiModelProperty(value = "userId")
	@NotNull
	private String userId;
	@ApiModelProperty(value = "审核意见")
	@NotNull
	private String checkOpinion;
	@ApiModelProperty(value = "审核状态 true 通过 false 不通过")
	@NotNull
	private Boolean status;
}
