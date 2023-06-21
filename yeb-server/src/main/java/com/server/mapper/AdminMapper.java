package com.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.server.pojo.Admin;
import com.server.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Bug
 * @since 2023-05-15
 */
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 获取所有操作员
     */
    List<Admin> gteAllAdmins(@Param("id") Integer id, @Param("keywords") String keywords);
}
