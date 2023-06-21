package com.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 * @author 神様
 */
@RestController
public class Yebcontroller {

    @GetMapping("/yeb")
    public String run(){
        return "yeb success!";
    }

    @GetMapping("/employee/basic/hello")
    public String hello(){
        return "/employee/basic/hello";
    }

    @GetMapping("/employee/advanced/hello")
    public String hello2(){
        return "/employee/advanced/hello";
    }


}
