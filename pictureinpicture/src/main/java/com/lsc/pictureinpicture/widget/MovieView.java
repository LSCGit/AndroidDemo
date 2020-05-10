package com.lsc.pictureinpicture.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

import com.lsc.pictureinpicture.R;

import java.io.IOException;
import java.lang.ref.WeakReference;


/**
 * Created by lsc on 2020-04-17 15:48.
 * E-Mail:2965219926@qq.com
 */
public class MovieView extends RelativeLayout {

    /** 监听与Movie有关事件 */
    public abstract static class MovieListener {

        /** 在视频开始或恢复时调用 */
        public void onMovieStarted() {}

        /** 当视频暂停或结束时调用。 */
        public void onMovieStopped() {}

        /** 当视图应最小化时调用。 */
        public void onMovieMinimized() {}
    }

    private static final String TAG = "MovieView";

    /** 快进和快退时间量 */
    private static final int FAST_FORWARD_REWIND_INTERVAL = 5000; // ms

    /** 控制淡出的时间。 */
    private static final int TIMEOUT_CONTROLS = 3000; // ms

    /** 显示视频回放 */
    private final SurfaceView mSurfaceView;

    // 控制 控件
    private final ImageButton mToggle;
    private final View mShade;
    private final ImageButton mFastForward;
    private final ImageButton mFastRewind;
    private final ImageButton mMinimize;

    /** 播放视频。没有设置视频时为null。 */
    MediaPlayer mMediaPlayer;

    /** 视频资源ID */
    @RawRes
    private int mVideoResourceId;

    /** 视频title */
    private String mTitle;

    /** 剩余区域 调整视图边界或者用黑色条填充 */
    private boolean mAdjustViewBounds;

    /** 处理控制控件的超时. */
    TimeoutHandler mTimeoutHandler;

    /** 所有事件的侦听器*/
    MovieListener mMovieListener;

    /** 当前播放位置*/
    private int mSavedCurrentPosition;



    public MovieView(Context context) {
        this(context, null);
    }

    public MovieView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //设置背景色
        setBackgroundColor(Color.BLACK);

        // 填充控件
        inflate(context, R.layout.view_movie, this);
        mSurfaceView = findViewById(R.id.surface);
        mShade = findViewById(R.id.shade);
        mToggle = findViewById(R.id.toggle);
        mFastForward = findViewById(R.id.fast_forward);
        mFastRewind = findViewById(R.id.fast_rewind);
        mMinimize = findViewById(R.id.minimize);

        //读取xml中设置的属性
        final TypedArray attributes =
                context.obtainStyledAttributes(
                        attrs,
                        R.styleable.MovieView,
                        defStyleAttr,
                        R.style.Widget_PictureInPicture_MovieView);

        setVideoResourceId(attributes.getResourceId(R.styleable.MovieView_android_src, 0));
        setAdjustViewBounds(
                attributes.getBoolean(R.styleable.MovieView_android_adjustViewBounds, false));
        setTitle(attributes.getString(R.styleable.MovieView_android_title));
        //属性释放
        attributes.recycle();

        // Bind view events
        final OnClickListener listener =
                new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.surface:
                                toggleControls();
                                break;
                            case R.id.toggle:
                                toggle();
                                break;
                            case R.id.fast_forward:
                                fastForward();
                                break;
                            case R.id.fast_rewind:
                                fastRewind();
                                break;
                            case R.id.minimize:
                                if (mMovieListener != null) {
                                    mMovieListener.onMovieMinimized();
                                }
                                break;
                        }
                        // 启动或重置超时 隐藏控件
                        if (mMediaPlayer != null) {
                            if (mTimeoutHandler == null) {
                                mTimeoutHandler = new TimeoutHandler(MovieView.this);
                            }
                            mTimeoutHandler.removeMessages(TimeoutHandler.MESSAGE_HIDE_CONTROLS);
                            if (mMediaPlayer.isPlaying()) {
                                mTimeoutHandler.sendEmptyMessageDelayed(
                                        TimeoutHandler.MESSAGE_HIDE_CONTROLS, TIMEOUT_CONTROLS);
                            }
                        }
                    }
                };

        mSurfaceView.setOnClickListener(listener);
        mToggle.setOnClickListener(listener);
        mFastForward.setOnClickListener(listener);
        mFastRewind.setOnClickListener(listener);
        mMinimize.setOnClickListener(listener);

        // 初始化 视频回放
        mSurfaceView
                .getHolder()
                .addCallback(
                        new SurfaceHolder.Callback() {
                            @Override
                            public void surfaceCreated(SurfaceHolder holder) {
                                openVideo(holder.getSurface());
                            }

                            @Override
                            public void surfaceChanged(
                                    SurfaceHolder holder, int format, int width, int height) {
                                // Do nothing
                            }

                            @Override
                            public void surfaceDestroyed(SurfaceHolder holder) {
                                if (mMediaPlayer != null) {
                                    mSavedCurrentPosition = mMediaPlayer.getCurrentPosition();
                                }
                                closeVideo();
                            }
                        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMediaPlayer != null) {
            final int videoWidth = mMediaPlayer.getVideoWidth();
            final int videoHeight = mMediaPlayer.getVideoHeight();
            if (videoWidth != 0 && videoHeight != 0) {
                final float aspectRatio = (float) videoHeight / videoWidth;
                final int width = MeasureSpec.getSize(widthMeasureSpec);
                final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
                final int height = MeasureSpec.getSize(heightMeasureSpec);
                final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
                if (mAdjustViewBounds) {
                    if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
                        super.onMeasure(
                                widthMeasureSpec,
                                MeasureSpec.makeMeasureSpec(
                                        (int) (width * aspectRatio), MeasureSpec.EXACTLY));
                    } else if (widthMode != MeasureSpec.EXACTLY
                            && heightMode == MeasureSpec.EXACTLY) {
                        super.onMeasure(
                                MeasureSpec.makeMeasureSpec(
                                        (int) (height / aspectRatio), MeasureSpec.EXACTLY),
                                heightMeasureSpec);
                    } else {
                        super.onMeasure(
                                widthMeasureSpec,
                                MeasureSpec.makeMeasureSpec(
                                        (int) (width * aspectRatio), MeasureSpec.EXACTLY));
                    }
                } else {
                    final float viewRatio = (float) height / width;
                    if (aspectRatio > viewRatio) {
                        int padding = (int) ((width - height / aspectRatio) / 2);
                        setPadding(padding, 0, padding, 0);
                    } else {
                        int padding = (int) ((height - width * aspectRatio) / 2);
                        setPadding(0, padding, 0, padding);
                    }
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                }
                return;
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mTimeoutHandler != null) {
            mTimeoutHandler.removeMessages(TimeoutHandler.MESSAGE_HIDE_CONTROLS);
            mTimeoutHandler = null;
        }
        super.onDetachedFromWindow();
    }

    /**
     * 设置监听器监听视频行为
     */
    public void setMovieListener(@Nullable MovieListener movieListener) {
        mMovieListener = movieListener;
    }

    /**
     * 设置视频title
     *
     */
    public void setTitle(String title) {
        this.mTitle = title;
    }

    /**
     * 得到当前播放视频的title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * 得到当前播放视频的资源ID
     */
    public int getVideoResourceId() {
        return mVideoResourceId;
    }

    /**
     * 得到当前播放进度
     *
     */
    public int getCurrentPosition() {
        if (mMediaPlayer == null) {
            return 0;
        }
        return mMediaPlayer.getCurrentPosition();
    }

    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    public void setAdjustViewBounds(boolean adjustViewBounds) {
        if (mAdjustViewBounds == adjustViewBounds) {
            return;
        }
        mAdjustViewBounds = adjustViewBounds;
        if (adjustViewBounds) {
            setBackground(null);
        } else {
            setBackgroundColor(Color.BLACK);
        }
        requestLayout();
    }

    public void play() {
        if (mMediaPlayer == null) {
            return;
        }
        mMediaPlayer.start();
        adjustToggleState();
        setKeepScreenOn(true);
        if (mMovieListener != null) {
            mMovieListener.onMovieStarted();
        }
    }

    public void pause() {
        if (mMediaPlayer == null) {
            adjustToggleState();
            return;
        }
        mMediaPlayer.pause();
        adjustToggleState();
        setKeepScreenOn(false);
        if (mMovieListener != null) {
            mMovieListener.onMovieStopped();
        }
    }

    /**
     * 设置播放视频的资源ID。
     */
    public void setVideoResourceId(@RawRes int id) {
        if (id == mVideoResourceId) {
            return;
        }
        mVideoResourceId = id;
        Surface surface = mSurfaceView.getHolder().getSurface();
        if (surface != null && surface.isValid()) {
            closeVideo();
            openVideo(surface);
        }
    }

    void openVideo(Surface surface) {
        if (mVideoResourceId == 0) {
            return;
        }
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setSurface(surface);
        startVideo();
    }

    /** 开始播放视频 */
    @TargetApi(24)
    public void startVideo() {
        mMediaPlayer.reset();
        try (AssetFileDescriptor fd = getResources().openRawResourceFd(mVideoResourceId)) {
            //todo
            String videoResourcePath = getResources().getResourceName(mVideoResourceId).toString();
            mMediaPlayer.setDataSource(fd);
            mMediaPlayer.setOnPreparedListener(
                    new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            // Adjust the aspect ratio of this view
                            requestLayout();
                            if (mSavedCurrentPosition > 0) {
                                mediaPlayer.seekTo(mSavedCurrentPosition);
                                mSavedCurrentPosition = 0;
                            } else {
                                // Start automatically
                                play();
                            }
                        }
                    });
            mMediaPlayer.setOnCompletionListener(
                    new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            adjustToggleState();
                            setKeepScreenOn(false);
                            if (mMovieListener != null) {
                                mMovieListener.onMovieStopped();
                            }
                        }
                    });
            mMediaPlayer.prepare();
        } catch (IOException e) {
            Log.e(TAG, "Failed to open video", e);
        }
    }

    void closeVideo() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    /** 显示所有控制控件 */
    public void showControls() {
        TransitionManager.beginDelayedTransition(this);
        mShade.setVisibility(View.VISIBLE);
        mToggle.setVisibility(View.VISIBLE);
        mFastForward.setVisibility(View.VISIBLE);
        mFastRewind.setVisibility(View.VISIBLE);
        mMinimize.setVisibility(View.VISIBLE);
    }

    /** 隐藏所有控制控件 */
    public void hideControls() {
        TransitionManager.beginDelayedTransition(this);
        mShade.setVisibility(View.INVISIBLE);
        mToggle.setVisibility(View.INVISIBLE);
        mFastForward.setVisibility(View.INVISIBLE);
        mFastRewind.setVisibility(View.INVISIBLE);
        mMinimize.setVisibility(View.INVISIBLE);
    }

    /** 快进 */
    public void fastForward() {
        if (mMediaPlayer == null) {
            return;
        }
        mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition() + FAST_FORWARD_REWIND_INTERVAL);
    }

    /** 快退 */
    public void fastRewind() {
        if (mMediaPlayer == null) {
            return;
        }
        mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition() - FAST_FORWARD_REWIND_INTERVAL);
    }


    void toggle() {
        if (mMediaPlayer == null) {
            return;
        }
        if (mMediaPlayer.isPlaying()) {
            pause();
        } else {
            play();
        }
    }

    void toggleControls() {
        if (mShade.getVisibility() == View.VISIBLE) {
            hideControls();
        } else {
            showControls();
        }
    }

    void adjustToggleState() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mToggle.setContentDescription(getResources().getString(R.string.pause));
            mToggle.setImageResource(R.drawable.ic_pause_64dp);
        } else {
            mToggle.setContentDescription(getResources().getString(R.string.play));
            mToggle.setImageResource(R.drawable.ic_play_arrow_64dp);
        }
    }

    private static class TimeoutHandler extends Handler {

        static final int MESSAGE_HIDE_CONTROLS = 1;

        private final WeakReference<MovieView> mMovieViewRef;

        TimeoutHandler(MovieView view) {
            mMovieViewRef = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_HIDE_CONTROLS:
                    MovieView movieView = mMovieViewRef.get();
                    if (movieView != null) {
                        movieView.hideControls();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
