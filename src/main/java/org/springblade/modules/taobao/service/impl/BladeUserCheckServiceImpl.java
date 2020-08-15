package org.springblade.modules.taobao.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.exception.SqlException;
import org.springblade.modules.taobao.entity.BladeUserCheck;
import org.springblade.modules.taobao.mapper.BladeUserCheckMapper;
import org.springblade.modules.taobao.mapper.BladeUserMapper;
import org.springblade.modules.taobao.service.IBladeUserCheckService;
import org.springblade.modules.taobao.service.IBladeUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springblade.modules.taobao.config.MethodConfig.*;

/**
 * <p>
 * 审核意见表 服务实现类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@Service
@AllArgsConstructor
public class BladeUserCheckServiceImpl extends ServiceImpl<BladeUserCheckMapper, BladeUserCheck> implements IBladeUserCheckService {
	private final BladeUserCheckMapper bladeUserCheckMapper;
	private final IBladeUserService iBladeUserService;

	/**
	 * 保存审核状态 先删除原有状态
	 *
	 * @param bladeUserCheck 审核信息
	 * @param status         审核状态 改变user的status
	 * @return 保存结果
	 */
	@Override
	@Transactional(rollbackFor = SqlException.class)
	public R<String> checkUserStatus(BladeUserCheck bladeUserCheck, Integer status) {
		deleteOldCheck(bladeUserCheck.getUserId());
		if (bladeUserCheckMapper.insert(bladeUserCheck) != 1) {
			throw new SqlException(SAVE_ERROR);
		}
		//改变status
		if (iBladeUserService.updateStatus(bladeUserCheck.getUserId(), status)) {
			throw new SqlException(SAVE_ERROR);
		}
		return R.success(SAVE_OK);
	}

	@Override
	public R<String> getUserCheckOpinion(String userId) {
		BladeUserCheck bladeUserCheck = bladeUserCheckMapper.selectOne(Wrappers.<BladeUserCheck>query().lambda()
			.eq(BladeUserCheck::getUserId, userId));
		if (null == bladeUserCheck) {
			return R.data(NO_QUERY_USER);
		}
		return R.data(bladeUserCheck.getCheckOpinion());
	}

	/**
	 * 删除原有审核数据
	 *
	 * @param userId 用户ID
	 */
	private void deleteOldCheck(String userId) {
		if (bladeUserCheckMapper.delete(Wrappers.<BladeUserCheck>query().lambda().eq(BladeUserCheck::getUserId, userId)) != 1) {
			throw new SqlException(SAVE_ERROR);
		}
	}
}
