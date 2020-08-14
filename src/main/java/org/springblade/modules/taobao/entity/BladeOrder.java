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
	 * 订单状态
	 */
	@ApiModelProperty(value = "订单状态")
	@TableField(value = "status", fill = FieldFill.INSERT_UPDATE)
	private Integer status;

}
