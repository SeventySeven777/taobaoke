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
@ApiModel(value = "平台帐号表")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BladeAdminAccount implements Serializable {

	/**
	 * 暂定UUID
	 */
	@ApiModelProperty(value = "暂定UUID")
	@TableField(value = "id", fill = FieldFill.INSERT_UPDATE)
	private String id;
	/**
	 * 平台帐号
	 */
	@ApiModelProperty(value = "平台帐号")
	@TableField(value = "account", fill = FieldFill.INSERT_UPDATE)
	private String account;

}
