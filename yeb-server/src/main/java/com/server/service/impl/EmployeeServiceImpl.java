package com.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.server.mapper.EmployeeMapper;
import com.server.mapper.MailLogMapper;
import com.server.pojo.*;
import com.server.service.IEmployeeService;
import com.sun.media.jfxmedia.control.MediaPlayerOverlay;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Bug
 * @since 2023-05-15
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private MailLogMapper mailLogMapper;

    /**
     * 获取所有员工(分页)
     */
    @Override
    public RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
        //开启分页
        Page<Employee> page = new Page<>(currentPage,size);
        IPage<Employee> employeeByPage = employeeMapper.getEmployeeByPage(page, employee, beginDateScope);
        RespPageBean respPageBean = new RespPageBean(employeeByPage.getTotal(), employeeByPage.getRecords());
        return respPageBean;
    }

    /**
     * 获取工号
     */
    @Override
    public RespBean maxWorkId() {
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workId)"));
        return RespBean.success(null,
                String.format("%08d",Integer.parseInt(maps.get(0).get("max(workId)").toString())+1));
    }

    /**
     * 添加员工
     */
    @Override
    public RespBean addEmp(Employee employee) {
        //处理合同期限
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        //保留小数点后两位
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days/365.00)));
        if(employeeMapper.insert(employee) == 1){
            ////添加员工时自动发送邮件
            //Employee emp = employeeMapper.getEmployee(employee.getId()).get(0);
            ////数据库记录已发送的消息
            //String msgId = UUID.randomUUID().toString();
            //MailLog mailLog = new MailLog();
            //mailLog.setMsgId(msgId);
            //mailLog.setEid(employee.getId());
            //mailLog.setStatus(0);
            //mailLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            //mailLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            //mailLog.setCount(0);
            //mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT));
            //mailLog.setCreateTime(LocalDateTime.now());
            //mailLog.setUpdateTime(LocalDateTime.now());
            //mailLogMapper.insert(mailLog);
            ////发送信息
            //rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY_NAME, emp, new CorrelationData(msgId));
            ////
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    /**
     * 更新员工信息
     */
    @Override
    public RespBean updateEmp(Employee employee) {
        //处理合同期限
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        //保留小数点后两位
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days/365.00)));
        if(updateById(employee)){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    /**
     * 查询员工
     */
    @Override
    public List<Employee> getEmployee(Integer id) {
        return employeeMapper.getEmployee(id);
    }

    /**
     * 获取所有账套-分页
     */
    @Override
    public RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size) {
        //开启分页
        Page<Employee> page = new Page<>(currentPage,size);
        IPage<Employee> employeeWithSalary = employeeMapper.getEmployeeWithSalary(page);
        RespPageBean respPageBean = new RespPageBean(employeeWithSalary.getTotal(), employeeWithSalary.getRecords());
        return respPageBean;
    }
}
