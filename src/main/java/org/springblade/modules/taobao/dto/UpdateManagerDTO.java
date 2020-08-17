package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 修改帐号改版DTO
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@ApiModel(value = "修改帐号改版DTO")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UpdateManagerDTO {
	@ApiModelProperty(value = "负责人姓名", name = "storeHuman", required = true)
	private String storeHuman;
	@ApiModelProperty(value = "店铺ID", name = "storeId", required = true)
	private String storeId;
	@ApiModelProperty(value = "负责人支付宝帐号", name = "payNumber", required = true)
	private String payNumber;

}
