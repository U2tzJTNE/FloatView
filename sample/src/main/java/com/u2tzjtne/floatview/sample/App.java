package com.u2tzjtne.floatview.sample;

import android.app.Application;

import com.u2tzjtne.floatview.FloatView;

/**
 * @author u2tzjtne
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FloatView
                .with(this)
                .setIcon(R.drawable.ic_float_view)
                .build();
    }
}
