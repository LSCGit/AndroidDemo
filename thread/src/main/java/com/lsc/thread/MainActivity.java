package com.lsc.thread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private Button mShowTip;
    private Button mStartThread;

    private static final int MSG_1 = 1;
    private static final int MSG_2 = 2;
    private static final int MSG_3 = 3;
    private static final int MSG_4 = 4;

    private Handler mHandler = new Handler(Looper.getMainLooper(),new Handler.Callback(){
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            switch (msg.what){
                case MSG_1:
                    Snackbar.make(mShowTip,"我是第二种线程",Snackbar.LENGTH_LONG).show();
                    break;
                case MSG_2:

                    break;
                case MSG_3:

                    break;
                case MSG_4:

                    break;
            }

            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShowTip = findViewById(R.id.showTip);
        mStartThread = findViewById(R.id.startThread);
        mShowTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"我显示了表示界面没死掉",
                        Snackbar.LENGTH_LONG).show();
            }
        });

        mStartThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyThread().start();
            }
        });

        //创建线程的第二种方法
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = MSG_1;
                mHandler.sendMessage(msg);
            }
        });
        thread.start();
    }

    //创建线程的第一种方法。
    class MyThread extends Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(20000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
