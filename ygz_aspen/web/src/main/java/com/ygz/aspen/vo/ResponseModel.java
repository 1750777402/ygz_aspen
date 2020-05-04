package com.ygz.aspen.vo;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;


public class ResponseModel<T> implements Serializable {
    private static final long serialVersionUID = -1241360949457314497L;
    private T result;
    private String message;
    private Integer code;

    public ResponseModel() {
    }

    public ResponseModel(Integer code, String message, T res){
        this.message = message;
        this.code = code;
        this.result = res;
    }

    public ResponseModel(ResultMsgEnum resultMsgEnum){
        this.code = resultMsgEnum.getCode();
        this.message = resultMsgEnum.getMessage();
    }

    public ResponseModel(T result){
        this.result = result;
        this.code = ResultMsgEnum.SUCCESS.getCode();
        this.message = ResultMsgEnum.SUCCESS.getMessage();
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String toString() {
        return "ResponseModel [result=" + this.result +  ", message=" + this.message + ", code=" + this.code + "]";
    }
}
