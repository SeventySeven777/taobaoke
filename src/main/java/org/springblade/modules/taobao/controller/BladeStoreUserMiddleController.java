package org.springblade.modules.taobao.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

import static org.springblade.modules.taobao.config.TaobaoURLConfig.BLADE_STORE_USER_MIDDLE_URL;

/**
* @author SeventySeven
* @since 2020-08-14
*/
@Api(tags = "店铺关联表接口管理")
@RestController
@RequestMapping(BLADE_STORE_USER_MIDDLE_URL)
@CrossOrigin
    public class BladeStoreUserMiddleController {
}
