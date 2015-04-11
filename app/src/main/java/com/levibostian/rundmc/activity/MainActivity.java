package com.levibostian.rundmc.activity;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.levibostian.rundmc.R;
import com.levibostian.rundmc.fragment.MainFragment;


public class MainActivity extends ActionBarActivity {

    private static final int RUN_DMC_GROUP_TOGGLE_INTERVAL = 100;

    private Handler mHandler;
    private Runnable mUpdater;
    private HandlerListener mListener;

    public interface HandlerListener {
        void postUpdate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler();
        setContentView(R.layout.activity_main);

        mUpdater = new Runnable() {
            @Override
            public void run() {
                mListener.postUpdate();
                mHandler.postDelayed(mUpdater, RUN_DMC_GROUP_TOGGLE_INTERVAL);
            }
        };

        setupFragment();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mHandler.removeCallbacks(mUpdater);
    }

    private void setupFragment() {
        MainFragment fragment = MainFragment.newInstance();
        mListener = fragment;

        fragment.setStartHandlerListener(new MainFragment.StartHandlerListener() {
            @Override
            public void startHandler() {
                mHandler.post(mUpdater);
            }
        });

        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
