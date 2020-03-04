package com.lsc.network.okhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.lsc.network.R;
import com.lsc.network.bean.ChatMessage;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/*
* GSON
* OkHttp请求图片，
* OkHttp上传文件
*
*
* */

public class OkHttpActivity extends AppCompatActivity {

    private Button mBtnRequestImg;
    private ImageView mImg1;

    private String mJsonStr = "{\"contactName\":\"路人甲\",\n" +
            "     \"time\":\"2018-06-12T13:31\",\n" +
            "     \"content\":\"我说啥了我？Get out!\"}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        initView();
    }

    private void initView(){
        mBtnRequestImg = findViewById(R.id.requestImage);
        mImg1 = findViewById(R.id.imageView1);
        mBtnRequestImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new OkHttpAsyncTask().execute("");
            }
        });
        initJson();
    }


    /**
     * Gson解析Json文件
     */
    private void initJson(){
        Gson gson = new Gson();
        ChatMessage message = gson.fromJson(mJsonStr,ChatMessage.class);
        Log.i("gson","name:" + message.getContactName());
    }

    class OkHttpAsyncTask extends AsyncTask<String,Integer,Bitmap>{
        Bitmap bitmap;

        /**
         *
         * okHttp请求图片
         * @param strings
         * @return
         */
        @Override
        protected Bitmap doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            try{
                Request request = new Request.Builder()
                        .url("http://pic3.178.com/3487/34870201/month_1607/1467705054-6082.jpg")
                        .build();
                Response response = client.newCall(request).execute();
                ResponseBody body = response.body();
                InputStream inputStream = body.byteStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

            }catch (IOException e){
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mImg1.setImageBitmap(bitmap);
        }
    }


    /**
     * 上传文件
     */
    private void uploadOneFile(){
        //创建后台线程，访问网络
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String msg = null;
                try{
                    //请求地址
                    String url = "https://www.baidu.com";
                    //创建表单 multipart form
                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    //设置类型为"multipart/form-data"
                    builder.setType(MultipartBody.FORM);
                    //设置表单内容，
                    //每次添加的数据都是一个part，
                    builder.addFormDataPart("userName","xxxx");//第一个参数part名字，

                    //从raw获取输入流对象。
                    InputStream inputStream = getResources().openRawResource(R.raw.raw_test);
                    //开足够大的内存
                    byte[] imgData = new byte[inputStream.available()];
                    //读文件数据到缓存
                    inputStream.read(imgData);
                    //创建一个part，里面放资源内容
                    RequestBody requestBody = RequestBody.create(null,imgData);
                    //part名字，文件名字，part数据
                    builder.addFormDataPart("file","raw_test.jpg",requestBody);

                    //创建包换所有part的RequestBody；
                    RequestBody body = builder.build();
                    //创建client发出请求,创建Requset，以post方式发出请求
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
