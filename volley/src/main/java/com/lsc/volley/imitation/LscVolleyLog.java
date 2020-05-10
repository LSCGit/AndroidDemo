package com.lsc.volley.imitation;

import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by lsc on 2020/4/19 21:53.
 * E-Mail:2965219926@qq.com
 * 
 * 
 */
public class LscVolleyLog {

    private static String TAG = "LscVolleyLog";

    public static boolean DEBUG = Log.isLoggable(TAG, Log.VERBOSE);

    /**
     * {@link Class#getName()}使用反射，并在可能的热代码路径上调用它可能会有一些开销。
     * 为了最小化这个代价，我们在这里获取一次类名，然后使用它。
     */
    private static final String CLASS_NAME = LscVolleyLog.class.getName();

    /**
     * 为你的应用程序定制日志标签，这样其他使用Volley的应用程序就不会混淆它们
     *
     * {@code adb shell setprop log.tag.&lt;tag&gt;}
     */
    public static void setTag(String tag) {
        d("Changing log tag to %s", tag);
        TAG = tag;

        // 重新初始化调试常量
        DEBUG = Log.isLoggable(TAG, Log.VERBOSE);
    }

    public static void v(String format, Object... args) {
        if (DEBUG) {
            Log.v(TAG, buildMessage(format, args));
        }
    }

    public static void d(String format, Object... args) {
        Log.d(TAG, buildMessage(format, args));
    }

    public static void e(String format, Object... args) {
        Log.e(TAG, buildMessage(format, args));
    }

    public static void e(Throwable tr, String format, Object... args) {
        Log.e(TAG, buildMessage(format, args), tr);
    }

    public static void wtf(String format, Object... args) {
        Log.wtf(TAG, buildMessage(format, args));
    }

    public static void wtf(Throwable tr, String format, Object... args) {
        Log.wtf(TAG, buildMessage(format, args), tr);
    }

    /**
     * 格式化调用者提供的消息，并预先添加有用的信息，如调用线程ID和方法名。
     */
    private static String buildMessage(String format, Object... args) {
        String msg = (args == null) ? format : String.format(Locale.US, format, args);
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

        String caller = "<unknown>";
        // Walk up the stack looking for the first caller outside of VolleyLog.
        // It will be at least two frames up, so start there.
        for (int i = 2; i < trace.length; i++) {
            String clazz = trace[i].getClassName();
            if (!clazz.equals(LscVolleyLog.CLASS_NAME)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);

                caller = callingClass + "." + trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread().getId(), caller, msg);
    }

    /** 一个简单的事件日志，记录包含名称、线程ID和时间戳。 */
    static class MarkerLog {
        public static final boolean ENABLED = LscVolleyLog.DEBUG;

        /** 标记日志中从第一个标记到最后一个标记的最小持续时间，以保证进行日志记录。*/
        private static final long MIN_DURATION_FOR_LOGGING_MS = 0;

        private static class Marker {
            public final String name;
            public final long thread;
            public final long time;

            public Marker(String name, long thread, long time) {
                this.name = name;
                this.thread = thread;
                this.time = time;
            }
        }

        private final List<Marker> mMarkers = new ArrayList<>();
        private boolean mFinished = false;

        /** 用指定的名称将标记添加到此日志中。 */
        public synchronized void add(String name, long threadId) {
            if (mFinished) {
                throw new IllegalStateException("Marker added to finished log");
            }

            mMarkers.add(new Marker(name, threadId, SystemClock.elapsedRealtime()));
        }

        /**
         * 关闭日志，如果第一个和最后一个标记之间的时间差大于{@link #MIN_DURATION_FOR_LOGGING_MS}，则将其转储到logcat。
         *
         * @param header Header string to print above the marker log.
         */
        public synchronized void finish(String header) {
            mFinished = true;

            long duration = getTotalDuration();
            if (duration <= MIN_DURATION_FOR_LOGGING_MS) {
                return;
            }

            long prevTime = mMarkers.get(0).time;
            d("(%-4d ms) %s", duration, header);
            for (Marker marker : mMarkers) {
                long thisTime = marker.time;
                d("(+%-4d) [%2d] %s", (thisTime - prevTime), marker.thread, marker.name);
                prevTime = thisTime;
            }
        }

        @Override
        protected void finalize() throws Throwable {
            // Catch requests that have been collected (and hence end-of-lifed)
            // but had no debugging output printed for them.
            // 铺获 已经被收集到但没有调试输出的信息
            if (!mFinished) {
                finish("Request on the loose");
                e("Marker log finalized without finish() - uncaught exit point for request");
            }
        }

        /** 返回此日志中第一个事件和最后一个事件之间的时间差。 */
        private long getTotalDuration() {
            if (mMarkers.size() == 0) {
                return 0;
            }

            long first = mMarkers.get(0).time;
            long last = mMarkers.get(mMarkers.size() - 1).time;
            return last - first;
        }
    }
}
