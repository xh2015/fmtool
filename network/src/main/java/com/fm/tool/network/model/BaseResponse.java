package com.fm.tool.network.model;

import java.io.Serializable;

/**
 * Author：gary
 * Email: xuhaozv@163.com
 * description:
 * Date: 2018/3/12 下午12:26
 */
public class BaseResponse<T> implements Serializable{

//    {
//        "status": 200,  // 所有的返回都是200
//        "message": "",  // 返回提示信息，后台返回的都是定义错误码处标记的中文信息
//        "devmsg": "",   // 开发用错误信息
//        "data": {},
//        "f1code": "200000", //返回200000表示提交成功，提交失败情况下，返回失败code
//        "msgLevel": "info"  //info、warn、error
//    }

    public int code;
    public int fmcode;
    public int f1code;
    public String message;
    public String devmsg;
    public String msgLevel;
    public T data;

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", fmcode=" + fmcode +
                ", f1code=" + f1code +
                ", message='" + message + '\'' +
                ", devmsg='" + devmsg + '\'' +
                ", msgLevel='" + msgLevel + '\'' +
                ", data=" + data +
                '}';
    }
}
