package com.lsc.appshortcuts;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by lsc on 2020-04-17 10:44.
 * E-Mail:2965219926@qq.com
 */
public class Utils {

    private Utils() {
    }

    public static void showToast(Context context, String message) {
        new Handler(Looper.getMainLooper()).post(() -> {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        });
    }
}
