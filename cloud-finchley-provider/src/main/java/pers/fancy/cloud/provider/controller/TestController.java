package pers.fancy.cloud.provider.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.fancy.cloud.provider.service.SendService;


/**
 * 功能描述：
 *
 * @author lihuan
 * @date: 2019/3/19 15:16
 */
@RestController
public class TestController {


    @Autowired
    private SendService service;

    @RequestMapping(value = "/send/{msg}", method = RequestMethod.GET)
    public void send(@PathVariable("msg") String msg){
        service.sendMessage(msg);
    }
}
