package org.springblade.modules.taobao.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.modules.taobao.config.BashNumberInterface;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 平台用户表 服务实现类
 * </p>
 *
 * @author SeventySeven
 * @since 2020-08-14
 */
@Service
@AllArgsConstructor
public class BashNumberInterFaceImpl implements BashNumberInterface {
	@Override
	public String getUserRole(Integer roleNumber) {
		if (roleNumber.equals(ADMIN_NUMBER)){
			return ADMIN;
		}
		if (roleNumber.equals(MANAGER_NUMBER)){
			return MANAGER;
		}
		if (roleNumber.equals(STORE_NUMBER)){
			return STORE;
		}
		return ERROR_DATA;
	}

	@Override
	public String getUserAccountStatus(Integer statusNumber) {
		if (statusNumber.equals(STATUS_NO_CHECK_NUMBER)){
			return STATUS_NO_CHECK;
		}
		if (statusNumber.equals(STATUS_ADMIN_NUMBER)){
			return STATUS_ADMIN;
		}
		if (statusNumber.equals(STATUS_ERROR_CHECK_NUMBER)){
			return STATUS_ERROR_CHECK;
		}
		if (statusNumber.equals(STATUS_OK_CHECK_NUMBER)){
			return STATUS_OK_CHECK;
		}
		if (statusNumber.equals(STATUS_OK_CHECK_NO_RATE_NUMBER)){
			return STATUS_OK_CHECK_NO_RATE;
		}
		return ERROR_DATA;
	}
}
