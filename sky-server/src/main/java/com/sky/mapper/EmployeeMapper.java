package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);


    @Insert("insert into employee (id_number,username, password, name, phone,sex,status, create_time, update_time, create_user, update_user) " +
            "values (#{idNumber},#{username}, #{password}, #{name},#{phone},#{sex}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Employee employee1);

    /**
     * 分页查询
     */
    Page pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    void update(Employee employee);

}
