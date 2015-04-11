package com.levibostian.rundmc.fragment;

import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.levibostian.rundmc.R;

public class MainFragment extends Fragment {

    private ImageView mRunDmcButton;
    private ImageView mRunDmcGroupImageView;

    private MediaPlayer mMediaPlayer;

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

        setupViews();

        return view;
    }

    private void setupViews() {
        mRunDmcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMyAdidas();

                showImages();
            }
        });
    }

    private void showImages() {
        mRunDmcGroupImageView.setVisibility(View.VISIBLE);
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
