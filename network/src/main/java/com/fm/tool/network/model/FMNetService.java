package com.fm.tool.network.model;

/**
 * Author：gary
 * Email: xuhaozv@163.com
 * description:fm服务器相关参数
 * Date: 2019/1/7 3:24 PM
 */
public interface FMNetService {

    //错误信息级别
    String MESSAGE_LEVEL_INFO = "info";
    String MESSAGE_LEVEL_WARN = "warn";
    String MESSAGE_LEVEL_ERROR = "error";
    String MESSAGE_LEVEL_FATAL = "fatal";

    //f1code 自定义错误码 前端可以根据错误码，转换成国际化信息展示

    int FM_CODE_SUCCESS = 200000;//提交成功
}
