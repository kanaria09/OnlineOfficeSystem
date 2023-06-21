package com.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.server.pojo.Admin;
import com.server.pojo.Menu;
import com.server.pojo.RespBean;
import com.server.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Bug
 * @since 2023-05-15
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 登录之后返回token
     */
    RespBean login(String username, String password, String code, HttpServletRequest request);

    /**
     * 根据用户名获取用户信息
     */
    Admin getAdminByUserName(String username);

    /**
     * 根据用户id查询角色列表
     */
    List<Role> getRoles(Integer adminId);

    /**
     * 获取所有操作员
     */
    List<Admin> gteAllAdmins(String keywords);

    /**
     * 更新操作员角色
     */
    RespBean updateAdminRole(Integer adminId, Integer[] rids);

    /**
     * 更新用户密码
     */
    RespBean updateAdminPassword(String oldPass, String pass, Integer adminId);

    /**
     * 更新用户头像
     */
    RespBean updateUserFace(String faceurl);
}
