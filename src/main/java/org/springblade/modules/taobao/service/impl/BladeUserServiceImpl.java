package org.springblade.modules.taobao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.modules.taobao.entity.BladeUser;
import org.springblade.modules.taobao.mapper.BladeUserMapper;
import org.springblade.modules.taobao.service.IBladeUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 平台用户表 服务实现类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@Service
public class BladeUserServiceImpl extends ServiceImpl<BladeUserMapper, BladeUser> implements IBladeUserService {

}
