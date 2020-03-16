package com.lsc.encryption.server.http;

/**
 * Created by lsc on 2020-03-10 11:04.
 * E-Mail:2965219926@qq.com
 *
 * Http请求回调，将Http的请求消息传出，并将业务数据返回。
 */
public interface HttpCallback {

    /**
     *
     * 收到消息的回调通知
     * @param request
     * @return
     */
    byte[] onResponse(String request);
}
