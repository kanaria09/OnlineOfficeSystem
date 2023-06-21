package com.server.controller;

import com.server.AdminUtils;
import com.server.pojo.Admin;
import com.server.pojo.RespBean;
import com.server.service.IAdminService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.xmlbeans.impl.values.XmlIntegerRestriction;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 个人中心
 * @author: Bug
 */

@RestController
public class AdminInfoController {

    @Resource
    private IAdminService adminService;

    @ApiOperation(value = "更新用户信息")
    @PutMapping("/admin/info")
    public RespBean update(@RequestBody Admin admin, Authentication authentication) {
        if(adminService.updateById(admin)){
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(admin,null,authentication.getAuthorities()));
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @ApiOperation(value = "更新用户密码")
    @PutMapping("/admin/pass")
    public RespBean updateAdminPassword(@RequestBody Map<String,Object> info) {
        String oldPass = (String) info.get("oldPass");
        String pass = (String) info.get("pass");
        Integer adminId = (Integer) info.get("adminId");
        return adminService.updateAdminPassword(oldPass,pass,adminId);
    }

    //@ApiOperation(value = "更新用户头像")
    //@PostMapping("/admin/userface")
    //public RespBean updateUserFace(@RequestBody Admin admin, Authentication authentication) {
    //    //System.out.println("test-controller");
    //    //return adminService.updateUserFace(faceurl);
    //    if(adminService.updateById(admin)){
    //        return RespBean.success("更新成功！");
    //    }
    //    return RespBean.error("更新失败！");
    //}


}
