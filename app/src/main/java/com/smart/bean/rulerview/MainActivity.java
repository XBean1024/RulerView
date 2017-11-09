package com.smart.bean.rulerview;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements RulerView.OnRulerValueChangedListener {
    private RulerView mRulerView;
    private TextView mRulerValue;
    public final String TAG = this.getClass().getSimpleName();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRulerView = findViewById(R.id.ruler_view);
        mRulerValue = findViewById(R.id.ruler_value);
        mRulerView.setOnValueChangedListener(this);
    }

    @Override
    public void sendValue(String value) {
        Log.i(TAG, "sendValue: "+value);
        mRulerValue.setText("身高 ："+value);
    }

}
