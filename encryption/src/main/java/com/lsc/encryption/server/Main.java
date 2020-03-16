package com.lsc.encryption.server;

import com.lsc.encryption.server.http.HttpCallback;
import com.lsc.encryption.server.http.HttpServer;

/**
 * Created by lsc on 2020-03-10 10:44.
 * E-Mail:2965219926@qq.com
 */
public class Main {

    public static void main(String[] args) {

        HttpServer server = new HttpServer(new HttpCallback() {
            @Override
            public byte[] onResponse(String request) {

                return "lsc-javaServer".getBytes();
            }
        });
        server.startHttpServer();

    }
}
