package com.pelitabangsa.upbandroidtv.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.airbnb.lottie.LottieAnimationView;
import com.pelitabangsa.upbandroidtv.MotorFragment;
import com.pelitabangsa.upbandroidtv.R;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private TextView btnStart;
    private LottieAnimationView motorAnim;
    private VideoView bgHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initView(view);

        initVideo();

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(5000);
        fadeOut.setDuration(5000);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeOut);
        motorAnim.setAnimation(animation);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                motorAnim.setVisibility(View.GONE);
            }
        }, 5000);
    }

    private void initVideo() {
        bgHome.setVideoURI(Uri.parse("android.resource://com.pelitabangsa.upbandroidtv/" + R.raw.bg_home_final));
        bgHome.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        bgHome.start();
    }

    private void initView(View view) {
        btnStart = (TextView) view.findViewById(R.id.btn_start);
        btnStart.requestFocus();
        motorAnim = (LottieAnimationView) view.findViewById(R.id.motor_anim);

        btnStart.setOnClickListener(this);
        bgHome = (VideoView) view.findViewById(R.id.bg_home);
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        changeFragment(new MotorFragment());
    }
}
