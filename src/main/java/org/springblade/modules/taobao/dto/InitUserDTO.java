package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用于用户注册
 * @author SeventySeven
 * @since 2020-08-15
 */
@ApiModel(value = "用户注册DTO")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InitUserDTO {
	/**
	 * 用户姓名
	 */
	@ApiModelProperty(value = "用户姓名",name = "userName",required = true)
	private String userName;
	/**
	 * 性别
	 */
	@ApiModelProperty(value = "性别",name = "userSex",required = true)
	private Integer userSex;
	/**
	 * 年龄
	 */
	@ApiModelProperty(value = "年龄",name = "userAge",required = true)
	private Integer userAge;
	/**
	 * 学历
	 */
	@ApiModelProperty(value = "学历",name = "education",required = true)
	private String education;
	/**
	 * 毕业院校
	 */
	@ApiModelProperty(value = "毕业院校",name = "school",required = true)
	private String school;
	/**
	 * 工作年限
	 */
	@ApiModelProperty(value = "工作年限",name = "workYear",required = true)
	private String workYear;
	/**
	 * 简历URL
	 */
	@ApiModelProperty(value = "简历URL",name = "resumeUrl",required = true)
	private String resumeUrl;
	/**
	 * 居住地址
	 */
	@ApiModelProperty(value = "居住地址",name = "address",required = true)
	private String address;
	/**
	 * 手机号对应登录账号
	 */
	@ApiModelProperty(value = "手机号对应登录账号",name = "phone",required = true)
	private String phone;
	/**
	 * 支付宝账号用于后期提现
	 */
	@ApiModelProperty(value = "支付宝账号用于后期提现",name = "alipay",required = true)
	private String alipay;
	/**
	 * 个人简介
	 */
	@ApiModelProperty(value = "个人简介",name = "individualResume",required = true)
	private String individualResume;
	/**
	 * 身份证正面
	 */
	@ApiModelProperty(value = "身份证正面",name = "identityImageFront",required = true)
	private String identityImageFront;
	/**
	 * 身份证反面
	 */
	@ApiModelProperty(value = "身份证反面",name = "identityImageVerso",required = true)
	private String identityImageVerso;
	/**
	 * 学历照片
	 */
	@ApiModelProperty(value = "学历照片",name = "educationImage",required = true)
	private String educationImage;
}
