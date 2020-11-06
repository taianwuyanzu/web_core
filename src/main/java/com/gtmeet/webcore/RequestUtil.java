package com.gtmeet.webcore;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: gt-meet
 * @Package: com.meet.util.adapter
 * @ClassName: RequestUtil
 * @Description: java类作用描述
 * @Author: wood
 * @CreateDate: 2020/11/3 2:21 下午
 * @UpdateUser:
 * @UpdateDate: 2020/11/3 2:21 下午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright:
 */
public class RequestUtil {
    public static final String ERROR_ATTRIBUTE = DefaultErrorAttributes.class.getName()
            + ".ERROR";


    public static String getPath(HttpServletRequest request) {
        return (String) request.getAttribute("javax.servlet.error.request_uri");
    }

    public static Throwable getError(HttpServletRequest request) {
        Throwable exception = (Throwable) request.getAttribute(ERROR_ATTRIBUTE);
        if (exception == null) {
            exception = (Throwable) request.getAttribute("javax.servlet.error.exception");
        }
        return exception;
    }
}