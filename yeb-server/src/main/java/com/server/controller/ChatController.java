package com.server.controller;

import com.server.pojo.Admin;
import com.server.service.IAdminService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 在线聊天
 * @author: Bug
 */

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Resource
    private IAdminService adminService;

    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/admin")
    public List<Admin> getAllAdmins(String keywords){
        return adminService.gteAllAdmins(keywords);
    }
}
