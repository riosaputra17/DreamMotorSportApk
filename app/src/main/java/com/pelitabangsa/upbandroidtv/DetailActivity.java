package com.pelitabangsa.upbandroidtv;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.pelitabangsa.upbandroidtv.adapter.SpecificationAdapter;
import com.pelitabangsa.upbandroidtv.models.motor.DetailsItem;
import com.pelitabangsa.upbandroidtv.models.motor.MotorModel;
import com.pelitabangsa.upbandroidtv.models.motor.ResultsItem;
import com.pelitabangsa.upbandroidtv.models.motor.SpesifikasiItem;
import com.pelitabangsa.upbandroidtv.utils.Constants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class DetailActivity extends FragmentActivity {

    private ImageView imgBanner;
    private TextView tvModel;
    private TextView tvDescriptionContent;
    private TextView viewMore;
    private Boolean isViewMore = true;

    private ListFragment listFragment;

    ArrayList<SpesifikasiItem> spesifikasiItems = new ArrayList<>();
    private YouTubePlayerView youtubePlayerView;
    private ImageView motorDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        initView();
    }

    private void initView() {
        imgBanner = (ImageView) findViewById(R.id.img_banner);
        tvModel = (TextView) findViewById(R.id.tv_model);
        tvDescriptionContent = (TextView) findViewById(R.id.tv_deskripsi_content);
        viewMore = (TextView) findViewById(R.id.view_more);
        youtubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);
        motorDetail = (ImageView) findViewById(R.id.motor_detail);

        getLifecycle().addObserver(youtubePlayerView);
        Bundle bundle = getIntent().getExtras();
        Constants constant = new Constants();

        youtubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
            @Override
            public void onYouTubePlayer(@NonNull YouTubePlayer youTubePlayer) {
                String linkYoutube = bundle.getString(constant.LINK_YOUTUBE);
                if (Objects.equals(linkYoutube, "")) {
                    youTubePlayer.loadVideo("iciYXaQ5BA4", 0);
                } else {
                    youTubePlayer.loadVideo(linkYoutube, 0);
                }

                Animation fadeOut = new AlphaAnimation(1, 0);
                fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
                fadeOut.setStartOffset(1000);
                fadeOut.setDuration(1000);

                AnimationSet animation = new AnimationSet(false);
                animation.addAnimation(fadeOut);
                imgBanner.setAnimation(animation);
                imgBanner.setVisibility(View.GONE);
                youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
                        super.onStateChange(youTubePlayer, state);
                        if (state == PlayerConstants.PlayerState.ENDED) {
                            youTubePlayer.seekTo(0);
                            youTubePlayer.play();
                        }
                    }
                });
            }
        });



        viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isViewMore = !isViewMore;
                if (isViewMore) {
                    tvDescriptionContent.setMaxLines(3);
                    viewMore.setText("View More Information");
                    viewMore.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_more, 0, 0, 0);
                    motorDetail.setVisibility(View.INVISIBLE);
                } else {
                    tvDescriptionContent.setMaxLines(100);
                    viewMore.setText("View Less Information");
                    viewMore.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_less, 0, 0, 0);
                    motorDetail.setVisibility(View.VISIBLE);
                }

            }
        });

        getDetails(bundle, constant);

        Gson gson = new Gson();
        InputStream i = null;
        try {
            i = getAssets().open("motor.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(i));
            MotorModel dataList = gson.fromJson(br, MotorModel.class);

            int id = getIntent().getIntExtra(constant.ID, 0);

            RecyclerView rvSpesifikasi = (RecyclerView) findViewById(R.id.rv_spesifikasi);

            bindDataDetailSpecification(dataList, id, rvSpesifikasi);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void bindDataDetailSpecification(MotorModel motorList, int id, RecyclerView rvSpesifikasi) {

        for (ResultsItem motorModel : motorList.getResults()) {

            for (DetailsItem detail : motorModel.getDetails()) {
                for (SpesifikasiItem spesifikasiItem : detail.getSpesifikasi()) {
                    if (detail.getId() == id) {
                        spesifikasiItems.add(spesifikasiItem);
                    }
                }
            }

            SpecificationAdapter adapter = new SpecificationAdapter(spesifikasiItems);
            rvSpesifikasi.setAdapter(adapter);
            rvSpesifikasi.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        }
    }

    private void getDetails(Bundle bundle, Constants constant) {

        getImgBanner(bundle, constant);
        tvModel.setText(bundle.getString(constant.TITLE));
        tvDescriptionContent.setText(bundle.getString(constant.DESCRIPTION));

        if (Objects.equals(bundle.getString(constant.MOTOR_DETAIL), "")) {
        } else {
            getMotorDetail(bundle, constant.MOTOR_DETAIL, motorDetail);
        }
    }

    private void getMotorDetail(Bundle bundle, String constant, ImageView motorDetail) {
        String url = bundle.getString(constant);
        Glide.with(this)
                .load(url)
                .into(motorDetail);
    }

    private void getImgBanner(Bundle bundle, Constants constant) {
        String url = bundle.getString(constant.BACKDROP);
        Glide.with(this)
                .load(url)
                .into(imgBanner);
    }
}