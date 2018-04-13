package com.fm.tool.network.model;

import java.io.Serializable;

/**
 * Author：gary
 * Email: xuhaozv@163.com
 * description:
 * Date: 2018/3/12 下午2:34
 */
public class SimpleResponse implements Serializable {

    public int code;
    public int fmcode;
    public String message;

    public BaseResponse toBaseResponse(){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.code = code;
        baseResponse.fmcode = fmcode;
        baseResponse.message = message;
        return baseResponse;
    }
}
