package com.u2tzjtne.floatview;

import android.app.Application;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

/**
 * @author u2tzjtne
 */
public class FloatView {


    private static Builder mBuilder = null;

    @MainThread
    public static Builder with(@NonNull Application application) {
        return mBuilder = new Builder(application);
    }

    public static class Builder {
        Application mApplication;
        int mIcon;
        int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        int xOffset;
        int yOffset;
        int mEdgeMargin = 13;
        boolean mShow = true;
        boolean mAutoEdge = true;
        Class[] mActivities;
        ViewStateListener mViewStateListener;

        private Builder() {
        }

        Builder(Application application) {
            mApplication = application;
        }

        public Builder setIcon(@DrawableRes int resId) {
            mIcon = resId;
            return this;
        }

        public Builder setWidth(int width) {
            mWidth = width;
            return this;
        }

        public Builder setHeight(int height) {
            mHeight = height;
            return this;
        }

        public Builder setX(int x) {
            xOffset = x;
            return this;
        }

        public Builder setY(int y) {
            yOffset = y;
            return this;
        }

        public Builder setEdgeMargin(int edgeMargin) {
            mEdgeMargin = edgeMargin;
            return this;
        }

        public Builder isAutoEdge(boolean autoEdge) {
            mAutoEdge = autoEdge;
            return this;
        }

        public Builder setViewListener(ViewStateListener listener) {
            mViewStateListener = listener;
            return this;
        }

        /**
         * 设置 Activity 过滤器，用于指定在哪些界面显示悬浮窗，默认全部界面都显示
         *
         * @param show       　过滤类型
         * @param activities 　过滤界面
         */
        public Builder setFilter(boolean show, @NonNull Class... activities) {
            mShow = show;
            mActivities = activities;
            return this;
        }

        public void build() {
            new ViewManager(this);
        }
    }
}