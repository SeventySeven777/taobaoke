package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 多店page
 *
 * @author SeventySeven
 * @since 2020-08-15
 */
@ApiModel(value = "多店page")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BladeStoreRatePageVO {
	private List<BladeStoreRateVO> list;
	private Integer size;
	private Integer current;
	private Long total;
}
