package com.ygz.aspen.vo;

import org.springframework.http.HttpStatus;

public class ResponseHelper {

    public ResponseHelper() {
    }

    public static <T> ResponseModel<T> notFound(String message) {
        ResponseModel response = new ResponseModel();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setCode(HttpStatus.NOT_FOUND.getReasonPhrase());
        response.setMessage(message);
        return response;
    }

    public static <T> ResponseModel<T> internalServerError(String message) {
        ResponseModel response = new ResponseModel();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        response.setMessage(message);
        return response;
    }

    public static <T> ResponseModel<T> validationFailure(String message) {
        ResponseModel response = new ResponseModel();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setCode(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setMessage(message);
        return response;
    }

    public static <T> ResponseModel<T> buildResponseModel(T result) {
        ResponseModel response = new ResponseModel();
        response.setStatus(HttpStatus.OK.value());
        response.setCode(HttpStatus.OK.getReasonPhrase());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setResult(result);
        return response;
    }
}