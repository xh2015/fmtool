package com.fm.tool.network.callback;

import com.fm.tool.network.model.BaseResponse;
import com.fm.tool.network.model.SimpleResponse;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.exception.StorageException;
import com.lzy.okgo.model.Response;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;

;

/**
 * Author：gary
 * Email: xuhaozv@163.com
 * description:默认将返回的数据解析成需要的Bean,可以是 BaseBean，String，List，Map
 * Date: 2018/3/12 上午11:04
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        //这里得到第二层泛型的所有类型: BaseResponse<Bean>
        Type type = params[0];

        if (!(type instanceof ParameterizedType)) {
            throw new IllegalStateException("没有填写泛型参数!");
        }
        //这里得到第二层数据的真是类型: BaseResponse
        Type rawType = ((ParameterizedType) type).getRawType();
        //这里得到第二层数据的泛型的真实类型: Bean
        Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];

        ResponseBody body = response.body();
        if (body == null) {
            return null;
        }

        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(body.charStream());
        //写入的参数类型不对应那么久按照原始解析
        if (rawType != BaseResponse.class) {
            T data = gson.fromJson(jsonReader, type);
            response.close();
            return data;
        } else {
            if (typeArgument == Void.class) {
                //无数据类型，new DialogCallBack<BaseResponse<Void>> 以这种形式传递的泛型
                SimpleResponse simpleResponse = gson.fromJson(jsonReader, type);
                response.close();
                return (T) simpleResponse.toBaseResponse();
            } else {
                //有数据类型，表示有data
                BaseResponse baseResponse = gson.fromJson(jsonReader, type);
                response.close();
                int fmcode = baseResponse.fmcode;
                if (fmcode == 0) {
                    return (T) baseResponse;
                } else {
                    throw new IllegalStateException(fmcode + "");
                }
            }
        }
    }

    @Override
    public void onError(Response<T> response) {
        Throwable exception = response.getException();
        if (exception != null) {
            exception.printStackTrace();
        }

        if (exception instanceof UnknownHostException || exception instanceof ConnectException) {
            // 这种异常的原因就是你网络的问题
            // 确定你url地址写对了？
            // 你的网络环境是正常的？还是只能内网才能访问？
            // 是否开代理了，导致连不上？

            System.out.println("网络连接失败，请连接网络!");
        } else if (exception instanceof SocketTimeoutException) {
            // 这很明显就是连接超时了嘛
            // ，我这里故意把超时时间设置为1ms，所以故意让他报的这个异常，解决办法就是
            // 确定网络环境良好
            // 超时时间设置是否正确
            System.out.println("网络请求超时");
        } else if (exception instanceof HttpException) {
            System.out.println("查看服务端响应码");
        } else if (exception instanceof StorageException) {
            System.out.println("sd卡不存在或没有权限");
        } else if (exception instanceof IllegalStateException) {
            String message = exception.getMessage();
            System.out.println(message);
        }
    }
}
