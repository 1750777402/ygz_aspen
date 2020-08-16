package com.ygz.aspen.common.base;

public enum ResultMsgEnum {

    SUCCESS(1001, "操作成功"),
    FAILED(2001,"系统错误"),
    UNAUTHORIZED(2002,"获取登录用户信息失败"),
    ERROR(2003, "操作失败"),
    USER_NO_POWER(2004, "权限不足"),
    PARAM_ERROR(2005, "参数错误"),
    DATA_NOT_FOUND(2006, "数据查不到");

    private Integer code;

    private String message;

    ResultMsgEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
