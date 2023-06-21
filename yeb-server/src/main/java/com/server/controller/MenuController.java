package com.server.controller;

import com.server.pojo.Menu;
import com.server.service.IAdminService;
import com.server.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sound.midi.Patch;
import java.util.List;

/**
 * <p>
 *     系统导航栏菜单
 * </p>
 *
 * @author Bug
 * @since 2023-05-15
 */
@RestController
@RequestMapping("/system/config")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @ApiOperation(value = "通过用户id查询菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenusByAdminId(){
        return menuService.getMenusByAdminId();
    }




}
