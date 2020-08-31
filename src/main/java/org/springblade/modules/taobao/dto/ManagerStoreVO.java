package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 经理查门店单个
 *
 * @author SeventySeven
 * @since 2020-08-15
 */
@ApiModel(value = "经理查门店单个")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ManagerStoreVO {
	private String storeName;
	private String time;
	private BigDecimal rateMoney;
	private Integer template;
}
