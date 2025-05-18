package com.sky.service.inter;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeEditPasswordDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void save(EmployeeDTO employee);

    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    void updateStatus(Integer stauts, Long id);

    Employee updateEmployee(EmployeeDTO employeeDTO);

    Employee getById(Long id);

    boolean verifyPassword(long empId, String oldPassword);

    void editPassword(EmployeeEditPasswordDTO employeeEditPasswordDTO);
}
