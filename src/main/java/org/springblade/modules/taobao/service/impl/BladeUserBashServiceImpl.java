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
import org.springblade.modules.taobao.mapper.BladeUserBashMapper;
import org.springblade.modules.taobao.service.IBladeUserBashService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
	private BladeUserBashMapper bladeUserBashMapper;

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
	public List<String> getUserIdBySomething(String what, Integer size, Integer current) {
		Page<BladeUserBash> bladeUserBashPage = bladeUserBashMapper.selectPage(new Page<BladeUserBash>().setCurrent(current).setSize(size),
			Wrappers.<BladeUserBash>query().lambda().like(BladeUserBash::getPhone, what).or().like(BladeUserBash::getUserName, what));
		List<String> result = new ArrayList<>();
		bladeUserBashPage.getRecords().forEach(item -> result.add(item.getId()));
		result.add(String.valueOf(bladeUserBashPage.getTotal()));
		return result;
	}
}
