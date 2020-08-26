package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用于用户注册
 *
 * @author SeventySeven
 * @since 2020-08-15
 */
@ApiModel(value = "店铺注册DTO")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InitStoreDTO {
	/**
	 * 店铺名称
	 */
	@ApiModelProperty(value = "店铺名称", name = "storeName", required = true)
	private String storeName;
	/**
	 * 店铺负责人
	 */
	@ApiModelProperty(value = "店铺负责人", name = "storeHuman", required = true)
	private String storeHuman;
	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号", name = "phone", required = true)
	private String phone;
	/**
	 * 地址
	 */
	@ApiModelProperty(value = "地址", name = "address", required = true)
	private String address;
	/**
	 * 经度
	 */
	@ApiModelProperty(value = "经度", name = "longitude", required = true)
	private String longitude;
	/**
	 * 维度
	 */
	@ApiModelProperty(value = "维度", name = "latitude", required = true)
	private String latitude;
	/**
	 * 图片
	 */
	@ApiModelProperty(value = "图片", name = "image", required = true)
	private String image;
	/**
	 * 经理ID
	 */
	@ApiModelProperty(value = "经理ID", name = "managerId", required = true)
	private String managerId;
	/**
	 * 支付宝ID
	 */
	@ApiModelProperty(value = "支付宝帐号", name = "payNumber", required = true)
	private String payNumber;
	/**
	 * 省份信息
	 */
	@ApiModelProperty(value = "省份信息", name = "province", required = true)
	private String province;


}
