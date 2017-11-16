package com.smart.bean.rulerview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class WatchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);
        Util.setStatusBarColor(this,Util.getColor(R.color.cyan));
    }

    public void skip(View view) {

    }
}
