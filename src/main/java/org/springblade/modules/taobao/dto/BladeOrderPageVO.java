package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * order page vo
 *
 * @author SeventySeven
 * @since 2020-08-15
 */
@ApiModel(value = "order page vo")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BladeOrderPageVO {
	private List<BladeOrderVO> list;
	private Integer size;
	private Integer current;
	private Long total;
}
