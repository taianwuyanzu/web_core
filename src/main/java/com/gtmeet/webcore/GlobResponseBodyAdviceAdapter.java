package com.gtmeet.webcore;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.gtmeet.webcore.RequestUtil.getError;
import static com.gtmeet.webcore.ResponseResultUtil.toRestResponse;


/**
 * @ProjectName: meet-back
 * @Package: com.chinarui.meetback.util.adapter
 * @ClassName: GlobResponseBodyAdviceAdapter
 * @Description: BusinessException异常拦截切面
 * @Author: wood
 * @CreateDate: 2020/10/31 2:59 下午
 * @UpdateUser:
 * @UpdateDate: 2020/10/31 2:59 下午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright:
 */
@Slf4j
@RestControllerAdvice
public class GlobResponseBodyAdviceAdapter implements ResponseBodyAdvice<Object> {

    @Value("${meetback.exception.show}")
    private boolean showException;

    private Logger logger = LoggerFactory.getLogger(GlobResponseBodyAdviceAdapter.class);

    public GlobResponseBodyAdviceAdapter() {
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest httpRequest, ServerHttpResponse httpResponse) {
        HttpServletRequest request = ((ServletServerHttpRequest) httpRequest).getServletRequest();
        HttpServletResponse response = ((ServletServerHttpResponse) httpResponse).getServletResponse();
        Throwable throwable = getError(request);
        if (throwable instanceof BusinessException) {
            return toRestResponse(throwable, showException);
        }else if(throwable !=null){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return body;
    }

}
