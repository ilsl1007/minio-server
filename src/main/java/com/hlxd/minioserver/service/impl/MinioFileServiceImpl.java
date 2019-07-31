package com.hlxd.minioserver.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.hlxd.minioserver.service.MinioFileService;
import com.hlxd.minioserver.service.MinioTemplate;
import com.hlxd.minioserver.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname MinioFileServiceImpl
 * @Description minio文件service层实现
 * @Author yulj
 * @Date: 2019/07/31 01:11
 */
@Slf4j
@Service
public class MinioFileServiceImpl implements MinioFileService {

    @Autowired
    private MinioTemplate minioTemplate;

    @Value("${minio.default-bucket}")
    private String defaultBucket;

    @Override
    public R uploadFile(MultipartFile file) {
        String fileId = IdUtil.simpleUUID();
        String fileName = fileId + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
        Map<String, String> resultMap = new HashMap<>(4);
        resultMap.put("fileId", fileId);
        resultMap.put("bucketName", defaultBucket);
        resultMap.put("fileName", fileName);
        log.info("上传文件{}", resultMap.toString());

        try {
            minioTemplate.putObject(defaultBucket, fileName, file.getInputStream());
        } catch (Exception e) {
            log.error("上传失败", e);
            return R.failed(e.getLocalizedMessage());
        }
        return R.ok(resultMap);
    }

    @Override
    public void getFile(String fileName, HttpServletResponse response) {
        try (InputStream inputStream = minioTemplate.getObject(defaultBucket, fileName)) {
            if (inputStream != null) {
                byte[] bytes = input2byte(inputStream);
                response.getOutputStream().write(bytes);
            }
        } catch (Exception e) {
            log.error("文件读取异常", e);
        }
    }

    public static final byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    @Override
    public R deleteFile(String fileName) {
        log.info("删除文件{}", fileName);
        try {
            minioTemplate.removeObject(defaultBucket, fileName);
        } catch (Exception e) {
            log.error("文件删除失败", e);
            return R.failed(e.getLocalizedMessage());
        }
        return R.ok("删除成功！");
    }
}
