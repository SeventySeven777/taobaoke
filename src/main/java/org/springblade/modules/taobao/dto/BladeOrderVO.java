package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * order VO
 *
 * @author SeventySeven
 * @since 2020-08-15
 */
@ApiModel(value = "order VO")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BladeOrderVO {
	/**
	 * 订单ID 暂定同步淘宝下拉
	 */
	@ApiModelProperty(value = "订单ID 暂定同步淘宝下拉")
	private String id;

	/**
	 * 淘宝Id
	 */
	@ApiModelProperty(value = "淘宝Id")
	private String orderAliId;

	/**
	 * 订单创建时间
	 */
	@ApiModelProperty(value = "订单创建时间")
	private Date createTime;

	/**
	 * 商品数量
	 */
	@ApiModelProperty(value = "商品数量")
	private Integer quantity;

	/**
	 * 支付金额
	 */
	@ApiModelProperty(value = "支付金额")
	private BigDecimal payMoney;

	/**
	 * 店铺ID
	 */
	@ApiModelProperty(value = "店铺ID")
	private String storeId;

	/**
	 * 店铺Name
	 */
	@ApiModelProperty(value = "店铺Name")
	private String storeName;

	/**
	 * 经理ID
	 */
	@ApiModelProperty(value = "经理ID")
	private String managerId;

	/**
	 * 经理Name
	 */
	@ApiModelProperty(value = "经理Name")
	private String managerName;

	/**
	 * 店铺分红金额
	 */
	@ApiModelProperty(value = "店铺分红金额")
	private BigDecimal storeRateMoney;

	/**
	 * 经理分红金额
	 */
	@ApiModelProperty(value = "经理分红金额")
	private BigDecimal managerRateMoney;

	/**
	 * 是否过期可提现
	 */
	@ApiModelProperty(value = "是否过期可提现")
	private Date pastTime;

	/**
	 * 订单状态
	 */
	@ApiModelProperty(value = "订单状态")
	private Integer status;
}
