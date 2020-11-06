package com.gtmeet.webcore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: gt-meet
 * @Package: com.meet.util.adapter
 * @ClassName: BasicErrorController
 * @Description: java类作用描述
 * @Author: wood
 * @CreateDate: 2020/11/3 2:10 下午
 * @UpdateUser:
 * @UpdateDate: 2020/11/3 2:10 下午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright:
 */
@Slf4j
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class BasicErrorController extends AbstractErrorController {
    private final ErrorProperties errorProperties;

    /**
     * Create a new {@link org.springframework.boot.autoconfigure.web} instance.
     *
     * @param errorAttributes the error attributes
     * @param errorProperties configuration properties
     */
    public BasicErrorController(ErrorAttributes errorAttributes,
                                ErrorProperties errorProperties) {
        this(errorAttributes, errorProperties,
                Collections.<ErrorViewResolver>emptyList());
    }

    /**
     * Create a new {@link org.springframework.boot.autoconfigure.web} instance.
     *
     * @param errorAttributes    the error attributes
     * @param errorProperties    configuration properties
     * @param errorViewResolvers error view resolvers
     */
    public BasicErrorController(ErrorAttributes errorAttributes,
                                ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorViewResolvers);
        Assert.notNull(errorProperties, "ErrorProperties must not be null");
        this.errorProperties = errorProperties;
    }

    @Override
    public String getErrorPath() {
        return this.errorProperties.getPath();
    }

    @Value("${meetback.exception.show:true}")
    private boolean showException;

    @RequestMapping
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        //转换异常
        status = HttpStatus.OK;
        Throwable throwable = RequestUtil.getError(request);
        return new ResponseEntity(ResponseResultUtil.toMap(throwable, showException), status);

    }
}
