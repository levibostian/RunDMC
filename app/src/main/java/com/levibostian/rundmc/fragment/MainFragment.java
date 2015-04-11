package com.levibostian.rundmc.fragment;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.levibostian.rundmc.R;
import com.levibostian.rundmc.activity.MainActivity;

public class MainFragment extends Fragment implements MainActivity.HandlerListener {

    private ImageView mRunDmcButton;
    private ImageView mRunDmcGroupImageView;
    private ImageView mRunDmcGroupInvertImageView;

    private int mCurrentRunDmcImageRes = R.drawable.run_dmc;

    private MediaPlayer mMediaPlayer;

    private StartHandlerListener mListener;

    @Override
    public void postUpdate() {
        toggleRunDmcGroupImage();
    }

    public interface StartHandlerListener {
        void startHandler();
    }

    public void setStartHandlerListener(StartHandlerListener listener) {
        mListener = listener;
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mRunDmcButton = (ImageView) view.findViewById(R.id.run_dmc_button);
        mRunDmcGroupImageView = (ImageView) view.findViewById(R.id.run_dmc_group);
        mRunDmcGroupInvertImageView = (ImageView) view.findViewById(R.id.run_dmc_invert_group);

        return view;
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

    private void showImages() {
        mRunDmcGroupImageView.setVisibility(View.VISIBLE);

        mListener.startHandler();
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

    private void playMyAdidas() {
        mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.my_adidas);
        mMediaPlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();

            mMediaPlayer = null;
        }
    }
}
