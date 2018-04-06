package com.example.liuying.video;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NfcWait extends AppCompatActivity implements View.OnClickListener{


    private Button btn_vali;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_wait);
        initView();
    }



    /**
     * initialization
     */
    private void initView() {
        btn_vali = (Button) findViewById(R.id.btn_vali);
        btn_vali.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_vali:
                Intent intent = new Intent(this, SenActivity.class);
                startActivity(intent);
                break;
        }
    }
}
