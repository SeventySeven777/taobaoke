package org.springblade.modules.taobao.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

import static org.springblade.modules.taobao.config.TaobaoURLConfig.BLADE_RATE_URL;

/**
* @author SeventySeven
* @since 2020-08-14
*/
@Api(tags = "比率表接口管理")
@RestController
@RequestMapping(BLADE_RATE_URL)
    public class BladeRateController {
}
