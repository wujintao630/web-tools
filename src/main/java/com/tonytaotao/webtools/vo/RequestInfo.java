package com.tonytaotao.webtools.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tonytaotao
 */
@Data
public class RequestInfo implements Serializable {

    /**
     * 请求类型
     * http
     * rpc
     */
    private String requestType;

    /**
     * http请求相关参数
     */
    private HttpReference httpReference;

    /**
     * rpc请求相关参数
     */
    private DubboReference dubboReference;

    /**
     *
     http 实例
         {
         "requestType":"http",
         "httpReference":{
                         "httpUrl":"绝对地址",
                         "httpMethod":"POST",
                         "httpHeader":{
                                     "Content-Type":"application/json",
                                     "Cookie":"sid=xxx"
                                     },
                         "httpBody":{
                                     "page":1,
                                     "limit":2
                                     }
                         }
         }

     rpc 示例
         {
         "requestType":"rpc",
         "dubboReference":{
                         "address":"注册中心地址，多个用英文分号隔开",
                         "version":"接口版本",
                         "serviceClass":"api类",
                         "method":"api方法",
                         "requestDataList":[
                                             {
                                             "page":1,
                                             "limit":2
                                             }
                                         ]
                         }
         }
     */
}
