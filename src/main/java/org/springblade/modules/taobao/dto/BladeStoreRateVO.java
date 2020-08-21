package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 单店
 *
 * @author SeventySeven
 * @since 2020-08-15
 */
@ApiModel(value = "单店")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BladeStoreRateVO {
	@ApiModelProperty(value = "店铺ID")
	private String storeId;
	@ApiModelProperty(value = "店铺名")
	private String storeName;
	@ApiModelProperty(value = "创建时间")
	private Long createTime;
	@ApiModelProperty(value = "总提成")
	private BigDecimal allRate;
}
