package org.springblade.modules.taobao.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

import static org.springblade.modules.taobao.config.TaobaoURLConfig.BLADE_ADMIN_ACCOUNT_URL;

/**
* @author SeventySeven
* @since 2020-08-14
*/
@Api(tags = "平台帐号表接口管理")
@RestController
@RequestMapping(BLADE_ADMIN_ACCOUNT_URL)
    public class BladeAdminAccountController {
}
