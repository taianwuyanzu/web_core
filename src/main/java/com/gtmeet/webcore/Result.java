package com.gtmeet.webcore;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @ProjectName: meet-back
 * @Package: com.chinarui.meetback.util
 * @ClassName: ResponseResponseResult
 * @msgription: 返回数据封装
 * @Author: wood
 * @CreateDate: 2020/10/31 10:58 上午
 * @UpdateUser:
 * @UpdateDate: 2020/10/31 10:58 上午
 * @UpdateRemark:
 * @Version: 1.0
 * @Copyright:
 */
public class Result<T> implements Serializable {
    public static final Integer APP_RESULT_CODE_SUCCESS = 200;
    public static final Integer APP_RESULT_CODE_FAILED = 500;
    public static final String APP_RESULT_CODE_SUCCESS_DESC = "成功";
    public static final String APP_RESULT_CODE_FAILED_DESC = "失败";

    private Integer code;
    private Integer time;
    /**
     * 请求成功后返回的字符串，失败时变成错误提示
     */
    private String msg;
    /**
     * 请求处理是否成功
     */
    private boolean success;
    /**
     * 响应内容实体
     */
    protected T data;

    protected Result() {
    }

    protected Result(boolean success, String msg, T data) {
        this.success = success;
        this.data = data;
        this.time = (int) (System.currentTimeMillis() / 1000);
        if (this.success == true) {
            this.code = APP_RESULT_CODE_SUCCESS;
            this.msg = StringUtils.isEmpty(msg) ? APP_RESULT_CODE_SUCCESS_DESC : msg;
        } else {
            this.code = APP_RESULT_CODE_FAILED;
        }
    }

    protected Result(boolean success, T data, String msg, Throwable throwable) {
        this.success = success;
        this.data = data;
        this.time = (int) (System.currentTimeMillis() / 1000);
        if (this.success == true) {
            this.code = APP_RESULT_CODE_SUCCESS;
            this.msg = this.msg==null ? APP_RESULT_CODE_SUCCESS_DESC : msg;
        } else {
            if (throwable == null || throwable instanceof BusinessException) {
                this.code = APP_RESULT_CODE_FAILED;
                this.msg = msg==null ? APP_RESULT_CODE_FAILED_DESC : msg;
            } else {
                this.code = APP_RESULT_CODE_FAILED;
                this.msg = msg==null ? APP_RESULT_CODE_FAILED_DESC : msg;
            }
        }
    }

    /**
     * 成功
     */
    public static <T> Result ok() {
        return new Result<>(true, null,null);
    }

    /**
     * 成功
     */
    public static <T> Result ok(T data, String msg) {
        return new Result<>(true,msg,data);
    }

    /**
     * 成功
     */
    public static <T> Result ok(T data) {
        return new Result<>(true,null, data);
    }

    /**
     * 失败
     */
    public static <T> Result error(String msg, Throwable throwable) {
        return new Result<>(false, null, msg, throwable);
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        if (data == null || StringUtils.isEmpty(data.toString())) {
            return (T) new HashMap<String, Object>();
        }
        return data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
