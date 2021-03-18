package com.tonytaotao.webtools.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author tonytaotao
 */
@Data
public class HttpReference implements Serializable {

    /**
     * http url
     */
    private String httpUrl;

    /**
     * http 请求类型
     */
    private String httpMethod;

    /**
     * http 请求头
     */
    private Map<String, String> httpHeader;

    /**
     * http 请求体
     */
    private Object httpBody;

}
