package org.springblade.modules.taobao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.ManagerStoreAll;
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

	/**
	 * 返回经理查询对象
	 * @param userId 用户id
	 * @param size size
	 * @param current current
	 * @param date time
	 * @return result
	 */
    R<ManagerStoreAll> getManagerPage(String userId,Integer size, Integer current, String date);

	/**
	 * 返回经理查询对象有1状态
	 * @param id id
	 * @param size size
	 * @param current current
	 * @param date time
	 * @param status 状态
	 * @return result
	 */
	R<ManagerStoreAll> getManagerPage(String id, Integer size, Integer current, String date, String status);
}
