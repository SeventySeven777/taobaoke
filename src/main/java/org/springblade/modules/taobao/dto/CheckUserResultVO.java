package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 审核用户返回结果
 *
 * @author SeventySeven
 * @since 2020-08-15
 */
@ApiModel(value = "审核用户返回结果")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CheckUserResultVO {
	@ApiModelProperty(value = "结果")
	private List<CheckUserVO> list;
	@ApiModelProperty(value = "size")
	private Integer size;
	@ApiModelProperty(value = "current")
	private Integer current;
	@ApiModelProperty(value = "total")
	private Long total;
}
