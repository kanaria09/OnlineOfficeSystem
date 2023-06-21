package com.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.server.AdminUtils;
import com.server.config.security.component.JwtTokenUtil;
import com.server.mapper.AdminMapper;
import com.server.mapper.AdminRoleMapper;
import com.server.mapper.RoleMapper;
import com.server.pojo.Admin;
import com.server.pojo.AdminRole;
import com.server.pojo.RespBean;
import com.server.pojo.Role;
import com.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Bug
 * @since 2023-05-15
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Resource
    private AdminMapper adminMapper;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private AdminRoleMapper adminRoleMapper;
    /**
     * 加密工具
     */
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Resource
    private RoleMapper roleMapper;


    /**
     * 登录之后返回token
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        //校验验证码
        String captcha = (String) request.getSession().getAttribute("captcha");
        if(StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)){
            return RespBean.error("验证码错误！");
        }

        //获取用户信息，登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error("用户名或密码不正确！");
        }
        if (!userDetails.isEnabled()) {
            return RespBean.error("账号被禁用，请联系管理员！");
        }

        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead",tokenHead);
        return RespBean.success("登录成功",tokenMap);
    }

    /**
     * 根据用户名获取用户信息
     */
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username)
                .eq("enabled",true));
    }

    /**
     * 根据用户id查询角色列表
     */
    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }

    /**
     * 获取所有操作员
     */
    @Override
    public List<Admin> gteAllAdmins(String keywords) {
        return adminMapper.gteAllAdmins(AdminUtils.getCurrentAdmin().getId(), keywords);

    }

    /**
     * 更新操作员角色
     */
    @Override
    @Transactional
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId", adminId));
        //rid无传参，用户选择清空角色
        if(rids == null || rids.length == 0) {
            return RespBean.success("更新成功！");
        }
        if(adminRoleMapper.addAdminRole(adminId, rids) == rids.length){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    /**
     * 更新操作员密码
     */
    @Override
    public RespBean updateAdminPassword(String oldPass, String pass, Integer adminId) {
        Admin admin = adminMapper.selectById(adminId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //判断旧密码是否正确
        if(encoder.matches(oldPass,admin.getPassword())){
            admin.setPassword(encoder.encode(pass));
            int result = adminMapper.updateById(admin);
            if(result == 1){
                return RespBean.success("更新成功！");
            }
        }
        return RespBean.error("更新失败！");
    }

    /**
     * 更新用户头像
     */
    @Override
    public RespBean updateUserFace(String faceurl) {
        System.out.println("test-service");
        Integer adminId = AdminUtils.getCurrentAdmin().getId();
        Admin admin = adminMapper.selectById(adminId);
        admin.setUserFace(faceurl);
        int result = adminMapper.updateById(admin);
        if(result == 1){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }


}
