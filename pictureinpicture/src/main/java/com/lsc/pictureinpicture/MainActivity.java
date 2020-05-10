package com.lsc.pictureinpicture;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.app.RemoteAction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.util.Rational;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.lsc.pictureinpicture.widget.MovieView;

import java.util.ArrayList;

//PBP（画外画）是多个画面拼接在一起，而PIP（画中画）则是一些画面缩小后显示在一些画面之上。
public class MainActivity extends AppCompatActivity {

    /** 在画中画模式中对视频控制*/
    private static final String ACTION_MEDIA_CONTROL = "media_control";

    /** 画中画模式对视频进行额外的控制 */
    private static final String EXTRA_CONTROL_TYPE = "control_type";

    /** 请求播放 */
    private static final int REQUEST_PLAY = 1;

    /** 请求暂停 */
    private static final int REQUEST_PAUSE = 2;

    //todo 注释
    /**The request code for info action PendingIntent. */
    private static final int REQUEST_INFO = 3;

    /** The intent extra value for play action. */
    private static final int CONTROL_TYPE_PLAY = 1;

    /** The intent extra value for pause action. */
    private static final int CONTROL_TYPE_PAUSE = 2;

    /** 在画中画模式中使用到的参数 */
    @TargetApi(26)
    private final PictureInPictureParams.Builder mPictureInPictureParamsBuilder =
            new PictureInPictureParams.Builder();

    /** 用来播放视频的控件. */
    private MovieView mMovieView;

    /** 屏幕的下半部分，当时横屏时隐藏 */
    private ScrollView mScrollView;

    /** 广播，用来接收画中画模式的行为。*/
    private BroadcastReceiver mReceiver;

    private String mPlay;
    private String mPause;

    private final View.OnClickListener mOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.pip:
                            minimize();
                            break;
                    }
                }
            };

    /** MovieView监听 */
    private MovieView.MovieListener mMovieListener =
            new MovieView.MovieListener() {

                @Override
                public void onMovieStarted() {
                    // 我们现在正在播放视频。在PiP模式下，我们希望显示一个动作项来暂停视频。
                    updatePictureInPictureActions(
                            R.drawable.ic_pause_24dp, mPause, CONTROL_TYPE_PAUSE, REQUEST_PAUSE);
                }

                @Override
                public void onMovieStopped() {
                    // 视频停止或结束。在PiP模式下，我们希望显示一个动作项来播放视频。
                    updatePictureInPictureActions(
                            R.drawable.ic_play_arrow_24dp, mPlay, CONTROL_TYPE_PLAY, REQUEST_PLAY);
                }

                @Override
                public void onMovieMinimized() {
                    // MovieView想让我们最小化它。我们现在进入图片-图片模式。
                    minimize();
                }
            };

    /**
     * 在Pip模式中更改控制按钮
     *
     * @param iconId The icon to be used.
     * @param title The title text.
     * @param controlType The type of the action. either {@link #CONTROL_TYPE_PLAY} or {@link
     *     #CONTROL_TYPE_PAUSE}.
     * @param requestCode The request code for the {@link PendingIntent}.
     */
    @TargetApi(26)
    void updatePictureInPictureActions(
            @DrawableRes int iconId, String title, int controlType, int requestCode) {
        final ArrayList<RemoteAction> actions = new ArrayList<>();

        // This is the PendingIntent that is invoked when a user clicks on the action item.
        // You need to use distinct request codes for play and pause, or the PendingIntent won't
        // be properly updated.
        final PendingIntent intent =
                PendingIntent.getBroadcast(
                        MainActivity.this,
                        requestCode,
                        new Intent(ACTION_MEDIA_CONTROL).putExtra(EXTRA_CONTROL_TYPE, controlType),
                        0);
        final Icon icon = Icon.createWithResource(MainActivity.this, iconId);
        actions.add(new RemoteAction(icon, title, title, intent));

        // Another action item. This is a fixed action.
        actions.add(
                new RemoteAction(
                        Icon.createWithResource(MainActivity.this, R.drawable.ic_info_24dp),
                        getString(R.string.info),
                        getString(R.string.info_description),
                        PendingIntent.getActivity(
                                MainActivity.this,
                                REQUEST_INFO,
                                new Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(getString(R.string.info_uri))),
                                0)));

        mPictureInPictureParamsBuilder.setActions(actions);

        // This is how you can update action items (or aspect ratio) for Picture-in-Picture mode.
        // Note this call can happen even when the app is not in PiP mode. In that case, the
        // arguments will be used for at the next call of #enterPictureInPictureMode.
        setPictureInPictureParams(mPictureInPictureParamsBuilder.build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Prepare string resources for Picture-in-Picture actions.
        mPlay = getString(R.string.play);
        mPause = getString(R.string.pause);

        // View references
        mMovieView = findViewById(R.id.movie);
        mScrollView = findViewById(R.id.scroll);

        Button switchExampleButton = findViewById(R.id.switch_example);
        switchExampleButton.setText(getString(R.string.switch_media_session));
        switchExampleButton.setOnClickListener(new SwitchActivityOnClick());

        // Set up the video; it automatically starts.
        mMovieView.setMovieListener(mMovieListener);
        findViewById(R.id.pip).setOnClickListener(mOnClickListener);
    }

    @Override
    protected void onStop() {
        // On entering Picture-in-Picture mode, onPause is called, but not onStop.
        // For this reason, this is the place where we should pause the video playback.
        mMovieView.pause();
        super.onStop();
    }

    @TargetApi(26)
    @Override
    protected void onRestart() {
        super.onRestart();
        if (!isInPictureInPictureMode()) {
            // Show the video controls so the video can be easily resumed.
            mMovieView.showControls();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adjustFullScreen(newConfig);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            adjustFullScreen(getResources().getConfiguration());
        }
    }

    @Override
    public void onPictureInPictureModeChanged(
            boolean isInPictureInPictureMode, Configuration configuration) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, configuration);
        if (isInPictureInPictureMode) {
            // Starts receiving events from action items in PiP mode.
            mReceiver =
                    new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            if (intent == null
                                    || !ACTION_MEDIA_CONTROL.equals(intent.getAction())) {
                                return;
                            }

                            // This is where we are called back from Picture-in-Picture action
                            // items.
                            final int controlType = intent.getIntExtra(EXTRA_CONTROL_TYPE, 0);
                            switch (controlType) {
                                case CONTROL_TYPE_PLAY:
                                    mMovieView.play();
                                    break;
                                case CONTROL_TYPE_PAUSE:
                                    mMovieView.pause();
                                    break;
                            }
                        }
                    };
            registerReceiver(mReceiver, new IntentFilter(ACTION_MEDIA_CONTROL));
        } else {
            // We are out of PiP mode. We can stop receiving events from it.
            unregisterReceiver(mReceiver);
            mReceiver = null;
            // Show the video controls if the video is not playing
            if (mMovieView != null && !mMovieView.isPlaying()) {
                mMovieView.showControls();
            }
        }
    }


    /** 进入画中画模式 */

    @TargetApi(26)
    void minimize() {
        if (mMovieView == null) {
            return;
        }
        // 画中画模式中隐藏控制控件
        mMovieView.hideControls();
        // 计算Pip屏幕的长宽比
        Rational aspectRatio = new Rational(mMovieView.getWidth(), mMovieView.getHeight());
        mPictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
        enterPictureInPictureMode(mPictureInPictureParamsBuilder.build());
    }
    /**
     * Adjusts immersive full-screen flags depending on the screen orientation.
     *
     * @param config The current {@link Configuration}.
     */
    private void adjustFullScreen(Configuration config) {
        final View decorView = getWindow().getDecorView();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            mScrollView.setVisibility(View.GONE);
            mMovieView.setAdjustViewBounds(false);
        } else {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            mScrollView.setVisibility(View.VISIBLE);
            mMovieView.setAdjustViewBounds(true);
        }
    }

    /** Launches {@link MediaSessionPlaybackActivity} and closes this activity. */
    private class SwitchActivityOnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(view.getContext(), MediaSessionPlaybackActivity.class));
            finish();
        }
    }
}