package com.qihao.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangqihao
 * @version 1.0
 * @date 2019/9/26 17:38
 */

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){

        return "hello";
    }

    @RequestMapping("/test")
    public String test(){

        return "hello test";
    }

    @RequestMapping("/user")
    public String user(){

        return "hello user";
    }
}
