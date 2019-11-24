package com.u2tzjtne.floatview.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author u2tzjtne
 */
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void onViewClicked(View view) {
        Intent intent = new Intent(SecondActivity.this, FirstActivity.class);
        startActivity(intent);
        finish();
    }
}
