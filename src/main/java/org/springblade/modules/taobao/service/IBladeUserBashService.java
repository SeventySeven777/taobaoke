package org.springblade.modules.taobao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.CheckUserResultVO;
import org.springblade.modules.taobao.entity.BladeUserBash;

import java.util.List;

/**
 * <p>
 * 平台工作人员基础信息表 服务类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
public interface IBladeUserBashService extends IService<BladeUserBash> {

	/**
	 * 返回分页后的用户基础信息
	 *
	 * @param userIds 用户IDs 最后一位为total
	 * @param size    分页
	 * @param current 分页
	 * @param role    角色信息
	 * @return 分页后的用户基础信息
	 */
	R<Object> getUserByIds(List<String> userIds, Integer size, Integer current, Integer role);

	/**
	 * 获取用户IDS
	 *
	 * @param what    手机号或名字
	 * @param size    分页
	 * @param current 分页
	 * @param role    角色
	 * @param status  状态
	 * @return ids
	 */
	List<String> getUserIdBySomething(String what, Integer size, Integer current, Integer role, Integer status, List<String> ids);

	/**
	 * 删除账号及其所有数据
	 *
	 * @param userId userID
	 * @return 成功
	 */
	R<String> deleteUser(String userId);
}
