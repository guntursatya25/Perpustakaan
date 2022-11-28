package com.guntursatya.perpustakaan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.guntursatya.perpustakaan.adapters.DBHelper;
import com.guntursatya.perpustakaan.adapters.User;
import com.guntursatya.perpustakaan.sessions.SessionManager;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;

    TextView textInputLayoutEmail;
    TextView textInputLayoutPassword;
    TextView textViewCreateAccount;

    TextView buttonLogin;

    SharedPreferences pref;


    SessionManager session;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DBHelper(this);
        initViews();

        session = new SessionManager(this);

//        if (session.isLoggedIn()) {
//            Intent i = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(i);
//            finish();
//        }



        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {


                    String Email = editTextUsername.getText().toString();
                    String Password = editTextPassword.getText().toString();

                    User currentUser = dbHelper.Authenticate(new User(null, null, Email, Password));

                    if (currentUser != null) {
                        Snackbar.make(buttonLogin, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();

                        session.setLogin(true);

//                        session.saveSession(Email);
//                        pref.edit().putString("usename", Email).commit();

//                        String sesiemail = pref.getString("key_email", null);

                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("emailnya",Email);
                        startActivity(intent);
                        finish();

                    } else {

                        Snackbar.make(buttonLogin, "Failed to log in , please try again", Snackbar.LENGTH_LONG).show();

                    }
                }
            }
        });


    }

    private void initViews() {
        textViewCreateAccount = findViewById(R.id.tvCreate);
        editTextUsername = findViewById(R.id.etemail);
        editTextPassword =  findViewById(R.id.etpassword);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        buttonLogin =  findViewById(R.id.btnlogin);

    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String Email = editTextUsername.getText().toString();
        String Password = editTextPassword.getText().toString();

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            textInputLayoutEmail.setText("Please enter valid Email!");
        } else {
            valid = true;
            textInputLayoutEmail.setText(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            textInputLayoutPassword.setText("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                textInputLayoutPassword.setText(null);
            } else {
                valid = false;
                textInputLayoutPassword.setText("Password is to short!");
            }
        }

        return valid;
    }


}