package com.lsc.encryption;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lsc.encryption.http.HttpRequest;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.ByteBuffer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*
* 加解密
* */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYRJR9kDonbh1Llaae85VyhzOG\n" +
            "DkOoQ0D+LcAkUhDKiguSe7z3Frdt4IpoQCWOe0GjqxpJ4ssgD25CyytLCcJ0rBlI\n" +
            "WP4muHCRQQYh2gdC3F+fSIpUNz5D93SlrFZ/HEn+nQBMb/5xJQ3uJuu73JitQDdf\n" +
            "cyouA5J1CVutRzXjewIDAQAB";

    public static final String URL = "http://192.168.1.9";

    private byte[] mAESKey;
    private DH mDh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final HttpRequest request = new HttpRequest(URL);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过判断aes密钥是否可用，来确定是否需要握手
                //如果AES密钥不可用，则直接握手
                mDh = new DH();
                int pubKey = mDh.getPublicKey();
                if (mAESKey == null || mAESKey.length <= 0){

                    Log.d(TAG,"public key is : " + mDh.getPublicKey());
                    request.handshake(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                            Log.d(TAG,"握手失败");
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                            Log.d(TAG,"握手成功");
                            byte[] pubKey = response.body().bytes();
                            ByteBuffer byteBuffer = ByteBuffer.wrap(pubKey);//将byte【】转换为int
                            mAESKey = SHA256.sha256(mDh.getSecreKey(byteBuffer.getInt()));
                            Log.d(TAG,"AES:" + mAESKey);
                        }
                    },RSA.encrypt(pubKey,PUB_KEY));
                }else {

                    request.request(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.e(TAG, "get请求失败");
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            Log.e(TAG, "请求成功");
                            byte[] responseContent = response.body().bytes();
                            AES aes = new AES(mAESKey);
                            String content = new String(aes.decrypt(responseContent));
                        }
                    });
                }
            }
        });
    }


}
