package com.nbplus.vnpy.gateway;

import com.nbplus.vnpy.domain.CTPLogin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author gongtan
 * @Date 2019/11/21 15:31
 * @Version 1.0
 **/
@RestController
@RequestMapping("/login")
public class LoginGateway {

    /**
     * @Description     登录ctp接口方法
     * @author gt_vv
     * @date 2019/11/21
     * @param
     * @return void
     */
    @PostMapping("/loginCtp")
    public void login(@RequestBody CTPLogin loginParam){

    }
}