package com.lsc.encryption.server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by lsc on 2020-03-10 10:54.
 * E-Mail:2965219926@qq.com
 *
 * 一个Socket一个线程，
 */
public class HttpThread implements Runnable{

    private Socket mSocket;

    //回调监听器，由业务方传入，网络库不做任何处理
    private HttpCallback mHttpCallback;

    public HttpThread(Socket socket,HttpCallback callback){
        mSocket = socket;
        mHttpCallback = callback;
    }

    @Override
    public void run() {

        //任务 1 ：读取客户端请求，2，根据业务采取响应操作，3，返回数据
        // ---- 1 读取客户端请求
        // 1 提升I/O效率，2 便于助航读入-----
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            String content;
            StringBuilder request = new StringBuilder();
            while ((content = reader.readLine()) != null && !content.trim().isEmpty()){
                request.append(content).append("\n");
            }
            System.out.println("request:\n" + request);

            //---- 3 返回数据----
            byte[] response = new byte[0];
            if (mHttpCallback != null){
                response = mHttpCallback.onResponse(request.toString());
            }
            //响应头
            //1:响应行
            String responseLine = "HTTP/1.1 200 OK \r\n";
            //2：响应首部
            String responseHeader = "Content-type:" + "text/html";
            OutputStream outputStream = mSocket.getOutputStream();
            //
            outputStream.write(responseLine.getBytes());//响应行
            outputStream.write(responseHeader.getBytes());//响应头
            outputStream.write("\r\n".getBytes());//空行
            outputStream.write(response);//响应体

            mSocket.close();//只需要close 这个就可以了
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
