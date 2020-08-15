package org.springblade.modules.taobao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.entity.BladeStoreUserMiddle;

/**
 * <p>
 * 店铺关联表 服务类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
public interface IBladeStoreUserMiddleService extends IService<BladeStoreUserMiddle> {

	/**
	 * 关联店铺及区域经理
	 *
	 * @param managerId
	 * @param storeId
	 * @return
	 */
	Boolean createLine(String managerId, String storeId);

	/**
	 * 获取门店总数
	 *
	 * @param userId userId
	 * @return total
	 */
	Integer getMiddleNumber(String userId);

	/**
	 * 获取区域经理name
	 *
	 * @param storeId storeId
	 * @return name
	 */
	String getManageName(String storeId);

	/**
	 * 修改负责人
	 *
	 * @param userId  id
	 * @param storeId id
	 * @return 成功
	 */
	R<String> updateManager(String userId, String storeId);
}
