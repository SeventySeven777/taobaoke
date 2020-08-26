package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 单个门店VO
 *
 * @author SeventySeven
 * @since 2020-08-15
 */
@ApiModel(value = "单个门店VO")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StoreVO {
	@ApiModelProperty(value = "storeId")
	private String storeId;
	@ApiModelProperty(value = "门店名称")
	private String storeName;
	@ApiModelProperty(value = "门店负责人")
	private String storeHuman;
	@ApiModelProperty(value = "手机")
	private String phone;
	@ApiModelProperty(value = "密码")
	private String password;
	@ApiModelProperty(value = "地址")
	private String address;
	@ApiModelProperty(value = "账号")
	private String account;
	@ApiModelProperty(value = "所属区域经理")
	private String managerName;
	@ApiModelProperty(value = "二维码")
	private String qRCode;
	@ApiModelProperty(value = "图片")
	private String image;
	@ApiModelProperty(value = "分红")
	private BigDecimal rate;
	@ApiModelProperty(value = "支付宝帐号")
	private String payNumber;
	@ApiModelProperty(value = "创建时间")
	private Long createTime;
	@ApiModelProperty(value = "门店唯一码")
	private String storeCode;
}
