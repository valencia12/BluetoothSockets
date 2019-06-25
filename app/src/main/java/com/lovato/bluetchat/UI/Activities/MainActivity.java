package com.lovato.bluetchat.UI.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewAnimator;

import com.lovato.bluetchat.R;
import com.lovato.bluetchat.UI.Fragments.ChatFragment;
import com.lovato.bluetchat.UI.Fragments.FragmentLoad;
import com.lovato.bluetchat.UI.Load.Load;
import com.lovato.bluetchat.UI.Load.LoadOneMessage;
import com.lovato.bluetchat.UI.Load.Wrapper;

public class MainActivity extends SampleActivityBase {

    public static final String TAG = "MainActivity";

    // Whether the Load Fragment is currently shown
    private boolean mLogShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ChatFragment fragment = new ChatFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_toggle_log:
                mLogShown = !mLogShown;
                ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
                if (mLogShown) {
                    output.setDisplayedChild(1);
                } else {
                    output.setDisplayedChild(0);
                }
                supportInvalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initializeLogging() {
        // Wraps Android's native log framework.
        Wrapper wrapper = new Wrapper();
        // Using Load, front-end to the logging chain, emulates android.util.log method signatures.
        Load.setLogNode(wrapper);

        // Filter strips out everything except the message text.
        LoadOneMessage msgFilter = new LoadOneMessage();
        wrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        FragmentLoad fragmentLoad = (FragmentLoad) getSupportFragmentManager()
                .findFragmentById(R.id.log_fragment);
        msgFilter.setNext(fragmentLoad.getLogView());

        Load.i(TAG, "Ready");
    }
}