package org.springblade.modules.taobao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author SeventySeven
 * @since 2020-08-14
 */
@ApiModel(value = "店铺详细表")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BladeUserStore implements Serializable {

	/**
	 * UUID64位对应userId
	 */
	@ApiModelProperty(value = "UUID64位对应userId")
	@TableField(value = "id", fill = FieldFill.INSERT_UPDATE)
	private String id;
	/**
	 * 店铺名称
	 */
	@ApiModelProperty(value = "店铺名称")
	@TableField(value = "store_name", fill = FieldFill.INSERT_UPDATE)
	private String storeName;
	/**
	 * 店铺负责人
	 */
	@ApiModelProperty(value = "店铺负责人")
	@TableField(value = "store_human", fill = FieldFill.INSERT_UPDATE)
	private String storeHuman;
	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号")
	@TableField(value = "phone", fill = FieldFill.INSERT_UPDATE)
	private String phone;
	/**
	 * 地址
	 */
	@ApiModelProperty(value = "地址")
	@TableField(value = "address", fill = FieldFill.INSERT_UPDATE)
	private String address;
	/**
	 * 经度
	 */
	@ApiModelProperty(value = "经度")
	@TableField(value = "longitude", fill = FieldFill.INSERT_UPDATE)
	private String longitude;
	/**
	 * 维度
	 */
	@ApiModelProperty(value = "维度")
	@TableField(value = "latitude", fill = FieldFill.INSERT_UPDATE)
	private String latitude;
	/**
	 * 二维码
	 */
	@ApiModelProperty(value = "二维码")
	@TableField(value = "q_r_code", fill = FieldFill.INSERT_UPDATE)
	private String qRCode;
	/**
	 * 图片
	 */
	@ApiModelProperty(value = "图片")
	@TableField(value = "image", fill = FieldFill.INSERT_UPDATE)
	private String image;
	/**
	 * 图片
	 */
	@ApiModelProperty(value = "支付宝帐号")
	@TableField(value = "pay_number", fill = FieldFill.INSERT_UPDATE)
	private String payNumber;

}
