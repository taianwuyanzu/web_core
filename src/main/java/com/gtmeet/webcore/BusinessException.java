package com.gtmeet.webcore;

/**
 * @ProjectName: meet-back
 * @Package: com.chinarui.meetback.util.exception
 * @ClassName: BusinessException
 * @Description: java类作用描述
 * @Author: wood
 * @CreateDate: 2020/10/31 11:59 上午
 * @UpdateUser:
 * @UpdateDate: 2020/10/31 11:59 上午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright:
 */
public class BusinessException extends RuntimeException {
    /**
     * 业务异常
     *
     * @param message 信息
     */
    public BusinessException(String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
