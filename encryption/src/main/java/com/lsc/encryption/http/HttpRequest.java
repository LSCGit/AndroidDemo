package com.lsc.encryption.http;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by lsc on 2020-03-10 10:26.
 * E-Mail:2965219926@qq.com
 */
public class HttpRequest {
    private static final String HANDSHAKE = "handshake";

    private Request.Builder mBuilder;

    //构造http request用于发起网络请求
    public HttpRequest(String url){
        mBuilder = new Request.Builder()
                .get()
                .url(url);
    }


    /**
     * 发起握手请求，目的是与对方交换公钥。
     * @param callback
     * @param pubKey
     */
    public void handshake(Callback callback,String pubKey){

        //通过在Header里面添加handshake字段，表示当前是一个握手请求
        //并且参数就是DH的公钥。
        mBuilder.addHeader(HANDSHAKE,pubKey);
        request(callback);
        //完成后，去掉字段
        mBuilder.removeHeader(HANDSHAKE);
    }

    public void request(Callback callback){
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(mBuilder.build());
        call.enqueue(callback);
            /*if (call.isExecuted()){//一个请求只允许执行一次
                call.clone().enqueue(callback);
            }else {
                call.enqueue(callback);
            }*/
    }
}
