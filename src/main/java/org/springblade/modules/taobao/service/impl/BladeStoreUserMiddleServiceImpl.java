package org.springblade.modules.taobao.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.entity.BladeStoreUserMiddle;
import org.springblade.modules.taobao.mapper.BladeStoreUserMiddleMapper;
import org.springblade.modules.taobao.mapper.BladeUserBashMapper;
import org.springblade.modules.taobao.mapper.BladeUserMapper;
import org.springblade.modules.taobao.service.IBladeStoreUserMiddleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springblade.modules.taobao.config.BashNumberInterface.NO_MANAGER;
import static org.springblade.modules.taobao.config.BashNumberInterface.NUMBER_ONE;
import static org.springblade.modules.taobao.config.MethodConfig.SAVE_OK;

/**
 * <p>
 * 店铺关联表 服务实现类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@Service
@AllArgsConstructor
public class BladeStoreUserMiddleServiceImpl extends ServiceImpl<BladeStoreUserMiddleMapper, BladeStoreUserMiddle> implements IBladeStoreUserMiddleService {
	private final BladeUserBashMapper bladeUserBashMapper;
	private final BladeStoreUserMiddleMapper bladeStoreUserMiddleMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean createLine(String managerId, String storeId) {
		return this.save(new BladeStoreUserMiddle().setUserId(managerId).setStoreId(storeId));
	}

	@Override
	public Integer getMiddleNumber(String userId) {
		return this.count(Wrappers.<BladeStoreUserMiddle>query().lambda().eq(BladeStoreUserMiddle::getUserId, userId));
	}

	@Override
	public String getManageName(String storeId) {
		BladeStoreUserMiddle one = this.getOne(Wrappers.<BladeStoreUserMiddle>query().lambda().eq(BladeStoreUserMiddle::getStoreId, storeId));
		if (one.getUserId().equals(NUMBER_ONE)) {
			return NO_MANAGER;
		}
		return bladeUserBashMapper.selectById(one.getUserId()).getUserName();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<String> updateManager(String userId, String storeId) {
		bladeStoreUserMiddleMapper.updateByDelete(userId, storeId);
		return R.success(SAVE_OK);
	}
}
