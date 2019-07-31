package com.hlxd.minioserver.controller;

import com.hlxd.minioserver.service.MinioFileService;
import com.hlxd.minioserver.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Classname MiniFilecontroller
 * @Description mino 文件控制器
 * @Author yulj
 * @Date: 2019/07/31 01:02
 */
@RestController
@RequestMapping("/minio")
@Slf4j
@Api(value = "minio", tags = "文件管理")
public class MiniFilecontroller {

    @Autowired
    private MinioFileService minioFileService;

    /**
     * 根据fileName删除文件管理
     *
     * @param fileName
     * @return
     */
    @ApiOperation(value = "根据fileName删除文件", notes = "根据fileName删除文件")
    @DeleteMapping("/{fileName}")
    public R removeByFileName(@PathVariable String fileName) {
        return this.minioFileService.deleteFile(fileName);
    }

    /**
     * 上传文件
     * 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
     *
     * @param file 资源
     * @return R(bucketName, filename)
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传文件", notes = "文件上传")
    public R upload(@RequestParam("file") MultipartFile file) {
        return this.minioFileService.uploadFile(file);
    }

    /**
     * 获取文件
     *
     * @param fileName 文件空间/名称
     * @param response
     * @return
     */
    @GetMapping("/{fileName}")
    @ApiOperation(value = "根据fileName获取文件", notes = "根据fileName获取文件")
    public void file(@PathVariable String fileName, HttpServletResponse response) {
        this.minioFileService.getFile(fileName, response);
    }
}
