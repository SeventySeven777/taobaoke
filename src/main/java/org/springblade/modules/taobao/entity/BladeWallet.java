package org.springblade.modules.taobao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author SeventySeven
 * @since 2020-08-14
 */
@ApiModel(value = "用户钱包")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BladeWallet implements Serializable {

	/**
	 * UUID64对应用户id
	 */
	@ApiModelProperty(value = "UUID64对应用户id")
	@TableField(value = "id", fill = FieldFill.INSERT_UPDATE)
	private String id;
	/**
	 * 钱
	 */
	@ApiModelProperty(value = "钱")
	@TableField(value = "money", fill = FieldFill.INSERT_UPDATE)
	private BigDecimal money;
	/**
	 * 历史总钱
	 */
	@ApiModelProperty(value = "历史总钱")
	@TableField(value = "history_money_all", fill = FieldFill.INSERT_UPDATE)
	private BigDecimal historyMoneyAll;
	/**
	 * 提现总钱
	 */
	@ApiModelProperty(value = "提现总钱")
	@TableField(value = "conversion_money_all", fill = FieldFill.INSERT_UPDATE)
	private BigDecimal conversionMoneyAll;

}
