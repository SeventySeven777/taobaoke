package org.springblade.modules.taobao.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.exception.SqlException;
import org.springblade.modules.taobao.entity.BladeUser;
import org.springblade.modules.taobao.entity.BladeUserStore;
import org.springblade.modules.taobao.mapper.BladeUserMapper;
import org.springblade.modules.taobao.mapper.BladeUserStoreMapper;
import org.springblade.modules.taobao.mapper.BladeWalletMapper;
import org.springblade.modules.taobao.service.IBladeUserStoreService;
import org.springframework.stereotype.Service;

import java.util.Date;

import static org.springblade.modules.taobao.config.BashNumberInterface.*;
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

}
