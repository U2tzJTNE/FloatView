package com.u2tzjtne.floatview;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

/**
 * @author u2tzjtne
 */
public class ViewManager {

    private FloatMagnetView mEnFloatingView;
    private FrameLayout mContainer;
    private FloatView.Builder mBuilder;

    private ViewManager() {
    }

    ViewManager(FloatView.Builder builder) {
        mBuilder = builder;
        init();
    }

    private void init() {
        if (mEnFloatingView != null) {
            return;
        }
        mEnFloatingView = new FloatMagnetView(mBuilder.mApplication);
        mEnFloatingView.setIconImage(mBuilder.mIcon);
        mEnFloatingView.setLayoutParams(Utils.geLayoutParams());
        mEnFloatingView.setViewStateListener(mBuilder.mViewStateListener);

        mBuilder.mApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                addView(activity);
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                removeView(activity);
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    /**
     * 1.Container赋值
     * 2.如果已经包含了悬浮窗，则移除
     * 3.添加悬浮窗
     *
     * @param activity 当前Activity
     */
    private void addView(Activity activity) {
        FrameLayout container = Utils.getActivityRoot(activity);
        if (container == null || mEnFloatingView == null) {
            mContainer = container;
        }
        if (mEnFloatingView != null && mContainer != null && mEnFloatingView.getParent() == mContainer) {
            mContainer.removeView(mEnFloatingView);
        }
        mContainer = container;
        if (container != null) {
            container.addView(mEnFloatingView);
        }
    }

    /**
     * 1.移除悬浮按钮
     * 2.将container设为null
     *
     * @param activity 当前Activity
     */
    private void removeView(Activity activity) {
        FrameLayout container = Utils.getActivityRoot(activity);
        if (mEnFloatingView != null && container != null && ViewCompat.isAttachedToWindow(mEnFloatingView)) {
            container.removeView(mEnFloatingView);
        }
        if (mContainer == container) {
            mContainer = null;
        }
    }
}