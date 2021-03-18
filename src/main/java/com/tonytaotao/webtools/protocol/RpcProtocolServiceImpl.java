package com.tonytaotao.webtools.protocol;

import com.tonytaotao.webtools.vo.DubboReference;
import com.tonytaotao.webtools.vo.RequestInfo;
import org.springframework.stereotype.Service;

/**
 * @author tonytaotao
 */
@Service("rpcProtocolService")
public class RpcProtocolServiceImpl implements ProtocolService {

    @Override
    public Object execute(RequestInfo requestInfo, Object requestContext) {

        DubboReference dubboReference = requestInfo.getDubboReference();

        return DubboReferenceInvoke.invoke(dubboReference);

    }

}
