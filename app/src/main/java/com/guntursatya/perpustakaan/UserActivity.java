package com.guntursatya.perpustakaan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.guntursatya.perpustakaan.sessions.SessionManager;

public class UserActivity extends AppCompatActivity {

    TextView buatEmail, buatUsername;
    Button blogout;

    SessionManager session;
    SharedPreferences pref;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "session";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        session = new SessionManager(this);

        buatEmail = findViewById(R.id.buatemailnya);
        buatUsername = findViewById(R.id.buatusernamenya);
        blogout = findViewById(R.id.btnlogout);

        pref = getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        String Email= pref.getString("key_email", "");
        String Username = pref.getString("key_username","");

        buatUsername.setText(Username);
        buatEmail.setText(Email);
        blogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogout(false);
                Intent intent = new Intent(UserActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}