package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author Nan
 * @description 员工映射器，用于处理员工表的数据库操作
 **/
@Mapper
public interface EmployeeMapper {
    
    /**
     * 根据员工ID查询员工信息
     * @param id 员工ID
     * @return 员工实体对象
     */
    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);

    /**
     * 根据用户名查询员工信息
     * @param username 用户名
     * @return 员工实体对象
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 新增员工
     * @param employee 员工实体对象
     */
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into employee (id_number,username, password, name, phone,sex,status, create_time, update_time, create_user, update_user) " +
            "values (#{idNumber},#{username}, #{password}, #{name},#{phone},#{sex}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Employee employee);

    /**
     * 分页查询员工信息
     * @param employeePageQueryDTO 分页查询参数
     * @return 分页结果
     */
    Page pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 更新员工信息
     * @param employee 员工实体对象
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);

}
