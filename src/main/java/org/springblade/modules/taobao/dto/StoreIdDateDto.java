package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 经理查点所有
 *
 * @author SeventySeven
 * @since 2020-08-15
 */
@ApiModel(value = "经理查点所有")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StoreIdDateDto {
	private List<String> list;
	private Long total;
}
