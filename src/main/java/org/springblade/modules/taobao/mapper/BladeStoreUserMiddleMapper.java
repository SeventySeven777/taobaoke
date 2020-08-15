package org.springblade.modules.taobao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import org.springblade.modules.taobao.entity.BladeStoreUserMiddle;

/**
 * <p>
 * 店铺关联表 Mapper 接口
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
public interface BladeStoreUserMiddleMapper extends BaseMapper<BladeStoreUserMiddle> {

	/**
	 * 手改
	 *
	 * @param numberOne 1
	 * @param userId    userID
	 */
	@Update("update blade_store_user_middle set user_id = #{numberOne} where user_id = #{userId}")
	void updateByDelete(String numberOne, String userId);

}
