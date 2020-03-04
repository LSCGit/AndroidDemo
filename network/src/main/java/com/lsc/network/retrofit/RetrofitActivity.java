package com.lsc.network.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;

import com.lsc.network.R;
import com.lsc.network.bean.ChatMessage;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/*
* Retrofit下载文本
* Retrofit下载图像
* Retrofit 上传文件
* */

public class RetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
    }

    private void jsonRetrofit(){
        //创建Retrofit对象，指明服务端主机地址
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/")
                //添加转化工厂，需要单独添加引用
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Retrofit 根据接口实现类并创建实例，动态代理技术
        ChatService service = retrofit.create(ChatService.class);
        //调用接口的方法，此方法的逻辑在动态代理类中实现了，
        //注意此方法并没有进行网络通信，知识创建了一个用于网路通行的对象call
        retrofit2.Call<ChatMessage> call = service.getChatMsg();
        try{
            //利用call发出网络请求，这是同步调用，它返回的是一个Response对象
            //其泛型参数必须与Call的参数一致
            retrofit2.Response<ChatMessage> response = call.execute();
            ChatMessage message = response.body();


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 下载图像
     */
    public void imageRetrofit(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //创建Retrofit对象
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://www.baidu.com")
                        .build();
                ChatService service = retrofit.create(ChatService.class);
                retrofit2.Call<ResponseBody> call = service.getImage();
                try{
                    retrofit2.Response<ResponseBody> response = call.execute();
                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    //祝线程
                    Handler handler = new Handler(getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    /**
     * 文件上传
     */
    public void uploadFile(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String msg = null;

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://www.baidu.xom")
                        .build();

                ChatService service = retrofit.create(ChatService.class);
                InputStream inputStream = null;
                try{
                    //从Raw中加载资源文件
                    inputStream = getResources().openRawResource(R.raw.raw_test);
                    //分配足够大的缓冲区
                    byte[] data = new byte[inputStream.available()];
                    inputStream.read(data);
                    //利用文件数据创建一个RequestBody, application/otcet-stream表示二进制
                    RequestBody requestFile = RequestBody.create(
                            MediaType.parse("application/otcet-stream"),data);
                    //利用RequestBody创建一个part
                    MultipartBody.Part part = MultipartBody.Part.createFormData("file",
                            "raw_test.jpg",requestFile);
                    //调用Service中的方法
                    retrofit2.Call<ResponseBody> call = service.uploadImage(part);
                    //执行网络传输
                    retrofit2.Response<ResponseBody> response = call.execute();
                    //处理返回
                    ResponseBody body = response.body();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public interface ChatService{

        //get请求，URL地址 "http://www.baidu.com/?keyfrom=dict2.index"
        @GET("/?keyfrom=dict2.index")
        Call<ChatMessage> getChatMsg();

        //下载图像
        @GET("/image/a.jpg")
        Call<ResponseBody> getImage();

        //下载图像
        @GET("2018/1022/20181022103725342.jpg")
        Call<ResponseBody> testImage();

        //文件上传
        @Multipart
        @POST("/")
        Call<ResponseBody> uploadImage(@Part MultipartBody.Part fileData);
    }
}
