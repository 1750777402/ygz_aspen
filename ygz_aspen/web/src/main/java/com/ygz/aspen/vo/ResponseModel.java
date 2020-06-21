package com.ygz.aspen.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class ResponseModel<T> implements Serializable {
    private static final long serialVersionUID = -1241360949457314497L;
    private T data;
    private String message;
    private Integer code;

    public ResponseModel() {
    }

    public ResponseModel(Integer code, String message, T data){
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public ResponseModel(ResultMsgEnum resultMsgEnum){
        this.code = resultMsgEnum.getCode();
        this.message = resultMsgEnum.getMessage();
    }

    public ResponseModel(T data){
        this.data = data;
        this.code = ResultMsgEnum.SUCCESS.getCode();
        this.message = ResultMsgEnum.SUCCESS.getMessage();
    }


}
