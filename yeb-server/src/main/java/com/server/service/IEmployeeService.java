package com.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.server.pojo.Employee;
import com.server.pojo.RespBean;
import com.server.pojo.RespPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Bug
 * @since 2023-05-15
 */
public interface IEmployeeService extends IService<Employee> {

    /**
     * 获取所有员工(分页)
     */
    RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    /**
     * 获取工号
     * @return
     */
    RespBean maxWorkId();

    /**
     * 添加员工
     */
    RespBean addEmp(Employee employee);

    /**
     * 更新员工信息
     * @param employee
     * @return
     */
    RespBean updateEmp(Employee employee);

    /**
     * 查询员工
     * @param id
     * @return
     */
    List<Employee> getEmployee(Integer id);

    /**
     * 获取所有账套-分页
     */
    RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size);
}
