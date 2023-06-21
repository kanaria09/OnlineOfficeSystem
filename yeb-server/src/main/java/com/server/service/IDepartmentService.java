package com.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.server.pojo.Department;
import com.server.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Bug
 * @since 2023-05-15
 */
public interface IDepartmentService extends IService<Department> {

    /**
     * 获取所有部门
     * @return
     */
    List<Department> getAllDepartments();

    /**
    * 添加部门
     */
    RespBean addDep(Department dep);

    /**
     * 删除部门
     */
    RespBean deleteDep(Integer id);
}
