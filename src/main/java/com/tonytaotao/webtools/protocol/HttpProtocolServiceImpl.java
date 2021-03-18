package com.tonytaotao.webtools.protocol;

import com.tonytaotao.webtools.vo.HttpReference;
import com.tonytaotao.webtools.vo.RequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author tonytaotao
 */
@Service("httpProtocolService")
public class HttpProtocolServiceImpl implements ProtocolService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpProtocolServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Object execute(RequestInfo requestInfo, Object requestContext) {

        HttpReference httpReference = requestInfo.getHttpReference();

        try {
            String url = httpReference.getHttpUrl();
            HttpMethod httpMethod = HttpMethod.resolve(httpReference.getHttpMethod());
            MultiValueMap<String, String> headers = createRequestHeaders(httpReference.getHttpHeader());

            RequestEntity requestEntity = new RequestEntity<>(httpReference.getHttpBody(), headers, httpMethod, new URI(url));
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(requestEntity, byte[].class);

            String responseBody = new String(responseEntity.getBody(), StandardCharsets.UTF_8);
            return responseBody;
        } catch (Exception e) {
            LOGGER.error("请求异常", e);
            return e.getMessage();
        }
    }

    private MultiValueMap<String, String> createRequestHeaders(Map<String, String> headerMap) {
        HttpHeaders headers = new HttpHeaders();
        headerMap.entrySet().forEach(entry -> {
            headers.add(entry.getKey(), entry.getValue());
        });
        headers.add("Keep-Alive", "timeout=30, max=1000");
        return headers;
    }

}
