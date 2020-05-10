package com.lsc.volley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initVolley(){
        final TextView textView = (TextView) findViewById(R.id.text);
        // 实例化 RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.baidu.com";

        // 请求URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 显示响应字符串的前500个字符
                        textView.setText("响应 : "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("没有工作");
            }
        });

        // 将请求加入到 RequestQueue.
        queue.add(stringRequest);
    }

    private void initVolleyRequestQueue(){
        RequestQueue requestQueue;
        //初始化缓存
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB 容量
        // 将网络设置为使用HttpURLConnection作为HTTP客户机。
        Network network = new BasicNetwork(new HurlStack());
        // Instantiate the RequestQueue with the cache and network.
        //实例化 RequestQueue 缓存和网络
        requestQueue = new RequestQueue(cache, network);
        // Start the queue
        //启动队列
        requestQueue.start();
        String url ="http://www.baidu.com";
        // Formulate the request and handle the response.
        //创建请求和处理响应
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 处理响应
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 处理错误
                    }
                });
        // Add the request to the RequestQueue.
        // 添加请求进入队列
        requestQueue.add(stringRequest);

        // ...
    }

    private void initSingleton(){
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();


    }

    private void initJson(){
        String url = "http://my-json-feed";
        final TextView textView = (TextView) findViewById(R.id.text);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        textView.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}

