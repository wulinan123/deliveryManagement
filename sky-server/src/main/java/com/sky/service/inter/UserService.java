package com.sky.service.inter;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

public interface UserService {
    User userLogin(UserLoginDTO userLoginDTO);
}
