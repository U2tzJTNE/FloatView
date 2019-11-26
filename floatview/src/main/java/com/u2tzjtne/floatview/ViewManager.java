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

    private MagnetView mMagnetView;
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
        if (mMagnetView != null) {
            return;
        }
        mMagnetView = new MagnetView(mBuilder.mApplication);
        mMagnetView.setIconAutoEdge(mBuilder.mAutoEdge);
        mMagnetView.setIconImage(mBuilder.mIcon);
        mMagnetView.setIconLayoutParams(mBuilder.mWidth, mBuilder.mHeight, mBuilder.xOffset, mBuilder.yOffset);
        mMagnetView.setViewStateListener(mBuilder.mViewStateListener);
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
        if (mMagnetView != null) {
            if (mContainer != null && mMagnetView.getParent() == mContainer) {
                mContainer.removeView(mMagnetView);
            }
            mContainer = Utils.getActivityRoot(activity);
            mMagnetView.setActivity(activity);
            mContainer.addView(mMagnetView);
        } else {
            Log.e(TAG, "MagnetView is null");
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
        if (mMagnetView != null && container != null && ViewCompat.isAttachedToWindow(mMagnetView)) {
            container.removeView(mMagnetView);
        }
        if (mContainer == container) {
            mContainer = null;
        }
    }
}