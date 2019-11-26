package com.u2tzjtne.floatview;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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
    public static FrameLayout.LayoutParams geLayoutParams(int x, int y) {
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(x, y, params.rightMargin, params.bottomMargin);
        return params;
    }

}
