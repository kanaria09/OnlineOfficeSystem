package com.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Bug
 * @since 2023-05-15
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 通过用户id查询系统菜单列表
     */
    List<Menu> getMenusByAdminId();

    /**
     * 根据角色获取权限列表
     */
    List<Menu> getMenusWithRole();

    /**
     * 查询所有菜单
     */
    List<Menu> getAllMenus();

}
