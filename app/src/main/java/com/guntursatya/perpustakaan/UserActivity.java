package com.guntursatya.perpustakaan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guntursatya.perpustakaan.sessions.SessionManager;

public class UserActivity extends AppCompatActivity {

    TextView text;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        session = new SessionManager(this);

        text = findViewById(R.id.textLogut);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogout(false);
                Intent intent = new Intent(UserActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}