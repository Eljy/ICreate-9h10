package com.example.liuying.video;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class VideoMain2Activity extends AppCompatActivity {

    private CustomVideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_main2);
        initView();
    }

    /**
     * initialization
     */
    private void initView() {

        videoview = (CustomVideoView) findViewById(R.id.videoview);
        //set the path of video
        videoview.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.recherche2));
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

    public void main2next2(View view){
        Intent intent = new Intent(this, NfcWait.class);
        startActivity(intent);
    }
}
