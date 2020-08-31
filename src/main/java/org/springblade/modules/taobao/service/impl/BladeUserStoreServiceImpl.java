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
import org.springblade.modules.taobao.dto.ManagerVO;
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
		List<BladeUser> list = getStoreByManagerId(userId);
		List<ManagerStoreVO> voList = new ArrayList<>();
		list.forEach(item -> {
			ManagerStoreVO managerStoreVO = new ManagerStoreVO();
			String storeName = getStoreNameById(item.getId());
			managerStoreVO.setStoreName(storeName).setTime(String.valueOf(item.getCreateDate().getTime()));
			List<BladeOrder> bladeOrders = null;
			if (StrUtil.isEmpty(date)) {
				bladeOrders = bladeOrderMapper.selectList(Wrappers.<BladeOrder>query().lambda().eq(BladeOrder::getStoreId, item.getId())
					.eq(BladeOrder::getStatus, ORDER_OK_NUMBER));
			} else {
				bladeOrders = bladeOrderMapper.selectList(Wrappers.<BladeOrder>query().lambda().eq(BladeOrder::getStoreId, item.getId())
					.eq(BladeOrder::getStatus, ORDER_OK_NUMBER)
					.between(BladeOrder::getCreateTime, DateUtil.beginOfMonth(new Date(Long.valueOf(date))), DateUtil.endOfMonth(new Date(Long.valueOf(date)))));
			}
			BigDecimal bigDecimal = new BigDecimal(NUMBER_ZERO);
			for (BladeOrder bladeOrder : bladeOrders) {
				bigDecimal = bigDecimal.add(bladeOrder.getManagerRateMoney());
			}
			managerStoreVO.setRateMoney(bigDecimal).setTemplate(bigDecimal.compareTo(new BigDecimal(NUMBER_ZERO)));
			voList.add(managerStoreVO);
		});
		//拿到所有店铺得结果
		List<ManagerStoreVO> result;
		result = toPage(voList, size, current);
		ManagerStoreAll managerStoreAll = new ManagerStoreAll().setList(result).setSize(size).setCurrent(current).setTotal(Long.valueOf(voList.size()));
		return R.data(managerStoreAll);
	}

	private List<ManagerStoreVO> toPage(List<ManagerStoreVO> voList, Integer size, Integer current) {
		Integer all = 0;
		if (voList.size() - size * current > size) {
			all = size;
		} else {
			all = voList.size() - size * current;
		}
		ArrayList<ManagerStoreVO> managerStoreVOS = new ArrayList<>();
		for (int i = size * (current - 1); i < all; i++) {
			managerStoreVOS.add(voList.get(i));
		}
		return managerStoreVOS;
	}

	@Override
	public R<ManagerStoreAll> getManagerPage(String id, Integer size, Integer current, String date, String status) {
		List<BladeUser> list = getStoreByManagerId(id);
		List<ManagerStoreVO> voList = new ArrayList<>();
		list.forEach(item -> {
			ManagerStoreVO managerStoreVO = new ManagerStoreVO();
			String storeName = getStoreNameById(item.getId());
			managerStoreVO.setStoreName(storeName).setTime(String.valueOf(item.getCreateDate().getTime()));
			List<BladeOrder> bladeOrders = null;
			if (StrUtil.isEmpty(date)) {
				bladeOrders = bladeOrderMapper.selectList(Wrappers.<BladeOrder>query().lambda().eq(BladeOrder::getStoreId, item.getId())
					.eq(BladeOrder::getStatus, ORDER_OK_NUMBER));
			} else {
				bladeOrders = bladeOrderMapper.selectList(Wrappers.<BladeOrder>query().lambda().eq(BladeOrder::getStoreId, item.getId())
					.eq(BladeOrder::getStatus, ORDER_OK_NUMBER)
					.between(BladeOrder::getCreateTime, DateUtil.beginOfMonth(new Date(date)), DateUtil.endOfMonth(new Date(date))));
			}
			BigDecimal bigDecimal = new BigDecimal(NUMBER_ZERO);
			for (BladeOrder bladeOrder : bladeOrders) {
				bigDecimal = bigDecimal.add(bladeOrder.getManagerRateMoney());
			}
			managerStoreVO.setRateMoney(bigDecimal).setTemplate(bigDecimal.compareTo(new BigDecimal(NUMBER_ZERO)));
			voList.add(managerStoreVO);
		});
		List<ManagerStoreVO> list1 = getVoList(voList,status);
		List<ManagerStoreVO> result;
		result = toPage(list1, size, current);
		ManagerStoreAll managerStoreAll = new ManagerStoreAll().setList(result).setSize(size).setCurrent(current).setTotal(Long.valueOf(list1.size()));
		return R.data(managerStoreAll);
	}

	public List<ManagerStoreVO> getVoList(List<ManagerStoreVO> voList,String status){
		ArrayList<ManagerStoreVO> list = new ArrayList<>();
		ArrayList<ManagerStoreVO> unlist = new ArrayList<>();
		for (ManagerStoreVO managerStoreVO : voList) {
			Integer integet = managerStoreVO.getTemplate();
			if(integet.equals(Integer.valueOf(status))){
				list.add(managerStoreVO);
			}else {
				unlist.add(managerStoreVO);
			}
		}
		return list;
	}

	private String getStoreNameById(String id) {
		return bladeUserStoreMapper.selectById(id).getStoreName();
	}


	private List<BladeUser> getStoreByManagerId(String userId) {
		List<BladeStoreUserMiddle> bsum = bladeStoreUserMiddleMapper.selectList(Wrappers.<BladeStoreUserMiddle>query().lambda().eq(BladeStoreUserMiddle::getUserId, userId));
		List<BladeUser> bladeUsers = null;
		if (null == bsum) {
			bsum = new ArrayList<BladeStoreUserMiddle>();
		}
		List<String> list = new ArrayList<>();
		bsum.forEach(item -> list.add(item.getStoreId()));
		if (list.size() == 0) {
			return new ArrayList<>();
		}
		List<BladeUser> users = bladeUserMapper.selectList(Wrappers.<BladeUser>query().lambda().in(BladeUser::getId, list).orderByDesc(BladeUser::getCreateDate));
		return users;
	}

}
