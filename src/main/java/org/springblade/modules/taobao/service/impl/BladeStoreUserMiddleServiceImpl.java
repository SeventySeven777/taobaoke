package org.springblade.modules.taobao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.modules.taobao.entity.BladeStoreUserMiddle;
import org.springblade.modules.taobao.mapper.BladeStoreUserMiddleMapper;
import org.springblade.modules.taobao.service.IBladeStoreUserMiddleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 店铺关联表 服务实现类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@Service
public class BladeStoreUserMiddleServiceImpl extends ServiceImpl<BladeStoreUserMiddleMapper, BladeStoreUserMiddle> implements IBladeStoreUserMiddleService {

	@Override
	public Boolean createLine(String managerId, String storeId) {
		return this.save(new BladeStoreUserMiddle().setUserId(managerId).setStoreId(storeId));
	}
}
