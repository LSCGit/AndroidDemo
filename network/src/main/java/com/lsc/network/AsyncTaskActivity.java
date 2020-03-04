package com.lsc.network;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTaskActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private ImageView mImage1, mImage2;
    private HttpAsyncTask mAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        mProgressBar = findViewById(R.id.progressBar);
        mImage1 = findViewById(R.id.imageView1);
        mImage2 = findViewById(R.id.imageView2);

        loadImage();
    }

    private void loadImage() {

        mAsyncTask = new HttpAsyncTask(2);
        mAsyncTask.execute("http://pic3.178.com/3487/34870201/month_1607/1467705054-6082.jpg",
                "http://www.wei2008.com/news/UploadPic/2016-6/20166237920838.jpg");
    }

    @Override
    protected void onDestroy() {
        if (mAsyncTask != null){
            mAsyncTask.cancel(false);
        }
        super.onDestroy();
    }

    class HttpAsyncTask extends AsyncTask<String, Integer, Bitmap[]> {

        private int taskNum = 0;

        public HttpAsyncTask(int taskNum) {
            this.taskNum = taskNum;
        }

        @Override
        protected void onPreExecute() {
            mProgressBar.setMax(taskNum);
        }

        @Override
        protected Bitmap[] doInBackground(String... strings) {
            Bitmap[] bitmaps = new Bitmap[strings.length];
            int i=0;
            for (String urlAddr : strings) {
                if (isCancelled()){
                    break;
                }
                try {
                    URL url = new URL(urlAddr);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    Log.i("task", "bitmap width = " + bitmap.getWidth() + ",/n height = " + bitmap.getHeight());
                    bitmaps[i] = bitmap;
                    i++;
                    publishProgress(i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return bitmaps;
        }

        @Override
        protected void onPostExecute(Bitmap[] bitmaps) {

            if (bitmaps == null) {
                return;
            }
            mImage1.setImageBitmap(bitmaps[0]);
            mImage2.setImageBitmap(bitmaps[1]);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressBar.setProgress(values[0].intValue());
        }
    }
}
