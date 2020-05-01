package com.ygz.aspen.common.base;

/**
 * @author ygz
 */
public class BusinessException extends Exception{

    private static final long serialVersionUID = 3455708526465670030L;

    public BusinessException(String msg){
        super(msg);
    }
}