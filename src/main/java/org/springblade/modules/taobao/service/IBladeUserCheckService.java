package org.springblade.modules.taobao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.entity.BladeUser;
import org.springblade.modules.taobao.entity.BladeUserCheck;

/**
 * <p>
 * 审核意见表 服务类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
public interface IBladeUserCheckService extends IService<BladeUserCheck> {


	/**
	 * 保存审核信息 先删除原有审核信息在保存
	 *
	 * @param bladeUserCheck 审核信息
	 * @param status         审核状态 改变user的status
	 * @return 保存结果
	 */
	R<String> checkUserStatus(BladeUserCheck bladeUserCheck, Integer status);

	/**
	 * 获取用户审核原因
	 *
	 * @param userId 用户ID
	 * @return 原因
	 */
	R<String> getUserCheckOpinion(String userId);
}
