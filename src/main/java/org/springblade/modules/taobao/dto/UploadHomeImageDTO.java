package org.springblade.modules.taobao.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 主页图片URl
 *
 * @author SeventySeven
 * @since 2020-08-15
 */
@ApiModel(value = "主页图片URl")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UploadHomeImageDTO {
	@ApiModelProperty(value = "主页图片URl", name = "homeImage", required = true)
	private String homeImage;
}
