package com.tonytaotao.webtools.protocol;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericException;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.tonytaotao.webtools.vo.DubboReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tonytaotao
 */
@Slf4j
public class DubboReferenceInvoke {

    /**
     * 当前应用的信息
     */
    private static ApplicationConfig application = new ApplicationConfig();
    static {
        application.setName("dubbo-tools");
    }

    /**
     * 注册中心信息缓存
     */
    private static Map<String, RegistryConfig> registryConfigCache = new ConcurrentHashMap<>();

    /**
     * ReferenceConfig缓存
     */
    private static Map<String, ReferenceConfig> referenceCache = new ConcurrentHashMap<>();

    /**
     * 获取注册中心信息
     * @param protocol
     * @param address
     * @return
     */
    private static RegistryConfig getRegistryConfig(String protocol, String address) {
        String key = protocol + "-" + address;
        RegistryConfig registryConfig = registryConfigCache.get(key);
        if (null == registryConfig) {
            registryConfig = new RegistryConfig();
            if (StringUtils.isEmpty(protocol)) {
                protocol = "zookeeper";
            }
            registryConfig.setProtocol(protocol);
            registryConfig.setAddress(address);

            registryConfigCache.put(key, registryConfig);
        }
        return registryConfig;
    }


    private static ReferenceConfig getReferenceConfig(String protocol, String address, String serviceIp, String interfaceName, String group, String version) {

        String referenceKey = "";
        if (StringUtils.isNotBlank(serviceIp)) {
            referenceKey = interfaceName + "_" + group + "_" + version + "_" + serviceIp;
        } else {
            referenceKey = interfaceName + "_" + address + "_" + group + "_" + version;
        }

        ReferenceConfig<GenericService> referenceConfig = referenceCache.get(referenceKey);

        try {
            if (referenceConfig == null) {
                referenceConfig = new ReferenceConfig<>();
                referenceConfig.setApplication(application);
                if(StringUtils.isNotBlank(address)) {
                    referenceConfig.setRegistry(getRegistryConfig(protocol, address));
                }
                if (StringUtils.isNotBlank(serviceIp)) {
                    referenceConfig.setUrl("dubbo://" + serviceIp);
                }
                if (StringUtils.isNotEmpty(group)) {
                    referenceConfig.setGroup(group);
                }
                if (StringUtils.isNotEmpty(version)) {
                    referenceConfig.setVersion(version);
                }

                referenceConfig.setInterface(interfaceName);
                referenceConfig.setId(interfaceName);
                referenceConfig.setTimeout(60000);

                referenceConfig.setGeneric(true);

                referenceCache.put(referenceKey, referenceConfig);
            }
        } catch (Exception e) {
            log.error("getReferenceConfig error", e);
        }

        return referenceConfig;
    }

    private static void deleteReferenceConfig(String address, String serviceIp, String interfaceName, String group, String version) {

        String referenceKey = "";
        if (StringUtils.isNotBlank(serviceIp)) {
            referenceKey = interfaceName + "_" + group + "_" + version + "_" + serviceIp;
        } else {
            referenceKey = interfaceName + "_" + address + "_" + group + "_" + version;
        }

        referenceCache.remove(referenceKey);
    }

    public static Object invoke(DubboReference dubboReference) {
        String registryProtocol = dubboReference.getRegistryProtocol();
        String address = dubboReference.getAddress();
        String serviceIp = dubboReference.getServiceIp();
        String serviceClass = dubboReference.getServiceClass();
        String consumerGroup = dubboReference.getConsumerGroup();
        String version = dubboReference.getVersion();
        String method = dubboReference.getMethod();
        List<Object> requestDataList = dubboReference.getRequestDataList();

        if (StringUtils.isBlank(serviceClass)) {
            return "serviceClass can not be null";
        }

        if (StringUtils.isBlank(version)) {
            return "version can not be null";
        }

        if (StringUtils.isBlank(method)) {
            return "method can not be null";
        }

        if (StringUtils.isBlank(address) && StringUtils.isBlank(serviceIp)) {
            return "address or serviceIp can not be null";
        }

        ReferenceConfig reference = getReferenceConfig(registryProtocol, address, serviceIp, serviceClass, consumerGroup, version);
        if (null != reference) {

            try {
                GenericService genericService = (GenericService) reference.get();
                if (genericService == null) {
                    return "GenericService 不存在:" + serviceClass;
                }
                Object[] paramObject = null;
                if (!CollectionUtils.isEmpty(requestDataList)) {
                    paramObject = new Object[requestDataList.size()];
                    for (int i = 0; i < requestDataList.size(); i++) {
                        paramObject[i] = requestDataList.get(i);
                    }
                }

                Object result = genericService.$invoke(method, null, paramObject);
                return result;
            } catch (GenericException e) {
                log.error("异常", e);
                deleteReferenceConfig(address, serviceIp,serviceClass, consumerGroup, version);
                return e.getExceptionMessage();
            }
        }
        return null;
    }


    public static String[] getMethodParamType(String interfaceName, String methodName) {
        try {
            //创建类
            Class<?> class1 = Class.forName(interfaceName);
            //获取所有的公共的方法
            Method[] methods = class1.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] paramClassList = method.getParameterTypes();
                    String[] paramTypeList = new String[paramClassList.length];
                    int i = 0;
                    for (Class className : paramClassList) {
                        paramTypeList[i] = className.getTypeName();
                        i++;
                    }
                    return paramTypeList;
                }
            }
        } catch (Exception e) {
            log.error("interfaceName= " + interfaceName + ", getMethodParamType error", e);
        }
        return null;
    }


}
