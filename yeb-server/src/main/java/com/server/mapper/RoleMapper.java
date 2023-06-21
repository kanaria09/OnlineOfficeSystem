package com.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Bug
 * @since 2023-05-15
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id获取角色列表
     */
    List<Role> getRoles(Integer adminId);
}
