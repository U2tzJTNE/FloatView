package com.u2tzjtne.floatview;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

/**
 * @author u2tzjtne
 */
public class ViewManager {

    private FloatMagnetView mFloatMagnetView;
    private FrameLayout mContainer;
    private FloatView.Builder mBuilder;
    private static final String TAG = "ViewManager";

    private ViewManager() {
    }

    ViewManager(FloatView.Builder builder) {
        mBuilder = builder;
        init();
    }

    private void init() {
        if (mFloatMagnetView != null) {
            return;
        }
        mFloatMagnetView = new FloatMagnetView(mBuilder.mApplication);
        mFloatMagnetView.setIconImage(mBuilder.mIcon);
        mFloatMagnetView.setLayoutParams(Utils.geLayoutParams(mBuilder.mWidth, mBuilder.mHeight, mBuilder.xOffset, mBuilder.yOffset));
        mFloatMagnetView.setViewStateListener(mBuilder.mViewStateListener);
        showFlag = mBuilder.mShow;
        mBuilder.mApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                if (needShow(activity)) {
                    addView(activity);
                }
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

    private boolean showFlag;

    private boolean needShow(Activity activity) {
        if (mBuilder.mActivities == null) {
            return true;
        }
        for (Class a : mBuilder.mActivities) {
            if (a.isInstance(activity)) {
                return showFlag;
            }
        }
        return !showFlag;
    }

    /**
     * 1.Container赋值
     * 2.如果已经包含了悬浮窗，则移除
     * 3.添加悬浮窗
     *
     * @param activity 当前Activity
     */
    private void addView(Activity activity) {
        Log.d(TAG, "current activity: " + activity.getLocalClassName());
        if (mFloatMagnetView != null) {
            if (mContainer != null && mFloatMagnetView.getParent() == mContainer) {
                mContainer.removeView(mFloatMagnetView);
            }
            mContainer = Utils.getActivityRoot(activity);
            mFloatMagnetView.setActivity(activity);
            mContainer.addView(mFloatMagnetView);
        } else {
            Log.e(TAG, "FloatMagnetView is null");
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
        if (mFloatMagnetView != null && container != null && ViewCompat.isAttachedToWindow(mFloatMagnetView)) {
            container.removeView(mFloatMagnetView);
        }
        if (mContainer == container) {
            mContainer = null;
        }
    }
}