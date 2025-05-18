package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.inter.UserService;
import com.sky.utils.HttpClientUtil;
import io.minio.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nan
 * @date 2025/05/18 15:15
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    public static final String WECHAT_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";
    public static final String WECHAT_LOGIN_GRANT_TYPE = "authorization_code";
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;
    /**
     * 1.登录
     * 2.保存用户
     *
     * @param userLoginDTO
     * @return User
     */
    @Override
    public User userLogin(UserLoginDTO userLoginDTO) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("appid", weChatProperties.getAppid());
        requestParams.put("secret", weChatProperties.getSecret());
        requestParams.put("js_code", userLoginDTO.getCode());
        requestParams.put("grant_type", WECHAT_LOGIN_GRANT_TYPE);

        String responseJson = HttpClientUtil.doGet(WECHAT_LOGIN_URL, requestParams);
        JSONObject jsonObject = JSON.parseObject(responseJson);
        String openid = jsonObject.getString("openid");

        if (openid == null) throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        User user = userMapper.selectByOpenId(openid);
        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        return user;

    }
}
