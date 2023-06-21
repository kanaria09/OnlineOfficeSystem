package com.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.server.pojo.MenuRole;
import com.server.pojo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Bug
 * @since 2023-05-15
 */
public interface IMenuRoleService extends IService<MenuRole> {

    /**
     * 更新角色菜单
     * @param rid 角色id
     * @param mids 菜单id组
     * @return
     */
    RespBean updateMenuRole(Integer rid, Integer[] mids);
}
