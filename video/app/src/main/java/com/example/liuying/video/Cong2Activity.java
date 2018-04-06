package com.example.liuying.video;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class Cong2Activity extends AppCompatActivity {

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cong2);
        TextView tv2 = (TextView) findViewById(R.id.tx_goodgame);
        tv2.setTextColor(Color.WHITE);
        GifImageView gifImageView1 = (GifImageView) findViewById(R.id.gif1);
        try {
            GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.bravo);
            gifImageView1.setImageDrawable(gifDrawable);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void btcong2(View view){
        Intent intent = new Intent(this, VideoLast1Activity.class);
        startActivity(intent);
    }
}
