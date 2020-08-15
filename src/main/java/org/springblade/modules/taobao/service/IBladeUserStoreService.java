package org.springblade.modules.taobao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.entity.BladeUserStore;

/**
 * <p>
 * 店铺详细表 服务类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
public interface IBladeUserStoreService extends IService<BladeUserStore> {


	/**
	 * 删除一切
	 *
	 * @param storeId 门店ID
	 * @return 成功
	 */
	R<String> deleteStore(String storeId);
}
