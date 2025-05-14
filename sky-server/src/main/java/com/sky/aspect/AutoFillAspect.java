package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
//import jdk.vm.ci.meta.Local;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面类
 */
@Slf4j
@Aspect
@Component
public class AutoFillAspect {
    // 切入点
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){}

    // 前置通知
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("开始 AOP 公共字段自动填充");

        // 1. 获得被拦截方法上的数据库操作类型
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = methodSignature.getMethod().getAnnotation(AutoFill.class);
        if (autoFill != null) {
            OperationType operationType = autoFill.value();
            // 2.获取被拦截参数｜约定需要填充的参数为方法的第一个参数
            Object[] args = joinPoint.getArgs();
            if(args==null || args.length==0)
                return;
            Object entity = args[0];

            //  3.进行赋值
            LocalDateTime time = LocalDateTime.now();
            Long editId = BaseContext.getCurrentId();
            if(operationType == OperationType.INSERT){
                Method methodCreateTime =entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME,LocalDateTime.class);
                Method methodCreateUser =entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,Long.class);
                Method methodUpdateTime =entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
                Method methodUpdateUser =entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);

                methodCreateTime.invoke(entity,time);
                methodCreateUser.invoke(entity,editId);
                methodUpdateTime.invoke(entity,time);
                methodUpdateUser.invoke(entity,editId);

            } else if (operationType == OperationType.UPDATE) {

                Method methodUpdateTime =entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
                Method methodUpdateUser =entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);

                methodUpdateTime.invoke(entity,time);
                methodUpdateUser.invoke(entity,editId);
            }
        }





    }

}
