package org.springblade.modules.taobao.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.dto.BladeOrderAliDTO;
import org.springblade.modules.taobao.dto.BladeOrderPageVO;
import org.springblade.modules.taobao.dto.BladeOrderVO;
import org.springblade.modules.taobao.entity.BladeOrder;
import org.springblade.modules.taobao.entity.BladeUserBash;
import org.springblade.modules.taobao.entity.BladeUserStore;
import org.springblade.modules.taobao.entity.BladeWallet;
import org.springblade.modules.taobao.mapper.BladeOrderMapper;
import org.springblade.modules.taobao.mapper.BladeUserBashMapper;
import org.springblade.modules.taobao.mapper.BladeUserStoreMapper;
import org.springblade.modules.taobao.service.IBladeOrderService;
import org.springblade.modules.taobao.service.IBladeWalletService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springblade.modules.taobao.config.BashNumberInterface.*;
import static org.springblade.modules.taobao.config.MethodConfig.SAVE_OK;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@Service
@AllArgsConstructor
public class BladeOrderServiceImpl extends ServiceImpl<BladeOrderMapper, BladeOrder> implements IBladeOrderService {
	private final BladeUserBashMapper bladeUserBashMapper;
	private final BladeUserStoreMapper bladeUserStoreMapper;
	private final IBladeWalletService iBladeWalletService;

	@Override
	public R<BladeOrderPageVO> getOrderPageInfo(List<String> orderIds, Integer size, Integer current, Long total) {
		BladeOrderPageVO bladeOrderPageVO = new BladeOrderPageVO().setTotal(total).setCurrent(current).setSize(size);
		if (orderIds == null || orderIds.size() == 0) {
			//实际上没有订单数据
			return R.data(bladeOrderPageVO.setList(new ArrayList<>()));
		}
		List<BladeOrder> list = this.list(Wrappers.<BladeOrder>query().lambda().in(BladeOrder::getId, orderIds));
		List<BladeOrderVO> voList = new ArrayList<>();
		list.forEach(item -> {
//			装名字
			BladeUserBash bladeUserBash = bladeUserBashMapper.selectById(item.getManagerId());
			BladeUserStore bladeUserStore = bladeUserStoreMapper.selectById(item.getStoreId());
			BladeOrderVO bladeOrderVO = new BladeOrderVO().setManagerName(bladeUserBash.getUserName()).setStoreName(bladeUserStore.getStoreName());
			BeanUtil.copyProperties(item, bladeOrderVO);
			voList.add(bladeOrderVO);
		});
		return R.data(bladeOrderPageVO.setList(voList));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<String> updateOrderStatus(String orderId, Integer status) {
		BladeOrder byId = this.getById(orderId);
		this.updateById(byId.setStatus(status));
		return R.success(SAVE_OK);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R insertOrder(BladeOrder bladeOrder) {
		String orderId = UUID.randomUUID().toString();
		return R.data(this.save(bladeOrder.setId(orderId)));
	}

	@Override
	public R<BladeOrder> conversionBladeOrder(BladeOrderAliDTO bladeOrderAliDTO) {
		//todo: 这里需要把提成算出来加进去
		return null;
	}

	@Override
	public R<List<String>> checkOrderPastTime() {
		List<BladeOrder> list = this.list(Wrappers.<BladeOrder>query().lambda().eq(BladeOrder::getStatus, ORDER_NO_NUMBER));
		if (list == null || list.size() == 0) {
			return R.success(SAVE_OK);
		}
		List<String> result = new ArrayList<>();
		list.forEach(item -> {
			if (item.getPastTime() - ONE_DAY > 0) {
				this.updateById(item.setPastTime(item.getPastTime() - ONE_DAY));
			} else {
				result.add(item.getId());
			}
		});
		return R.data(result);
	}

	@Override
	public R<String> rateMoneyToManagerAndStore(List<String> orderIds) {
		if (orderIds.size() == 0) {
			return R.success(SAVE_OK);
		}
		List<BladeOrder> bladeOrders = this.listByIds(orderIds);
		bladeOrders.forEach(item -> {
			//分钱
			BladeWallet storeWallet = iBladeWalletService.getById(item.getStoreId());
			BladeWallet managerWallet = iBladeWalletService.getById(item.getManagerId());
			iBladeWalletService.addMoney(storeWallet, item.getStoreRateMoney(), REASON_ADD_NUMBER);
			iBladeWalletService.addMoney(managerWallet, item.getManagerRateMoney(), REASON_ADD_NUMBER);
			//将订单status改为分账完成
			this.updateOrderStatus(item.getId(), ORDER_OK_NUMBER);
		});
		return R.success(SAVE_OK);
	}
}
