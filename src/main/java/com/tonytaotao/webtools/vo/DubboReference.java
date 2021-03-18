package com.tonytaotao.webtools.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author tonytaotao
 */
@Data
public class DubboReference implements Serializable {

    /**
     * dubbo 注册中心，默认zk
     */
    private String registryProtocol;

    /**
     * dubbo 注册地址
     */
    private String address;

    /**
     * dubbo 直连Ip
     */
    private String serviceIp;

    /**
     * dubbo 消费者组
     */
    private String consumerGroup;

    /**
     * dubbo service名称
     */
    private String serviceClass;

    /**
     * dubbo 接口版本
     */
    private String version;

    /**
     * dubbo 方法
     */
    private String method;

    /**
     * dubbo 请求参数
     * 按实际接口参数顺序排列
     */
    private List<Object> requestDataList;
}
