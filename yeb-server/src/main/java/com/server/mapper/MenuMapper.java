package com.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Bug
 * @since 2023-05-15
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 通过用户id查询系统菜单列表
     */
    List<Menu> getMenusByAdminId(Integer id);


    /**
     * 根据角色获取权限列表
     * @return
     */
    List<Menu> getMenusWithRole();

    /**
     * 查询所有菜单
     * @return
     */
    List<Menu> getAllMenus();
}
