package com.lovato.bluetchat.UI.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.lovato.bluetchat.UI.Load.Load;
import com.lovato.bluetchat.UI.Load.Wrapper;

public class SampleActivityBase extends FragmentActivity {

    public static final String TAG = "SampleActivityBase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected  void onStart() {
        super.onStart();
        initializeLogging();
    }

    public void initializeLogging() {
        // Using Load, front-end to the logging chain, emulates android.util.log method signatures.
        // Wraps Android's native log framework
        Wrapper wrapper = new Wrapper();
        Load.setLogNode(wrapper);

        Load.i(TAG, "Ready");
    }
}