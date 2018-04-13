package com.fm.tool.network.model;

import java.io.Serializable;

/**
 * Author：gary
 * Email: xuhaozv@163.com
 * description:
 * Date: 2018/3/12 下午12:26
 */
public class BaseResponse<T> implements Serializable{
    public int code;
    public int fmcode;
    public String message;
    public T data;

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", fmcode=" + fmcode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
