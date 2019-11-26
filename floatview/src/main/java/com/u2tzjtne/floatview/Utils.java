package com.u2tzjtne.floatview;

import android.app.Activity;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * @author u2tzjtne
 */
public class Utils {

    /**
     * 获取Activity 根部局
     *
     * @param activity activity
     * @return 根部局
     */
    public static FrameLayout getActivityRoot(Activity activity) {
        if (activity == null) {
            return null;
        }
        try {
            return (FrameLayout) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取布局参数
     *
     * @return
     */
    public static FrameLayout.LayoutParams geLayoutParams(int width, int height, int x, int y) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.gravity = Gravity.START;
        params.setMargins(x, y, params.rightMargin, 56);
        return params;
    }

}
