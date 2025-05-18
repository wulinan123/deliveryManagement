package com.sky.controller.user;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.inter.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nan
 * @date 2025/05/18 15:11
 **/
@RestController
@RequestMapping("/user/user")
@Api("用户接口")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation("登录")
    public Result<UserLoginVO> userLongin(@RequestBody UserLoginDTO userLoginDTO){

        User user = userService.userLogin(userLoginDTO);
        HashMap<String, Object> map = new HashMap<>();
        map.put(JwtClaimsConstant.USER_ID, (Object) user.getId());
        String jwt = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), map);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .token(jwt)
                .id(user.getId())
                .openid(user.getOpenid())
                .build();
        return Result.success(userLoginVO);
    }
}
