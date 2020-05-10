package com.lsc.runtimepermission.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by lsc on 2020-04-17 18:41.
 * E-Mail:2965219926@qq.com
 *
 * 相机预览。
 *
 * 处理基本的生命周期方法来显示和停止预览。
 * 实现直接基于http://developer.android.com/guide/topics/media/camera.html中的文档
 * 使用弃用android.hardware。摄像头为了支持{14 < API < 21}。
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "CameraPreview";
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private Camera.CameraInfo mCameraInfo;
    private int mDisplayOrientation;

    public CameraPreview(Context context) {
        this(context, null, null, 0);
    }

    public CameraPreview(Context context, Camera camera, Camera.CameraInfo cameraInfo,
                         int displayOrientation) {
        super(context);

        // 如果没有设置相机，不能初始化
        if (camera == null || cameraInfo == null) {
            return;
        }
        mCamera = camera;
        mCameraInfo = cameraInfo;
        mDisplayOrientation = displayOrientation;

        // 设置SurfaceHolder.Callback，以便在创建和销毁底层表面时得到通知。
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    /**
     * 计算屏幕方向
     * @param info
     * @param rotation
     * @return
     */
    public static int calculatePreviewOrientation(Camera.CameraInfo info, int rotation) {
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }

        return result;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //表面已经创建，现在告诉相机在哪里画预览。
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
            Log.d(TAG, "Camera preview started.");
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        //如果您的预览可以更改或旋转，请在这里处理这些事件。
        //
        //在调整大小或重新格式化之前，请确保停止preview。
        if (mHolder.getSurface() == null) {
            // 预览表面不存在
            Log.d(TAG, "Preview surface does not exist");
            return;
        }

        // 改变前停止preview
        try {
            mCamera.stopPreview();
            Log.d(TAG, "Preview stopped.");
        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }

        int orientation = calculatePreviewOrientation(mCameraInfo, mDisplayOrientation);
        mCamera.setDisplayOrientation(orientation);

        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
            Log.d(TAG, "Camera preview started.");
        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //空。注意在你的活动中发布相机预览。
    }
}
