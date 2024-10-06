package com.zcyi.slimserver.Base;

/**
 * @author ZaoYi
 */

public enum ApiResultCode {

    /**
     * 返回值CODE
     */
    Succeed(200, "请求成功"),
    Failed(400, "请求失败"),
    Error(500, "服务器执行异常");
    private final int _code;
    private final String _msg;

    ApiResultCode(int _code, String _msg) {
        this._code = _code;
        this._msg = _msg;
    }

    public int value() {
        return _code;
    }

    public String message() {
        return _msg;
    }

}
