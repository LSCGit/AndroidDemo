package com.lsc.runtimepermission.camera;


import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lsc.runtimepermission.R;

public class CameraPreviewFragment extends Fragment {

    private static final String TAG = "CameraPreviewFragment";

    /**
     * 0 表示第一次使用摄像头
     */
    private static final int CAMERA_ID = 0;

    private CameraPreview mPreview;
    private Camera mCamera;

    public static CameraPreviewFragment newInstance() {
        return new CameraPreviewFragment();
    }

    //获取相机对象实例的安全方法
    public static Camera getCameraInstance(int cameraId) {
        Camera c = null;
        try {
            c = Camera.open(cameraId);//尝试得到相机实例

            String str = new String();

        } catch (Exception e) {
            Log.d(TAG, "Camera " + cameraId  + e.getMessage());
        }
        return c;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //打开相机的实例并检索它的信息。
        mCamera = getCameraInstance(CAMERA_ID);
        Camera.CameraInfo cameraInfo = null;
        if (mCamera != null) {
            // 只有在相机可用时才获取相机信息
            cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(CAMERA_ID, cameraInfo);
        }

        if (mCamera == null || cameraInfo == null) {
            // 摄像头不可用，显示错误信息
            Toast.makeText(getActivity(), "Camera is not available.", Toast.LENGTH_SHORT).show();
            return inflater.inflate(R.layout.fragment_camera_unavailable, null);
        }

        View root = inflater.inflate(R.layout.fragment_camera_preview, null);

        //
        final int displayRotation = getActivity().getWindowManager().getDefaultDisplay()
                .getRotation();

        //
        mPreview = new CameraPreview(getActivity(), mCamera, cameraInfo, displayRotation);
        FrameLayout preview = (FrameLayout) root.findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        return root;
    }
}
