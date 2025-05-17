package com.sky.service.impl;

import com.sky.mapper.SetMealMapper;
import com.sky.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Nan
 * @date 2025/05/17 21:36
 **/
@Service
@Slf4j
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    SetMealMapper mapper;

}
