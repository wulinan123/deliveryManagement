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
import com.sky.service.inter.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * 员工业务逻辑实现类
 * 负责员工登录、新增、分页查询、状态更新、信息修改等核心业务功能
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录验证
     * @param employeeLoginDTO 包含用户名和密码的登录信息DTO
     * @return 登录成功的员工实体对象
     * @throws AccountNotFoundException 用户名不存在时抛出
     * @throws PasswordErrorException 密码错误时抛出
     * @throws AccountLockedException 账号被锁定时抛出
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

    /**
     * 新增员工信息
     * @param employee 包含员工基本信息的DTO（不包含密码和状态）
     */
    @Override
    public void save(EmployeeDTO employee) {
        Employee  employee1= new Employee();
        BeanUtils.copyProperties(employee,employee1);
        // Employee的额外属性设定
        employee1.setStatus(StatusConstant.ENABLE);
        employee1.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
//        employee1.setCreateTime(LocalDateTime.now());
//        employee1.setUpdateTime(LocalDateTime.now());
        //
        // 获取当前线程的ID，作为创建和更新用户的标识
        Long id = BaseContext.getCurrentId();
        // 设置ID为当前线程ID
//        employee1.setCreateUser(id);
//        employee1.setUpdateUser(id);

        employeeMapper.insert(employee1);
    }

    /**
     * 员工信息分页查询
     * @param employeePageQueryDTO 包含分页参数和查询条件的DTO
     * @return 分页结果对象（包含总记录数和当前页数据）
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        // 计算查询参数，返回到 Mapper

        //TODO：不应该返回管理员
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        PageResult pageResult = new PageResult(page.getTotal(),page.getResult());
        return pageResult;
    }

    /**
     * 更新员工账号状态（启用/禁用）
     * @param stauts 状态值（1-启用，0-禁用）
     * @param id 要更新状态的员工ID
     */
    @Override
    public void updateStatus(Integer stauts, Long id) {
        employeeMapper.update(
                Employee.builder()
                        .id(id)
                        .status(stauts)
//                        .updateTime(LocalDateTime.now())
                        .build());

    }
    /**
     * 根据ID查询员工详细信息
     * @param id 员工ID
     * @return 对应的员工实体对象
     */
    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        return employee;
    }

    /**
     * 验证员工密码是否正确
     * @param empId 员工ID
     * @param password 待验证的原始密码（明文）
     * @return 验证结果（true-正确，false-错误）
     */
    @Override
    public boolean verifyPassword(long empId, String password) {
        Employee employee = employeeMapper.getById(empId);
        if(employee.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes())))
            return true;
        else
            return false;
    }

    /**
     * 修改员工登录密码
     * @param employeeEditPasswordDTO 包含员工ID和新密码的DTO
     */
    @Override
    public void editPassword(EmployeeEditPasswordDTO employeeEditPasswordDTO) {
        String password = DigestUtils.md5DigestAsHex(employeeEditPasswordDTO.getNewPassword().getBytes());
        Employee employee = Employee.builder()
                .id(employeeEditPasswordDTO.getEmpId())
                .password(password)
                .build();
        employeeMapper.update(employee);

    }

    /**
     * 更新员工基本信息（不包含密码）
     * @param employeeDTO 包含更新信息的员工DTO（需包含ID）
     * @return 更新后的员工实体对象（已过滤密码字段）
     */
    @Override
    public Employee updateEmployee(EmployeeDTO employeeDTO) {
//        Long id = BaseContext.getCurrentId();
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(id);
        employeeMapper.update(employee);
        Employee employee1 =  employeeMapper.getById(employee.getId());
        employee1.setPassword("");// 删除密码
        return employee1;
    }


}
