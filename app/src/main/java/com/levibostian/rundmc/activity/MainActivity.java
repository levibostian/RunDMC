package com.levibostian.rundmc.activity;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.levibostian.rundmc.R;

public class MainActivity extends ActionBarActivity {

    private static final int RUN_DMC_GROUP_TOGGLE_INTERVAL = 100;

    private ImageView mRunDmcButton;
    private ImageView mRunDmcGroupImageView;

    private int mCurrentRunDmcImageRes = R.drawable.run_dmc;

    private MediaPlayer mMediaPlayer;

    private Handler mHandler;
    private Runnable mRunDmcGroupRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRunDmcButton = (ImageView) findViewById(R.id.run_dmc_button);
        mRunDmcGroupImageView = (ImageView) findViewById(R.id.run_dmc_group);

        mHandler = new Handler();
        mRunDmcGroupRunnable = new Runnable() {
            @Override
            public void run() {
                toggleRunDmcGroupImage();
                mHandler.postDelayed(mRunDmcGroupRunnable, RUN_DMC_GROUP_TOGGLE_INTERVAL);
            }
        };

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void playMyAdidas() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.my_adidas);
        mMediaPlayer.start();
    }

    private void showImages() {
        mRunDmcGroupImageView.setVisibility(View.VISIBLE);

        mHandler.post(mRunDmcGroupRunnable);
    }

    private void toggleRunDmcGroupImage() {
        Log.d("whatever", "toggled");

        if (mCurrentRunDmcImageRes == R.drawable.run_dmc_invert) {
            mCurrentRunDmcImageRes = R.drawable.run_dmc;
        } else {
            mCurrentRunDmcImageRes = R.drawable.run_dmc_invert;
        }

        mRunDmcGroupImageView.setImageResource(mCurrentRunDmcImageRes);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mHandler.removeCallbacks(mRunDmcGroupRunnable);

        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();

            mMediaPlayer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        setupViews();
    }

    private void setupViews() {
        mRunDmcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMyAdidas();

                showImages();

                mRunDmcButton.setVisibility(View.GONE);
            }
        });
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
