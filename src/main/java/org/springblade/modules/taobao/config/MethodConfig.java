package org.springblade.modules.taobao.config;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * method配置
 * 方便找统一放一起
 * @author SeventySeven
 * @since 2020-08-14
 */
@ApiModel(value = "method配置")
@Accessors(chain = true)
public interface MethodConfig {
	String FIELD_MISSING = "参数为空或不全,请检查参数";


}
