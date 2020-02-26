package com.u2tzjtne.floatview;

import android.annotation.SuppressLint;
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
import android.widget.RelativeLayout;

/**
 * @author u2tzjtne
 */
public class MagnetView extends FrameLayout {

    private static final String TAG = "MagnetView";
    /**
     * 左右边距
     */
    private int mEdgeMargin = 13;
    private static final int TOUCH_TIME_THRESHOLD = 150;
    private long mLastTouchDownTime;
    private MoveAnimator mMoveAnimator;
    private ViewStateListener mViewStateListener;
    private int mScreenWidth;
    private int mScreenHeight;
    private float mOriginalRawX;
    private float mOriginalRawY;
    private float mOriginalX;
    private float mOriginalY;
    private ImageView mIcon;
    private boolean mAutoEdge;
    private Activity mActivity;

    public MagnetView(Context context) {
        this(context, null);
    }

    public MagnetView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MagnetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(FloatView.Builder builder) {
        setClickable(true);
        View view = inflate(builder.mApplication, R.layout.float_view, this);
        mIcon = view.findViewById(R.id.icon);
        mIcon.setImageResource(builder.mIcon);
        setAutoEdge(builder.mAutoEdge);
        mEdgeMargin = builder.mEdgeMargin;
        mViewStateListener = builder.mViewStateListener;
        if (mEdgeMargin < 0) {
            mEdgeMargin = 0;
        }
        setIconLayoutParams(builder.mWidth, builder.mHeight, builder.xOffset, builder.yOffset);
    }

    public void setAutoEdge(boolean autoEdge) {
        mAutoEdge = autoEdge;
        if (mAutoEdge) {
            mMoveAnimator = new MoveAnimator();
        }
    }

    public void setIconLayoutParams(int width, int height, int x, int y) {
        Log.d(TAG, "mScreenWidth: " + mScreenWidth);
        Log.d(TAG, "mScreenHeight: " + mScreenHeight);
        mIcon.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
        if (mAutoEdge) {
            x = mEdgeMargin;
        }
        setLayoutParams(Utils.geLayoutParams(x, y));
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    public Activity getActivity() {
        return mActivity;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event == null) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                updateSize();
                changeOriginalTouchParams(event);
                if (mAutoEdge) {
                    mMoveAnimator.stop();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                updateViewPosition(event);
                break;
            case MotionEvent.ACTION_UP:
                if (mAutoEdge) {
                    moveToEdge();
                }
                if (isOnClickEvent()) {
                    dealClickEvent();
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 回调点击事件
     */
    protected void dealClickEvent() {
        if (mViewStateListener != null) {
            mViewStateListener.onClick(getActivity());
        }
    }

    /**
     *
     * @return 是否是点击事件
     */
    protected boolean isOnClickEvent() {
        return System.currentTimeMillis() - mLastTouchDownTime < TOUCH_TIME_THRESHOLD;
    }

    private void updateViewPosition(MotionEvent event) {
        // 限制不可超出屏幕宽度
        float desX = mOriginalX + event.getRawX() - mOriginalRawX;
        if (desX < 0) {
            desX = 0;
        }
        if (desX > mScreenWidth) {
            desX = mScreenWidth;
        }
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
        float moveDistance = isNearestLeft() ? mEdgeMargin : mScreenWidth - mEdgeMargin;
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
