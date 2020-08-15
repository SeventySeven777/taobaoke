package org.springblade.modules.taobao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.entity.BladeUser;
import org.springblade.modules.taobao.entity.BladeUserCheck;
import org.springblade.modules.taobao.mapper.BladeUserCheckMapper;
import org.springblade.modules.taobao.service.IBladeUserCheckService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 审核意见表 服务实现类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@Service
@AllArgsConstructor
public class BladeUserCheckServiceImpl extends ServiceImpl<BladeUserCheckMapper, BladeUserCheck> implements IBladeUserCheckService {
	private BladeUserCheckMapper bladeUserCheckMapper;

}
