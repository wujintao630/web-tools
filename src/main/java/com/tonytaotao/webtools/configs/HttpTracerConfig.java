package com.tonytaotao.webtools.configs;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author tonytaotao
 */
@Component
public class HttpTracerConfig implements ServletRequestListener {

    public static final String MDC_KEY_TRACE_ID = "traceId";

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletRequest servletRequest = sre.getServletRequest();
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String traceId = httpServletRequest.getHeader("x-request-id");
            if (traceId == null) {
                traceId = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
            }
            MDC.put(MDC_KEY_TRACE_ID, traceId);
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        MDC.remove(MDC_KEY_TRACE_ID);
    }
}
