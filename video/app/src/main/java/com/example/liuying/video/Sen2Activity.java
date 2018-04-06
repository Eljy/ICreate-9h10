package com.example.liuying.video;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Sen2Activity extends AppCompatActivity {


    public static Sen2Activity instance;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        //Full scan
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(new MySurfaceView(this));

        setContentView(R.layout.activity_sen2);
    }

    public void nextBtsen2(View view){
        Intent intent = new Intent(this, Cong2Activity.class);
        startActivity(intent);
    }
}
