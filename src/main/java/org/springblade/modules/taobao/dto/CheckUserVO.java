package org.springblade.modules.taobao.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用于返回审核用户
 *
 * @author SeventySeven
 * @since 2020-08-15
 */
@ApiModel(value = "审核用户VO")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CheckUserVO {
	/**
	 * UUID64位用户ID
	 */
	@ApiModelProperty(value = "UUID64位用户ID")
	private String id;
	/**
	 * 用户姓名
	 */
	@ApiModelProperty(value = "用户姓名")
	private String userName;
	/**
	 * 性别
	 */
	@ApiModelProperty(value = "性别")
	private Integer userSex;
	/**
	 * 年龄
	 */
	@ApiModelProperty(value = "年龄")
	private Integer userAge;
	/**
	 * 学历
	 */
	@ApiModelProperty(value = "学历")
	private String education;
	/**
	 * 毕业院校
	 */
	@ApiModelProperty(value = "毕业院校")
	private String school;
	/**
	 * 工作年限
	 */
	@ApiModelProperty(value = "工作年限")
	private Integer workYear;
	/**
	 * 简历URL
	 */
	@ApiModelProperty(value = "简历URL")
	private String resumeUrl;
	/**
	 * 居住地址
	 */
	@ApiModelProperty(value = "居住地址")
	private String address;
	/**
	 * 手机号对应登录账号
	 */
	@ApiModelProperty(value = "手机号对应登录账号")
	private String phone;
	/**
	 * 个人简介
	 */
	@ApiModelProperty(value = "个人简介")
	private String individualResume;
	/**
	 * 身份证正面
	 */
	@ApiModelProperty(value = "身份证正面")
	private String identityImageFront;
	/**
	 * 身份证反面
	 */
	@ApiModelProperty(value = "身份证反面")
	private String identityImageVerso;
	/**
	 * 学历照片
	 */
	@ApiModelProperty(value = "学历照片")
	private String educationImage;

	/**
	 * 支付宝账号用于后期提现
	 */
	@ApiModelProperty(value = "支付宝账号用于后期提现")
	private String alipay;

	/**
	 * 申请时间
	 */
	@ApiModelProperty(value = "申请时间")
	private Long checkTime;


}
