package com.tonytaotao.webtools.configs;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author tonytaotao
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        RestTemplate restTemplate = builder.build();

        ClientHttpRequestInterceptor tracerInterceptor = new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
                HttpHeaders httpHeaders = httpRequest.getHeaders();
                String traceId = MDC.get(HttpTracerConfig.MDC_KEY_TRACE_ID);
                if (StringUtils.isNotBlank(traceId)) {
                    httpHeaders.add("x-request-id", traceId);
                }
                return clientHttpRequestExecution.execute(httpRequest, bytes);
            }
        };
        restTemplate.getInterceptors().add(tracerInterceptor);

        return restTemplate;

    }
}
