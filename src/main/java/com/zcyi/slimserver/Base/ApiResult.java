package com.zcyi.slimserver.Base;

import lombok.AccessLevel;
import lombok.Setter;

/**
 * Api统一响应实体类
 * @author ZaoYi
 * @param <T> 业务实体类
 */
@Setter(AccessLevel.PRIVATE)
public class ApiResult<T> {

    public int resultCode;
    public T data;
    public String message;
    public boolean success;

    public static <T> ApiResult<T> success() {
        return success(ApiResultCode.Succeed.message(), null, ApiResultCode.Succeed.value());
    }
    public static <T> ApiResult<T> success(T data) {
        return success(ApiResultCode.Succeed.message(), data, ApiResultCode.Succeed.value());
    }

    public static <T> ApiResult<T> success(String msg) {
        return success(msg, null);
    }

    public static <T> ApiResult<T> success(String msg, T data) {
        return success(msg, data, ApiResultCode.Succeed.value());
    }

    public static <T> ApiResult<T> success(String msg, T data, int code) {
        ApiResult<T> result = new ApiResult<>();
        result.setMessage(msg);
        result.setData(data);
        result.setResultCode(code);
        result.setSuccess(true);

        return result;
    }

    public static <T> ApiResult<T> failed() {
        return failed(ApiResultCode.Failed.message(), null, ApiResultCode.Failed.value());
    }

    public static <T> ApiResult<T> failed(String msg) {
        return failed(msg, null);
    }

    public static <T> ApiResult<T> failed(String msg, T data) {
        return failed(msg, data, ApiResultCode.Failed.value());
    }

    public static <T> ApiResult<T> failed(String msg, T data, int code) {
        ApiResult<T> result = new ApiResult<>();
        result.setMessage(msg);
        result.setData(data);
        result.setResultCode(code);
        result.setSuccess(false);
        return result;
    }

    public static <T> ApiResult<T> error(String msg) {
        ApiResult<T> result = new ApiResult<>();
        result.setMessage(msg);
        result.setResultCode(ApiResultCode.Error.value());

        result.setSuccess(false);
        return result;
    }
}
