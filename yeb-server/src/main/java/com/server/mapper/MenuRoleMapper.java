package com.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.server.pojo.MenuRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Bug
 * @since 2023-05-15
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    /**
     * 批量更新角色菜单
     * @param rid
     * @param mids
     */
    Integer insertRecord(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
