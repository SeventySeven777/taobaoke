package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 经理名List
 *
 * @author SeventySeven
 * @since 2020-08-15
 */
@ApiModel(value = "经理名List")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ManagerNameVO {
	@ApiModelProperty(value = "经理name",name = "managerName",required = true)
	private String managerName;
	@ApiModelProperty(value = "经理ID",name = "managerId",required = true)
	private String managerId;
}
