package org.springblade.modules.taobao.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 门店allVO
 *
 * @author SeventySeven
 * @since 2020-08-15
 */
@ApiModel(value = "门店allVO")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StoreAllVO {
	@ApiModelProperty(value = "size")
	private Integer size;
	@ApiModelProperty(value = "current")
	private Integer current;
	@ApiModelProperty(value = "total")
	private Long total;
	@ApiModelProperty(value = "单个门店VO")
	private List<StoreVO> list;
}
