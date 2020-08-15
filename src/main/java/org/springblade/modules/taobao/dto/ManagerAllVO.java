package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 区域经理管理VO
 *
 * @author SeventySeven
 * @since 2020-08-15
 */
@ApiModel(value = "区域经理管理VO")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ManagerAllVO {
	@ApiModelProperty(value = "size")
	private Integer size;
	@ApiModelProperty(value = "current")
	private Integer current;
	@ApiModelProperty(value = "单个区域经理VO")
	private List<ManagerVO> list;
	@ApiModelProperty(value = "total")
	private Long total;
}
