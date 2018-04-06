package com.example.liuying.video;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Videonfc1Activity extends AppCompatActivity {


    private CustomVideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videonfc1);
        initView();
    }

    /**
     * initialization
     */
    private void initView() {

        videoview = (CustomVideoView) findViewById(R.id.videoview);
        //set the path of video
        videoview.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.instructions3));
        //play
        videoview.start();
        //Play repeatedly
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
            }
        });

    }

    public void nfc1next(View view){
        Intent intent = new Intent(this, Videonfc2Activity.class);
        startActivity(intent);
    }
}
