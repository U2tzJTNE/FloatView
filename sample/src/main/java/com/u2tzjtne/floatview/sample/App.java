package com.u2tzjtne.floatview.sample;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;

import com.u2tzjtne.floatview.FloatView;
import com.u2tzjtne.floatview.Unit;
import com.u2tzjtne.floatview.ViewStateListener;

/**
 * @author u2tzjtne
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FloatView.with(this)
                .setIcon(R.drawable.ic_float_view)
                .setSize(50, 50)
                .setDefaultPosition(0, 100)
                .setEdgeMargin(5)
                .setUnit(Unit.DP)
                .isAutoEdge(true)
                .setFilter(true, SecondActivity.class)
                .setViewListener(new ViewStateListener() {
                    @Override
                    public void onClick(Activity activity) {
                        Toast.makeText(App.this, "我被点击了: "
                                + activity.getLocalClassName(), Toast.LENGTH_SHORT).show();
                    }
                }).build();
    }
}
