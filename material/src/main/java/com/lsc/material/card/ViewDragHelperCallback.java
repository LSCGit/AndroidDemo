package com.lsc.material.card;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;

import com.google.android.material.card.MaterialCardView;

/**
 * Created by lsc on 2020-04-05 15:58.
 * E-Mail:2965219926@qq.com
 */
public class ViewDragHelperCallback extends ViewDragHelper.Callback{

    @Override
    public boolean tryCaptureView(@NonNull View child, int pointerId) {
        return false;
    }

    @Override
    public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
        super.onViewCaptured(capturedChild, activePointerId);
        if (capturedChild instanceof MaterialCardView) {

        }
    }

    @Override
    public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
        super.onViewReleased(releasedChild, xvel, yvel);
    }
}
