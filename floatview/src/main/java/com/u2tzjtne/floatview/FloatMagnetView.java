package com.u2tzjtne.floatview;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

/**
 * @author u2tzjtne
 */
public class FloatMagnetView extends FrameLayout {

    public static final int MARGIN_EDGE = 13;
    private float mOriginalRawX;
    private float mOriginalRawY;
    private float mOriginalX;
    private float mOriginalY;
    private ViewStateListener mViewStateListener;
    private static final int TOUCH_TIME_THRESHOLD = 150;
    private long mLastTouchDownTime;
    protected MoveAnimator mMoveAnimator;
    protected int mScreenWidth;
    private int mScreenHeight;

    private ImageView mIcon;

    private static final String TAG = "FloatMagnetView";

    private Activity mActivity;

    public void setViewStateListener(ViewStateListener viewStateListener) {
        this.mViewStateListener = viewStateListener;
    }

    public FloatMagnetView(Context context) {
        this(context, null);
    }

    public FloatMagnetView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatMagnetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = inflate(context, R.layout.float_view, this);
        mIcon = view.findViewById(R.id.icon);
        mMoveAnimator = new MoveAnimator();
        setClickable(true);
    }

    public void setIconImage(@DrawableRes int resId) {
        mIcon.setImageResource(resId);
    }

    public void setActivity(Activity activity) {
        Log.d(TAG, "start setActivity: " + activity.getLocalClassName());
        this.mActivity = activity;
    }

    public Activity getActivity() {
        return mActivity;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event == null) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                updateSize();
                changeOriginalTouchParams(event);
                mMoveAnimator.stop();
                break;
            case MotionEvent.ACTION_MOVE:
                updateViewPosition(event);
                break;
            case MotionEvent.ACTION_UP:
                moveToEdge();
                if (isOnClickEvent()) {
                    dealClickEvent();
                }
                break;
            default:
                break;
        }
        return true;
    }

    protected void dealClickEvent() {
        if (mViewStateListener != null) {
            mViewStateListener.onClick(this);
        }
    }

    protected boolean isOnClickEvent() {
        return System.currentTimeMillis() - mLastTouchDownTime < TOUCH_TIME_THRESHOLD;
    }

    private void updateViewPosition(MotionEvent event) {
        float desX = mOriginalX + event.getRawX() - mOriginalRawX;
        // 限制不可超出屏幕高度
        float desY = mOriginalY + event.getRawY() - mOriginalRawY;
        if (desY < 0) {
            desY = 0;
        }
        if (desY > mScreenHeight) {
            desY = mScreenHeight;
        }
        setX(desX);
        setY(desY);
    }

    private void changeOriginalTouchParams(MotionEvent event) {
        mOriginalX = getX();
        mOriginalY = getY();
        mOriginalRawX = event.getRawX();
        mOriginalRawY = event.getRawY();
        mLastTouchDownTime = System.currentTimeMillis();
    }

    public void updateSize() {
        FrameLayout activityRoot = Utils.getActivityRoot(mActivity);
        mScreenWidth = activityRoot.getWidth() - this.getWidth();
        mScreenHeight = activityRoot.getHeight() - this.getWidth();
    }

    public void moveToEdge() {
        float moveDistance = isNearestLeft() ? MARGIN_EDGE : mScreenWidth - MARGIN_EDGE;
        mMoveAnimator.start(moveDistance, getY());
    }

    protected boolean isNearestLeft() {
        int middle = mScreenWidth / 2;
        return getX() < middle;
    }

    protected class MoveAnimator implements Runnable {

        private Handler handler = new Handler(Looper.getMainLooper());
        private float destinationX;
        private float destinationY;
        private long startingTime;

        void start(float x, float y) {
            this.destinationX = x;
            this.destinationY = y;
            startingTime = System.currentTimeMillis();
            handler.post(this);
        }

        @Override
        public void run() {
            if (getRootView() == null || getRootView().getParent() == null) {
                return;
            }
            float progress = Math.min(1, (System.currentTimeMillis() - startingTime) / 400f);
            float deltaX = (destinationX - getX()) * progress;
            float deltaY = (destinationY - getY()) * progress;
            move(deltaX, deltaY);
            if (progress < 1) {
                handler.post(this);
            }
        }

        private void stop() {
            handler.removeCallbacks(this);
        }
    }

    private void move(float deltaX, float deltaY) {
        setX(getX() + deltaX);
        setY(getY() + deltaY);
    }
}
