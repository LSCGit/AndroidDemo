package com.lsc.network;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.lsc.network.okhttp.OkHttpActivity;
import com.lsc.network.rxjava.RxJavaActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private Button mBtnRequest,mAsyncTask,mOkHttp,mRxJava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        mWebView = findViewById(R.id.webView);
        mBtnRequest = findViewById(R.id.request);
        mAsyncTask = findViewById(R.id.asyncTask);
        mOkHttp = findViewById(R.id.okhttp);
        mRxJava = findViewById(R.id.rxjava);

        mBtnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        testGetHtml();
                    }
                }).start();
            }
        });
        mAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AsyncTaskActivity.class));
            }
        });
        mOkHttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OkHttpActivity.class));
            }
        });

        mRxJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RxJavaActivity.class));
            }
        });
    }

    private void testGetHtml(){
        try{
            //URL url = new URL(mEtUrl.getText().toString());
            URL url = new URL("https://cn.bing.com");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            //进行连接，这一步可能非常耗时
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            //开缓冲区，存放数据
            byte[] buffer = new byte[4096];
            StringBuffer stringBuffer = new StringBuffer();
            int ret = inputStream.read(buffer);
            //循环，每次读出不超过4096字节，
            while (ret >= 0){
                if (ret > 0){
                    //因为服务端翻来是HTML文本，所以转换成字符串
                    String html = new String(buffer,0,ret);
                    //日志输出
                    Log.i("html",html);
                    stringBuffer.append(html);
                    ret = inputStream.read(buffer);
                }
            }
            final String allHtml = stringBuffer.toString();
            Handler handler = new Handler(MainActivity.this.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadData(allHtml,"text/html","utf8");
                }
            });
        }catch (IOException e){}
    }


}
