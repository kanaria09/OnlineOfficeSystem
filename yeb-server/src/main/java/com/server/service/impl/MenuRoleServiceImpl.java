package com.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.server.mapper.MenuMapper;
import com.server.mapper.MenuRoleMapper;
import com.server.pojo.MenuRole;
import com.server.pojo.RespBean;
import com.server.service.IMenuRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Bug
 * @since 2023-05-15
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    @Resource
    private MenuRoleMapper menuRoleMapper;

    /**
     * 更新角色菜单
     * @param rid 角色id
     * @param mids 菜单id组
     * @return
     */
    @Override
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        //清空角色的全部菜单
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid",rid));
        //mid无传参，用户选择清空菜单
        if(mids == null || mids.length == 0) {
            return RespBean.success("更新成功！");
        }
        //批量更新角色菜单
        if(menuRoleMapper.insertRecord(rid,mids) == mids.length) {
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
}
