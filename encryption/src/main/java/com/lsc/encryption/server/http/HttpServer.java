package com.lsc.encryption.server.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lsc on 2020-03-10 10:48.
 * E-Mail:2965219926@qq.com
 */
public class HttpServer {


    private boolean mRunning;

    private HttpCallback mCallback;

    public HttpServer(HttpCallback callback){
        mCallback = callback;
    }

    /**
     * 编写接口函数，用于启动服务
     */
    public void startHttpServer(){
        if (mRunning){
            //若果在运行，可以返回错误信息
            return;
        }
        mRunning = true;
        //SS三步法 1：启动Socket
        try {
            ServerSocket serverSocket = new ServerSocket(80);
            while (mRunning) {//绑定，每个client对应一个Socket，这里需要并发管理
                //SS三步法 2 ：等待客户端连接
                Socket socket = serverSocket.accept();
                ExecutorService threadPool = Executors.newCachedThreadPool();
                threadPool.execute(new HttpThread(socket,mCallback));
            }
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
