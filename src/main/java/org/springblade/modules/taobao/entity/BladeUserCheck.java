package org.springblade.modules.taobao.entity;

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
@ApiModel(value = "审核意见表")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BladeUserCheck implements Serializable {

	/**
	 * 无意义自增主键
	 */
	@ApiModelProperty(value = "无意义自增主键")
	@TableId(value = "id", type = IdType.AUTO)
	@TableField(value = "id", fill = FieldFill.INSERT_UPDATE)
	private Integer id;
	/**
	 * 实际用户id
	 */
	@ApiModelProperty(value = "实际用户id")
	@TableField(value = "user_id", fill = FieldFill.INSERT_UPDATE)
	private String userId;
	/**
	 * 审核意见
	 */
	@ApiModelProperty(value = "审核意见")
	@TableField(value = "check_opinion", fill = FieldFill.INSERT_UPDATE)
	private String checkOpinion;
	/**
	 * 发起时间
	 */
	@ApiModelProperty(value = "发起时间")
	@TableField(value = "create_time", fill = FieldFill.INSERT_UPDATE)
	private Date createTime;

}
