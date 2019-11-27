package com.u2tzjtne.floatview.sample;

import android.app.Application;
import android.widget.Toast;

import com.u2tzjtne.floatview.MagnetView;
import com.u2tzjtne.floatview.FloatView;
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
                .setWidth(200)
                .setHeight(200)
                .setX(100)
                .setY(100)
                .isAutoEdge(true)
                .setEdgeMargin(20)
                .setFilter(true, SecondActivity.class)
                .setViewListener(new ViewStateListener() {
                    @Override
                    public void onClick(MagnetView view) {
                        Toast.makeText(App.this, "我被点击了: " + view.getActivity().getLocalClassName(), Toast.LENGTH_SHORT).show();
                    }
                }).build();
    }
}
