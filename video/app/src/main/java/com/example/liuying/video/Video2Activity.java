package com.example.liuying.video;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Video2Activity extends AppCompatActivity implements View.OnClickListener{

    private CustomVideoView videoview;
    private Button btn_next3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video2);
        initView();
    }

    /**
     * initialization
     */
    private void initView() {
        btn_next3 = (Button) findViewById(R.id.btn_next3);
        btn_next3.setOnClickListener(this);

        videoview = (CustomVideoView) findViewById(R.id.videoview);
        //set the path of video
        videoview.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.test));
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next3:
                Intent intent = new Intent(this, Sen2Activity.class);
                startActivity(intent);
                //Toast.makeText(this,"NEXT PAGE!",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
