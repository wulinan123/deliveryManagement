package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeEditPasswordDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping
    @ApiOperation("新建员工方法")
    public Result<EmployeeDTO> save(@RequestBody EmployeeDTO employee){
        log.info("新增员工：{}",employee);
        employeeService.save(employee);
        return Result.success(employee);
    }



    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录方法")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);
        //如何账号已禁用，则不返回 jwt
        if(employee.getStatus() == 0)
            return Result.error("员工账号已禁用");

        else {
            //登录成功后，生成jwt令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
            String token = JwtUtil.createJWT(
                    jwtProperties.getAdminSecretKey(),
                    jwtProperties.getAdminTtl(),
                    claims);
            EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                    .id(employee.getId())
                    .userName(employee.getUsername())
                    .name(employee.getName())
                    .token(token)
                    .build();
            return Result.success(employeeLoginVO);
        }
    }

    @GetMapping("/page")
    @ApiOperation("员工分页查询")
    public Result pageQuery(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("执行分页查询：用户名{},{}页 ，{}",
                employeePageQueryDTO.getName(),
                employeePageQueryDTO.getPage(),
                employeePageQueryDTO.getPageSize());

        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("启用/禁用员工")
    public Result updateStatus(@PathVariable Integer status,Long id){
        if(status == 1) log.info("禁用员工,id：{}",id);
        else log.info("启用员工,id：{}",id);

        employeeService.updateStatus(status,id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("通过 ID 查询员工")
    public  Result getById(@PathVariable Long id){
        log.info("查询用户：{}",id);
        return Result.success(employeeService.getById(id));
    }

    @PutMapping
    @ApiOperation("修改员工信息")
    public Result update(@RequestBody EmployeeDTO employeeDTO){
        Long id =employeeDTO.getId();
        if(id == null)return Result.error("未返回员工 id");
        log.info("修改员工信息,id：{}",id);
        Employee employee = employeeService.updateEmployee(employeeDTO);
        return  Result.success(employee);
    }

    @PutMapping("/editPassword")
    @ApiOperation("修改密码")
    public Result editPassword(@RequestBody EmployeeEditPasswordDTO employeeEditPasswordDTO){
        boolean verify = employeeService.verifyPassword(employeeEditPasswordDTO.getEmpId(),employeeEditPasswordDTO.getOldPassword());
        if(verify) {
            employeeService.editPassword(employeeEditPasswordDTO);
            return Result.success("密码更新成功");
        }
        else return Result.error("密码错误");
    }
    /**
     * 退出
     *
     * @return
     */

    @PostMapping("/logout")
    @ApiOperation("员工登出")
    public Result<String> logout() {
        return Result.success();
    }

}
