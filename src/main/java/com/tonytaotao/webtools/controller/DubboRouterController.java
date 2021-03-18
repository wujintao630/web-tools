package com.tonytaotao.webtools.controller;

import com.tonytaotao.webtools.enums.ProtocolEnum;
import com.tonytaotao.webtools.protocol.ProtocolService;
import com.tonytaotao.webtools.utils.SpringContextUtil;
import com.tonytaotao.webtools.vo.RequestInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

/**
 * @author tonytaotao
 */
@RestController
@RequestMapping(value = "/api")
public class DubboRouterController {

    /**
     *
     * @param requestInfo
     * @param servletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/router")
    @ResponseBody
    public Object router(@RequestBody @Valid RequestInfo requestInfo, ServletRequest servletRequest) {

        ProtocolEnum protocolEnum = ProtocolEnum.getProtocolEnum(requestInfo.getRequestType());

        ProtocolService protocolService = (ProtocolService) SpringContextUtil.getBean(protocolEnum.getServiceName());

        return protocolService.execute(requestInfo, servletRequest);

    }

}
