package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.MinioUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api("通用接口")
@Slf4j
public class CommonController {
    @Autowired
    private MinioUtil minioUtil;

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public Result<String > upload(MultipartFile file)  {
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf("."));//截取文件后缀
        String ObjectName = UUID.randomUUID().toString() + extension; // 使用 UUID+后缀的格式保存
        log.info("上传文件：{}",file.getOriginalFilename());
        String url = null;
        try {
            url = minioUtil.upload(file.getBytes(),ObjectName);
        } catch (IOException e) {
            log.error("上传错误");
        }
        return Result.success(url);//TODO
    }
}
