package com.tonytaotao.webtools.enums;

/**
 * @author tonytaotao
 */
public enum ProtocolEnum {

    HTTP("http", "httpProtocolService", "http请求"),
    RPC("rpc", "rpcProtocolService", "rpc请求");

    private String code;

    private String serviceName;

    private String remark;

    ProtocolEnum(String code, String serviceName, String remark) {
        this.code = code;
        this.serviceName = serviceName;
        this.remark = remark;
    }

    public static ProtocolEnum getProtocolEnum(String code) {
        for(ProtocolEnum protocolEnum : ProtocolEnum.values()) {
            if (protocolEnum.code.equals(code)) {
                return protocolEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getRemark() {
        return remark;
    }
}
