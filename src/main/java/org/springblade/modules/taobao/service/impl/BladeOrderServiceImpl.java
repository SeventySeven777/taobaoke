package org.springblade.modules.taobao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.modules.taobao.entity.BladeOrder;
import org.springblade.modules.taobao.mapper.BladeOrderMapper;
import org.springblade.modules.taobao.service.IBladeOrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@Service
public class BladeOrderServiceImpl extends ServiceImpl<BladeOrderMapper, BladeOrder> implements IBladeOrderService {

}
