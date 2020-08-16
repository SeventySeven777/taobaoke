package org.springblade.modules.taobao.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.CheckUserResultVO;
import org.springblade.modules.taobao.dto.CheckUserVO;
import org.springblade.modules.taobao.entity.BladeUserBash;
import org.springblade.modules.taobao.entity.BladeUserCheck;
import org.springblade.modules.taobao.entity.BladeWalletHistory;
import org.springblade.modules.taobao.mapper.*;
import org.springblade.modules.taobao.service.IBladeUserBashService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springblade.modules.taobao.config.BashNumberInterface.NUMBER_ONE;
import static org.springblade.modules.taobao.config.MethodConfig.DELETE_OK;

/**
 * <p>
 * 平台工作人员基础信息表 服务实现类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@Service
@AllArgsConstructor
public class BladeUserBashServiceImpl extends ServiceImpl<BladeUserBashMapper, BladeUserBash> implements IBladeUserBashService {
	private final BladeUserBashMapper bladeUserBashMapper;
	private final BladeUserMapper bladeUserMapper;
	private final BladeWalletMapper bladeWalletMapper;
	private final BladeWalletHistoryMapper bladeWalletHistoryMapper;
	private final BladeStoreUserMiddleMapper bladeStoreUserMiddleMapper;
	private final BladeRateMapper bladeRateMapper;
	private final BladeUserCheckMapper bladeUserCheckMapper;

	/**
	 * 返回分页后的用户基础信息
	 *
	 * @param userIds 用户IDs 最后一位为total
	 * @param size    分页
	 * @param current 分页
	 * @return 分页后的用户基础信息
	 */
	@Override
	public R<CheckUserResultVO> getUserByIds(List<String> userIds, Integer size, Integer current) {
		String stringTotal = userIds.get(userIds.size() - 1);
		//删除total 本来想删除total的由于为空时list为空下方会报错于是留着total了反正不影响
		List<BladeUserBash> bladeUserBashes = bladeUserBashMapper.selectList(Wrappers.<BladeUserBash>query().lambda()
			.in(BladeUserBash::getId, userIds));
		List<CheckUserVO> voList = new ArrayList<>();
		bladeUserBashes.forEach(item -> {
			CheckUserVO checkUserVO = new CheckUserVO();
			BeanUtil.copyProperties(item, checkUserVO);
			BladeUserCheck bladeUserCheck = bladeUserCheckMapper.selectOne(Wrappers.<BladeUserCheck>query().lambda()
				.eq(BladeUserCheck::getUserId, item.getId()));
			if (null != bladeUserCheck) {
				checkUserVO.setCheckTime(bladeUserCheck.getCreateTime().getTime());
			}
			voList.add(checkUserVO);
		});
		CheckUserResultVO checkUserResultVO = new CheckUserResultVO().setSize(size).setCurrent(current)
			.setTotal(Long.valueOf(stringTotal)).setList(voList);
		return R.data(checkUserResultVO);
	}

	/**
	 * 老规矩 list最后一位为total
	 *
	 * @param what    手机号或名字
	 * @param size    分页
	 * @param current 分页
	 * @return ids
	 */
	@Override
	public List<String> getUserIdBySomething(String what, Integer size, Integer current, Integer role, Integer status, List<String> ids) {
		Page<BladeUserBash> bladeUserBashPage = bladeUserBashMapper.selectPage(new Page<BladeUserBash>().setCurrent(current).setSize(size),
			Wrappers.<BladeUserBash>query().lambda().in(BladeUserBash::getId, ids).like(BladeUserBash::getPhone, what).or().like(BladeUserBash::getUserName, what));
		List<String> result = new ArrayList<>();
		bladeUserBashPage.getRecords().forEach(item -> result.add(item.getId()));
		result.add(String.valueOf(bladeUserBashPage.getTotal()));
		return result;
	}

	/**
	 * 全部删完 店铺中间改为1
	 *
	 * @param userId userID
	 * @return 成功
	 */
	@Override
	public R<String> deleteUser(String userId) {
		bladeUserBashMapper.deleteById(userId);
		bladeRateMapper.deleteById(userId);
		bladeStoreUserMiddleMapper.updateByDelete(NUMBER_ONE, userId);
		bladeWalletHistoryMapper.delete(Wrappers.<BladeWalletHistory>query().lambda().eq(BladeWalletHistory::getUserId, userId));
		bladeWalletMapper.deleteById(userId);
		bladeUserMapper.deleteById(userId);
		return R.success(DELETE_OK);
	}
}
