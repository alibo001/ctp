package com.nbplus.vnpy.gateway;

import com.nbplus.vnpy.CTPservice.serviceImpl.tdCtpImpl;
import com.nbplus.vnpy.common.msg.ObjectRestResponse;
import com.nbplus.vnpy.domain.CTPLogin;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/tdCTP")
public class LoginGateway {

    @Autowired
    private tdCtpImpl tdCtp;

    @PostMapping("/login")
    public ObjectRestResponse login(@RequestBody CTPLogin ctpLogin){
        tdCtp.login(ctpLogin);
        return null;
    }

}
