package com.gtmeet.webcore;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ProjectName: gt-meet
 * @Package: com.meet.util.adapter
 * @ClassName: ResponseResultUtil
 * @Description: java类作用描述
 * @Author: wood
 * @CreateDate: 2020/11/3 2:25 下午
 * @UpdateUser:
 * @UpdateDate: 2020/11/3 2:25 下午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright:
 */
@Slf4j
public class ResponseResultUtil {
    public static Result toRestResponse(Throwable throwable, Boolean showThrowable) {
        String message = null;
        if (throwable != null) {
            message = StringUtils.trimToEmpty(throwable.getMessage());
            if (throwable instanceof HttpMessageNotReadableException) {
                message = "请求中包含错误格式的数据,请检查";
            }
            if (throwable instanceof NullPointerException) {
                message = "系统发生了未知的错误";
            }
            if (throwable instanceof org.springframework.web.bind.MissingServletRequestParameterException) {
                message = "缺少请求参数";
            }
            if (throwable instanceof BusinessException) {
                if (!StringUtils.isEmpty(throwable.getMessage())) {
                    message = StringUtils.trimToEmpty(throwable.getMessage());
                }
            }
        }
        if (StringUtils.isBlank(message)) {
            message = "无权限或服务异常";
        }
        return Result.error(message, showThrowable ? throwable : null);
    }

    public static Map<String, Object> toMap(Throwable throwable, Boolean showThrowable) {
        Result responseResult = toRestResponse(throwable, showThrowable);
        Map<String, Object> returnMap = new LinkedHashMap<>();
        Class type = responseResult.getClass();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class") && !propertyName.equals("throwable")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(responseResult);
                    if (result != null) {
                        returnMap.put(propertyName, result);
                    } else {
                        returnMap.put(propertyName, "");
                    }
                } else if (propertyName.equals("throwable")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(responseResult);
                    if (result != null && showThrowable) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        if (((Throwable) result).getStackTrace() != null && ((Throwable) result).getStackTrace().length > 1) {
                            Throwable t1 = new Throwable(((Throwable) result).getMessage());
                            t1.setStackTrace(new StackTraceElement[]{((Throwable) result).getStackTrace()[0]});
                            result = t1;
                        }
                        returnMap.put(propertyName, objectMapper.writeValueAsString(result));
                    } else {
                        returnMap.put(propertyName, "");
                    }
                }
            }
        } catch (Exception e) {
            log.error("e:{}" + e);
        }

        return returnMap;
    }
}
