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
@ApiModel(value = "平台工作人员基础信息表")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BladeUserBash implements Serializable {

	/**
	 * UUID64位用户ID
	 */
	@ApiModelProperty(value = "UUID64位用户ID")
	@TableField(value = "id", fill = FieldFill.INSERT_UPDATE)
	private String id;
	/**
	 * 用户姓名
	 */
	@ApiModelProperty(value = "用户姓名")
	@TableField(value = "user_name", fill = FieldFill.INSERT_UPDATE)
	private String userName;
	/**
	 * 性别
	 */
	@ApiModelProperty(value = "性别")
	@TableField(value = "user_sex", fill = FieldFill.INSERT_UPDATE)
	private Integer userSex;
	/**
	 * 年龄
	 */
	@ApiModelProperty(value = "年龄")
	@TableField(value = "user_age", fill = FieldFill.INSERT_UPDATE)
	private Integer userAge;
	/**
	 * 学历
	 */
	@ApiModelProperty(value = "学历")
	@TableField(value = "education", fill = FieldFill.INSERT_UPDATE)
	private String education;
	/**
	 * 毕业院校
	 */
	@ApiModelProperty(value = "毕业院校")
	@TableField(value = "school", fill = FieldFill.INSERT_UPDATE)
	private String school;
	/**
	 * 工作年限
	 */
	@ApiModelProperty(value = "工作年限")
	@TableField(value = "work_year", fill = FieldFill.INSERT_UPDATE)
	private Integer workYear;
	/**
	 * 简历URL
	 */
	@ApiModelProperty(value = "简历URL")
	@TableField(value = "resume_url", fill = FieldFill.INSERT_UPDATE)
	private String resumeUrl;
	/**
	 * 居住地址
	 */
	@ApiModelProperty(value = "居住地址")
	@TableField(value = "address", fill = FieldFill.INSERT_UPDATE)
	private String address;
	/**
	 * 手机号对应登录账号
	 */
	@ApiModelProperty(value = "手机号对应登录账号")
	@TableField(value = "phone", fill = FieldFill.INSERT_UPDATE)
	private String phone;
	/**
	 * 支付宝账号用于后期提现
	 */
	@ApiModelProperty(value = "支付宝账号用于后期提现")
	@TableField(value = "alipay", fill = FieldFill.INSERT_UPDATE)
	private String alipay;
	/**
	 * 个人简介
	 */
	@ApiModelProperty(value = "个人简介")
	@TableField(value = "individual_resume", fill = FieldFill.INSERT_UPDATE)
	private String individualResume;
	/**
	 * 身份证正面
	 */
	@ApiModelProperty(value = "身份证正面")
	@TableField(value = "identity_image_front", fill = FieldFill.INSERT_UPDATE)
	private String identityImageFront;
	/**
	 * 身份证反面
	 */
	@ApiModelProperty(value = "身份证反面")
	@TableField(value = "identity_image_verso", fill = FieldFill.INSERT_UPDATE)
	private String identityImageVerso;
	/**
	 * 学历照片
	 */
	@ApiModelProperty(value = "学历照片")
	@TableField(value = "education_image", fill = FieldFill.INSERT_UPDATE)
	private String educationImage;

}
