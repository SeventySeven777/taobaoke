package org.springblade.modules.taobao.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.secure.AuthInfo;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.InitStoreDTO;
import org.springblade.modules.taobao.dto.InitUserDTO;
import org.springblade.modules.taobao.dto.LoginUserDTO;
import org.springblade.modules.taobao.entity.BladeUser;
import org.springblade.modules.taobao.entity.BladeUserBash;
import org.springblade.modules.taobao.entity.BladeUserStore;

import java.util.List;

/**
 * <p>
 * 平台用户表 服务类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
public interface IBladeUserService extends IService<BladeUser> {

	/**
	 * 用户登录 用户登录返回用户信息+token 管理员登录返回token
	 *
	 * @param loginUserDTO 登录DTO
	 * @return token
	 */
	R<AuthInfo> login(LoginUserDTO loginUserDTO);

	/**
	 * 判断用户手机号是否存在
	 *
	 * @param phone 手机
	 * @return isOk
	 */
	Boolean examineUserPhone(String phone);

	/**
	 * 初始化用户相关表数据
	 * 包括账号 基础信息 钱包 等
	 *
	 * @param bladeUserBash 用户基础数据
	 * @return user
	 */
	R<BladeUser> initUserAll(BladeUserBash bladeUserBash);

	/**
	 * 判断用户是否存在通过userID
	 *
	 * @param userId 用户id
	 * @return isOK true 不存在 false 存在
	 */
	Boolean examineUser(String userId);

	/**
	 * 初始化店铺
	 *
	 * @param bladeUserStore 店铺
	 * @return 店铺
	 */
	R<BladeUserStore> initStore(BladeUserStore bladeUserStore,String province);

	/**
	 * 判断密码是否正确
	 *
	 * @param password    password
	 * @param passwordOld passwordOld
	 * @return isOk
	 */
	Boolean whetherPassword(String password, String passwordOld);

	/**
	 * 修改用户密码
	 *
	 * @param bladeUser   user
	 * @param passwordNew password
	 * @return isOK
	 */
	R<String> setPassword(BladeUser bladeUser, String passwordNew);

	/**
	 * 通过审核状态查询role的ids
	 *
	 * @param status        审核状态
	 * @param size          分页
	 * @param current       分页
	 * @param managerNumber 查什么role
	 * @return 最后一位为total
	 */
	List<String> getUserIdsByStatus(Integer status, Integer size, Integer current, Integer managerNumber);

	/**
	 * @param status        状态
	 * @param size          size
	 * @param current       current
	 * @param managerNumber 经理ID
	 * @param filter        条件
	 * @return ids
	 */
	List<String> getUserIdsByStatus(Integer status, Integer size, Integer current, Integer managerNumber, String filter);

	/**
	 * 修改用户 审核状态
	 *
	 * @param userId 用户ID
	 * @param status 审核状态
	 * @return true 修改失败 false 修改成功
	 */
	Boolean updateStatus(String userId, Integer status);

	/**
	 * 获取所有用户IDS
	 *
	 * @param size    1
	 * @param current 1
	 * @param role    1
	 * @param status  1
	 * @return 1
	 */
	List<String> getAllUserIds(Integer size, Integer current, Integer role, Integer status);

	/**
	 * 获取区域经理审核过,分页数据
	 *
	 * @param size    分页
	 * @param current 分页
	 * @return 分页DATA
	 */
	R<Page<BladeUser>> getManagerPage(Integer size, Integer current);

	/**
	 * 管理员登录
	 *
	 * @param loginUserDTO 登录DTO
	 * @return token
	 */
	R<AuthInfo> loginAdmin(LoginUserDTO loginUserDTO);

	/**
	 * 解决乱码test01
	 *
	 * @param initStoreDTO code
	 * @return decode
	 */
	BladeUserStore deCode(InitStoreDTO initStoreDTO);

	/**
	 * 解决乱码 test02
	 *
	 * @param initUserDTO code
	 * @return decode
	 */
	BladeUserBash deCode(InitUserDTO initUserDTO);

}
