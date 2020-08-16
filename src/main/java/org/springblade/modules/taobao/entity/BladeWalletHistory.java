package org.springblade.modules.taobao.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
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
@ApiModel(value = "钱变动历史表")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BladeWalletHistory implements Serializable {

	/**
	 * 自增无意义ID
	 */
	@ApiModelProperty(value = "自增无意义ID")
	@TableId(value = "id", type = IdType.AUTO)
	@TableField(value = "id", fill = FieldFill.INSERT_UPDATE)
	private Integer id;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	@TableField(value = "user_id", fill = FieldFill.INSERT_UPDATE)
	private String userId;
	/**
	 * 变更金额
	 */
	@ApiModelProperty(value = "变更金额")
	@TableField(value = "money_change", fill = FieldFill.INSERT_UPDATE)
	private BigDecimal moneyChange;
	/**
	 * 什么原因改变暂定 0 分成加钱 1 提现
	 */
	@ApiModelProperty(value = "什么原因改变 -1 管理员加钱 10 提现扣钱 7订单加钱")
	@TableField(value = "reason", fill = FieldFill.INSERT_UPDATE)
	private Integer reason;
	/**
	 * 扣完钱后多少钱
	 */
	@ApiModelProperty(value = "扣完钱后多少钱")
	@TableField(value = "then_money", fill = FieldFill.INSERT_UPDATE)
	private BigDecimal thenMoney;
	/**
	 * 扣钱时间
	 */
	@ApiModelProperty(value = "扣钱时间")
	@TableField(value = "create_time", fill = FieldFill.INSERT_UPDATE)
	private Date createTime;
	/**
	 * 该钱状态是否成功之类
	 */
	@ApiModelProperty(value = "该钱状态是否成功之类")
	@TableField(value = "status", fill = FieldFill.INSERT_UPDATE)
	private Integer status;

}
