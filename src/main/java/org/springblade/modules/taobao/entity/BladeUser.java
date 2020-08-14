package org.springblade.modules.taobao.entity;

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
@ApiModel(value = "平台用户表")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BladeUser implements Serializable {

	/**
	 * UUID64位对应用户基础表ID
	 */
	@ApiModelProperty(value = "UUID64位对应用户基础表ID")
	@TableField(value = "id", fill = FieldFill.INSERT_UPDATE)
	private String id;
	/**
	 * 账号对应基础信息手机号
	 */
	@ApiModelProperty(value = "账号对应基础信息手机号")
	@TableField(value = "phone", fill = FieldFill.INSERT_UPDATE)
	private String phone;
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	@TableField(value = "password", fill = FieldFill.INSERT_UPDATE)
	private String password;
	/**
	 * 角色-管理员-1 商家 2 工作人员 3
	 */
	@ApiModelProperty(value = "角色-管理员-1 商家 2 工作人员 3")
	@TableField(value = "role", fill = FieldFill.INSERT_UPDATE)
	private Integer role;
	/**
	 * 审核状态 1为不通过 -1 管理员 0待审 2 通过
	 */
	@ApiModelProperty(value = "审核状态 1为不通过 -1 管理员 0待审 2 通过")
	@TableField(value = "status", fill = FieldFill.INSERT_UPDATE)
	private Integer status;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField(value = "create_date", fill = FieldFill.INSERT_UPDATE)
	private Date createDate;

}
