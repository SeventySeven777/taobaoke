package org.springblade.modules.taobao.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.exception.SqlException;
import org.springblade.modules.taobao.entity.*;
import org.springblade.modules.taobao.mapper.*;
import org.springblade.modules.taobao.service.IBladeUserStoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.springblade.modules.taobao.config.BashNumberInterface.*;
import static org.springblade.modules.taobao.config.MethodConfig.DELETE_OK;
import static org.springblade.modules.taobao.config.MethodConfig.SAVE_ERROR;

/**
 * <p>
 * 店铺详细表 服务实现类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@Service
@AllArgsConstructor
public class BladeUserStoreServiceImpl extends ServiceImpl<BladeUserStoreMapper, BladeUserStore> implements IBladeUserStoreService {
	private final BladeUserMapper bladeUserMapper;
	private final BladeWalletMapper bladeWalletMapper;
	private final BladeWalletHistoryMapper bladeWalletHistoryMapper;
	private final BladeStoreUserMiddleMapper bladeStoreUserMiddleMapper;
	private final BladeRateMapper bladeRateMapper;


	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<String> deleteStore(String storeId) {
		bladeUserMapper.deleteById(storeId);
		bladeWalletMapper.deleteById(storeId);
		bladeWalletHistoryMapper.delete(Wrappers.<BladeWalletHistory>query().lambda().eq(BladeWalletHistory::getUserId, storeId));
		bladeStoreUserMiddleMapper.delete(Wrappers.<BladeStoreUserMiddle>query().lambda().eq(BladeStoreUserMiddle::getStoreId, storeId));
		bladeRateMapper.delete(Wrappers.<BladeRate>query().lambda().eq(BladeRate::getUserId, storeId));
		this.remove(Wrappers.<BladeUserStore>query().lambda().eq(BladeUserStore::getId, storeId));
		return R.success(DELETE_OK);
	}
}
