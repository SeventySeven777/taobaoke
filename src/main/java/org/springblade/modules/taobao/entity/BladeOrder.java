package org.springblade.modules.taobao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
@ApiModel(value = "订单表")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BladeOrder implements Serializable {

	/**
	 * 订单ID 暂定同步淘宝下拉
	 */
	@ApiModelProperty(value = "订单ID 暂定同步淘宝下拉")
	@TableField(value = "id", fill = FieldFill.INSERT_UPDATE)
	private String id;

	/**
	 * 淘宝Id
	 */
	@ApiModelProperty(value = "淘宝Id")
	@TableField(value = "order_ali_id", fill = FieldFill.INSERT_UPDATE)
	private String orderAliId;

	/**
	 * 订单创建时间
	 */
	@ApiModelProperty(value = "订单创建时间")
	@TableField(value = "create_time", fill = FieldFill.INSERT_UPDATE)
	private Date createTime;

	/**
	 * 商品数量
	 */
	@ApiModelProperty(value = "商品数量")
	@TableField(value = "quantity", fill = FieldFill.INSERT_UPDATE)
	private Integer quantity;

	/**
	 * 支付金额
	 */
	@ApiModelProperty(value = "支付金额")
	@TableField(value = "pay_money", fill = FieldFill.INSERT_UPDATE)
	private BigDecimal payMoney;

	/**
	 * 店铺ID
	 */
	@ApiModelProperty(value = "店铺ID")
	@TableField(value = "store_id", fill = FieldFill.INSERT_UPDATE)
	private String storeId;

	/**
	 * 经理ID
	 */
	@ApiModelProperty(value = "经理ID")
	@TableField(value = "manager_id", fill = FieldFill.INSERT_UPDATE)
	private String managerId;

	/**
	 * 店铺分红金额
	 */
	@ApiModelProperty(value = "店铺分红金额")
	@TableField(value = "store_rate_money", fill = FieldFill.INSERT_UPDATE)
	private BigDecimal storeRateMoney;

	/**
	 * 经理分红金额
	 */
	@ApiModelProperty(value = "经理分红金额")
	@TableField(value = "manager_rate_money", fill = FieldFill.INSERT_UPDATE)
	private BigDecimal managerRateMoney;

	/**
	 * 是否过期可提现
	 */
	@ApiModelProperty(value = "是否过期可提现")
	@TableField(value = "past_time", fill = FieldFill.INSERT_UPDATE)
	private Long pastTime;

	/**
	 * 订单状态
	 */
	@ApiModelProperty(value = "订单状态")
	@TableField(value = "status", fill = FieldFill.INSERT_UPDATE)
	private Integer status;
}
