package com.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.server.mapper.DepartmentMapper;
import com.server.pojo.Department;
import com.server.pojo.RespBean;
import com.server.service.IDepartmentService;
import org.springframework.data.repository.config.ResourceReaderRepositoryPopulatorBeanDefinitionParser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Bug
 * @since 2023-05-15
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    /**
     * 获取所有部门
     * @return
     */
    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartments(-1);
    }

    /**
     * 添加部门
     * @param dep
     * @return
     */
    @Override
    public RespBean addDep(Department dep) {
        dep.setEnabled(true);
        departmentMapper.addDep(dep);
        if (dep.getResult() == 1) {
            return RespBean.success("添加成功！",dep);
        }
        return RespBean.error("添加失败！");
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @Override
    public RespBean deleteDep(Integer id) {
        Department dep = new Department();
        dep.setId(id);
        departmentMapper.deleteDep(dep);
        if(dep.getResult() == -2){
            return RespBean.error("该部门下还有子部门，删除失败！");
        }
        if (dep.getResult() == -1) {
            return RespBean.error("该部门下还有员工，删除失败！");
        }
        if (dep.getResult() == 1) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
