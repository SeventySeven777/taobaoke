package org.springblade.modules.taobao.entity;

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
@ApiModel(value = "店铺关联表")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BladeStoreUserMiddle implements Serializable {

	/**
	 * 无意义自增Id
	 */
	@ApiModelProperty(value = "无意义自增Id")
	@TableId(value = "id", type = IdType.AUTO)
	@TableField(value = "id", fill = FieldFill.INSERT_UPDATE)
	private Integer id;
	/**
	 * 工作人员Id
	 */
	@ApiModelProperty(value = "工作人员Id")
	@TableField(value = "user_id", fill = FieldFill.INSERT_UPDATE)
	private String userId;
	/**
	 * 店铺ID
	 */
	@ApiModelProperty(value = "店铺ID")
	@TableField(value = "store_id", fill = FieldFill.INSERT_UPDATE)
	private String storeId;

}
