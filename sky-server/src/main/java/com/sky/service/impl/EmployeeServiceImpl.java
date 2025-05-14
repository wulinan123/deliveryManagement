package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeEditPasswordDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();
        String passwordHash = DigestUtils.md5DigestAsHex(password.getBytes());

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        if (!passwordHash.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象


        return employee;
    }

    @Override
    public void save(EmployeeDTO employee) {
        Employee  employee1= new Employee();
        BeanUtils.copyProperties(employee,employee1);
        // Employee的额外属性设定
        employee1.setStatus(StatusConstant.ENABLE);
        employee1.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        employee1.setCreateTime(LocalDateTime.now());
        employee1.setUpdateTime(LocalDateTime.now());
        //
        // 获取当前线程的ID，作为创建和更新用户的标识
        Long id = BaseContext.getCurrentId();
        // 设置ID为当前线程ID
        employee1.setCreateUser(id);
        employee1.setUpdateUser(id);

        employeeMapper.insert(employee1);
    }

    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        // 计算查询参数，返回到 Mapper

        //TODO：不应该返回管理员
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        PageResult pageResult = new PageResult(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public void updateStatus(Integer stauts, Long id) {
        employeeMapper.update(
                Employee.builder()
                        .id(id)
                        .status(stauts)
                        .updateTime(LocalDateTime.now())
                        .build());

    }
    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        return employee;
    }

    @Override
    public boolean verifyPassword(long empId, String password) {
        Employee employee = employeeMapper.getById(empId);
        if(employee.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes())))
            return true;
        else
            return false;
    }

    @Override
    public void editPassword(EmployeeEditPasswordDTO employeeEditPasswordDTO) {
        String password = DigestUtils.md5DigestAsHex(employeeEditPasswordDTO.getNewPassword().getBytes());
        Employee employee = Employee.builder().id(employeeEditPasswordDTO.getEmpId())
                .password(password).build();
        employeeMapper.update(employee);

    }

    @Override
    public Employee updateEmployee(EmployeeDTO employeeDTO) {
        Long id = BaseContext.getCurrentId();
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(id);
        employeeMapper.update(employee);

        Employee employee1 =  employeeMapper.getById(employee.getId());
        employee1.setPassword("");// 删除密码
        return employee1;
    }



}
