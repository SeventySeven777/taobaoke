package org.springblade.modules.taobao.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.exception.SqlException;
import org.springblade.modules.taobao.dto.ManagerStoreAll;
import org.springblade.modules.taobao.dto.ManagerStoreVO;
import org.springblade.modules.taobao.dto.StoreIdDateDto;
import org.springblade.modules.taobao.entity.*;
import org.springblade.modules.taobao.mapper.*;
import org.springblade.modules.taobao.service.IBladeUserStoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static org.springblade.modules.taobao.config.BashNumberInterface.*;
import static org.springblade.modules.taobao.config.MethodConfig.DELETE_OK;
import static org.springblade.modules.taobao.config.MethodConfig.SAVE_ERROR;

/**
 * <p>
 * 店铺详细表 服务实现类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@Service
@AllArgsConstructor
public class BladeUserStoreServiceImpl extends ServiceImpl<BladeUserStoreMapper, BladeUserStore> implements IBladeUserStoreService {
	private final BladeUserMapper bladeUserMapper;
	private final BladeWalletMapper bladeWalletMapper;
	private final BladeWalletHistoryMapper bladeWalletHistoryMapper;
	private final BladeStoreUserMiddleMapper bladeStoreUserMiddleMapper;
	private final BladeRateMapper bladeRateMapper;
	private final BladeOrderMapper bladeOrderMapper;
	private final BladeUserStoreMapper bladeUserStoreMapper;



	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<String> deleteStore(String storeId) {
		bladeUserMapper.deleteById(storeId);
		bladeWalletMapper.deleteById(storeId);
		bladeWalletHistoryMapper.delete(Wrappers.<BladeWalletHistory>query().lambda().eq(BladeWalletHistory::getUserId, storeId));
		bladeStoreUserMiddleMapper.delete(Wrappers.<BladeStoreUserMiddle>query().lambda().eq(BladeStoreUserMiddle::getStoreId, storeId));
		bladeRateMapper.delete(Wrappers.<BladeRate>query().lambda().eq(BladeRate::getUserId, storeId));
		this.remove(Wrappers.<BladeUserStore>query().lambda().eq(BladeUserStore::getId, storeId));
		return R.success(DELETE_OK);
	}

	@Override
	public R<ManagerStoreAll> getManagerPage(String userId, Integer size, Integer current, String date) {
		StoreIdDateDto storeId = getStoreId(userId, date);
		List<String> list = storeId.getList();
		List<String> idList = getPageId(list,size,current);
		ManagerStoreAll managerStoreAll = new ManagerStoreAll();
		List<ManagerStoreVO> storeVOList = new ArrayList<>();
		idList.forEach(item->{
			ManagerStoreVO managerStoreVO = new ManagerStoreVO();
			BladeUser bladeUser = bladeUserMapper.selectById(item);
			managerStoreVO.setTime(bladeUser.getCreateDate());
			BladeUserStore bladeUserStore = bladeUserStoreMapper.selectById(item);
			managerStoreVO.setStoreName(bladeUserStore.getStoreName()).setRateMoney(new BigDecimal(0));
			List<BladeOrder> bladeOrders = bladeOrderMapper.selectList(Wrappers.<BladeOrder>query().lambda()
				.eq(BladeOrder::getStoreId, item).eq(BladeOrder::getStatus,ORDER_OK_NUMBER));
			for (BladeOrder bladeOrder : bladeOrders) {
				managerStoreVO.setRateMoney(managerStoreVO.getRateMoney().add(bladeOrder.getManagerRateMoney()));
			}
			storeVOList.add(managerStoreVO);
		});
		return R.data(managerStoreAll.setTotal(Long.valueOf(list.size())).setSize(size).setCurrent(current).setList(storeVOList));
	}

	private List<String> getPageId(List<String> list, Integer size, Integer current) {
		List<String> result = new ArrayList<>();
		int k = list.size()-(current*size)>size?size:list.size()-(current*size);
		for (int i = current*(size-1); i < k; i++) {
			result.add(list.get(i));
		}
		return result;
	}

	@Override
	public R<ManagerStoreAll> getManagerPage(String userId, Integer size, Integer current, String date, String status) {
		StoreIdDateDto storeId = getStoreId(userId, date);

		return null;
	}

	/**
	 * 获取店铺Id
	 * @param id 经理id
	 * @return 店铺id list
	 */
	private StoreIdDateDto getStoreId(String id, String date){
		List<BladeStoreUserMiddle> bladeStoreUserMiddles = bladeStoreUserMiddleMapper.selectList(Wrappers
			.<BladeStoreUserMiddle>query().lambda().eq(BladeStoreUserMiddle::getUserId, id));
		List<String> list = new ArrayList<>();
		bladeStoreUserMiddles.forEach(item->list.add(item.getStoreId()));
		if (!StrUtil.isEmpty(date)){
			DateTime dateTime = DateUtil.beginOfMonth(new Date(date));
			DateTime dateTime1 = DateUtil.endOfMonth(new Date(date));
			List<BladeUser> users = bladeUserMapper.selectList(Wrappers.<BladeUser>query().lambda().in(BladeUser::getId, list)
				.between(BladeUser::getCreateDate,dateTime,dateTime1).orderByDesc(BladeUser::getCreateDate));
			List<String> listResult = new ArrayList<>();
			users.forEach(item->listResult.add(item.getId()));
			return new StoreIdDateDto().setTotal(Long.valueOf(users.size())).setList(listResult);
		}
		List<BladeUser> users = bladeUserMapper.selectList(Wrappers.<BladeUser>query().lambda().in(BladeUser::getId, list)
			.orderByDesc(BladeUser::getCreateDate));
		List<String> listResult = new ArrayList<>();
		users.forEach(item->listResult.add(item.getId()));
		return new StoreIdDateDto().setList(listResult).setTotal(Long.valueOf(listResult.size()));
	}

}
