package org.springblade.modules.taobao.service;

import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.entity.BladeUser;

/**
 * 获取用户基础数据
 *
 * @author SeventySeven
 * @since 2020-08-26
 */
public interface SessionService {
	/**
	 * 获取用户数据
	 *
	 * @return 返回用户
	 */
	R<BladeUser> getUser();

	/**
	 * 返回是否是admin
	 * @return 是否是admin
	 */
	R<Boolean> getAdmin();
}
