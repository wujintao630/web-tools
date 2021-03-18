package com.tonytaotao.webtools.protocol;

import com.tonytaotao.webtools.vo.RequestInfo;

/**
 * @author tonytaotao
 */
public interface ProtocolService {

    Object execute(RequestInfo requestInfo, Object requestContext);

}
