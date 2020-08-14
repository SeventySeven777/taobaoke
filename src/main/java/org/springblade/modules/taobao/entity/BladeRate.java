package org.springblade.modules.taobao.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

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
@ApiModel(value = "比率表")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BladeRate implements Serializable {

	/**
	 * 无意义自增ID
	 */
	@ApiModelProperty(value = "无意义自增ID")
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
	 * 比率
	 */
	@ApiModelProperty(value = "比率")
	@TableField(value = "rate", fill = FieldFill.INSERT_UPDATE)
	private BigDecimal rate;

}
