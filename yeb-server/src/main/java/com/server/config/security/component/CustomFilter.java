package com.server.config.security.component;

import com.server.pojo.Menu;
import com.server.pojo.Role;
import com.server.service.IMenuService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 权限控制
 * 根据请求URl分析请求所需的角色
 * @author: Bug
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

    @Resource
    private IMenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     *  判断URL权限是否与角色匹配
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取请求的URL
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<Menu> menus = menuService.getMenusWithRole();
        for (Menu menu : menus) {
            //判断请求URL是否与菜单角色匹配
            if (antPathMatcher.match(menu.getUrl(), requestUrl)) {
                String[] str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(str);
            }
        }
        //没匹配的URl,默认登录即可访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
