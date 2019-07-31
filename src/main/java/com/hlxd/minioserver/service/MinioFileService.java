package com.hlxd.minioserver.service;

import com.hlxd.minioserver.util.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Classname MinioFileService
 * @Description minio文件service层
 * @Author yulj
 * @Date: 2019/07/31 01:09
 */
public interface MinioFileService {

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    R uploadFile(MultipartFile file);

    /**
     * 读取文件
     *
     * @param fileName
     * @param response
     */
    void getFile(String fileName, HttpServletResponse response);

    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    R deleteFile(String fileName);
}
