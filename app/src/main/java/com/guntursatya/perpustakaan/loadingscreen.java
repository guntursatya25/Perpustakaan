package com.guntursatya.perpustakaan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

public class loadingscreen extends AppCompatActivity {
    private int waktu_loading=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent home=new Intent(loadingscreen.this,LoginActivity.class);
                startActivity(home);
                finish();

            }
        },waktu_loading);

    }
}