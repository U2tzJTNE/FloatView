package com.u2tzjtne.floatview;

import android.app.Activity;
import android.content.res.Resources;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * @author u2tzjtne
 */
class Utils {

    /**
     * 获取Activity 根部局
     *
     * @param activity activity
     * @return 根部局
     */
    static FrameLayout getActivityRoot(Activity activity) {
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
     * @return 布局参数
     */
    static FrameLayout.LayoutParams geLayoutParams(int x, int y) {
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(x, y, params.rightMargin, params.bottomMargin);
        return params;
    }

    /**
     * dp转px
     *
     * @return 转换后的数值
     */
    static int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
