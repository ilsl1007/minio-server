package com.hlxd.minioserver.vo;

import io.minio.ObjectStat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Classname MinioObject
 * @Description 存储对象的元数据
 * @Author yulj
 * @Date: 2019/07/31 00:49
 */
@Data
@AllArgsConstructor
public class MinioObject {

    private String bucketName;
    private String name;
    private Date createdTime;
    private Long length;
    private String etag;
    private String contentType;
    private Map<String, List<String>> httpHeaders;

    public MinioObject(ObjectStat os) {
        this.bucketName = os.bucketName();
        this.name = os.name();
        this.createdTime = os.createdTime();
        this.length = os.length();
        this.etag = os.etag();
        this.contentType = os.contentType();
        this.httpHeaders = os.httpHeaders();
    }
}
