package com.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.server.pojo.Employee;
import com.server.pojo.RespBean;
import com.server.pojo.RespPageBean;
import com.server.pojo.Salary;
import com.server.service.IEmployeeService;
import com.server.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.print.MultiDocPrintService;
import java.util.List;

/**
 * 员工工资账套管理
 * @author: Bug
 */

@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySobCfgController {

    @Resource
    private ISalaryService salaryService;

    @Resource
    private IEmployeeService employeeService;

    @ApiOperation(value = "获取所有工资账套")
    @GetMapping("/salaries")
    public List<Salary> getAllSalaries(){
        return salaryService.list();
    }

    @ApiOperation(value = "获取所有账套-分页")
    @GetMapping("/")
    public RespPageBean getEmployeeWithSalary(@RequestParam(defaultValue = "1") Integer currentPage,
                                              @RequestParam(defaultValue = "10") Integer size){
        return employeeService.getEmployeeWithSalary(currentPage, size);
    }

    @ApiOperation(value = "更新工资账套")
    @PutMapping("/")
    public RespBean updateEmployeeSalary(Integer eid, Integer sid){
        if(employeeService.update(new UpdateWrapper<Employee>().set("salaryId", sid).eq("id",eid))){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }


}

