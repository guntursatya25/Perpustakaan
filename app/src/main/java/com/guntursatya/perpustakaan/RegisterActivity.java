package com.guntursatya.perpustakaan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.guntursatya.perpustakaan.adapters.DBHelper;
import com.guntursatya.perpustakaan.adapters.User;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextUserName;
    EditText editTextEmail;
    EditText editTextPassword;

    //Declaration TextInputLayout
    TextView textInputLayoutUserName;
    TextView textInputLayoutEmail;
    TextView textInputLayoutPassword;

    //Declaration Button
    TextView buttonRegister;

    //Declaration SqliteHelper
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new DBHelper(this);
        initTextViewLogin();
        initViews();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    if (validusername()){
                        if (validemail()) {
                            if (validpassw()) {
                    String UserName = editTextUserName.getText().toString();
                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();

                    //Check in the database is there any user associated with  this email
                    if (!dbHelper.isEmailExists(Email)) {

                        //Email does not exist now add new user to database
                        dbHelper.addUser(new User(null, UserName, Email, Password));
                        Snackbar.make(buttonRegister, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                    }else {

                        //Email exists with email input provided so show error user already exist
                        Snackbar.make(buttonRegister, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                    }

                            }
                        }
                    }
                }
            }
        });
    }

    //this method used to set Login TextView click event
    private void initTextViewLogin() {
        TextView textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //this method is used to connect XML views to its Objects
    private void initViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUserName = findViewById(R.id.editTextUserName);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputLayoutUserName = findViewById(R.id.textInputLayoutUserName);
        buttonRegister = findViewById(R.id.buttonRegister);

    }

    //This method is used to validate input given by user

    public boolean validemail(){
        boolean valid = false;

        String Email = editTextEmail.getText().toString();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            textInputLayoutEmail.setText("Please enter valid email!");
        } else {
            valid = true;
            textInputLayoutEmail.setText(null);
        }
        return valid;
    }

    public boolean validpassw(){
        boolean valid = false;
        String Password = editTextPassword.getText().toString();
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
        return  valid;

    }
    public boolean validusername(){
        boolean valid = false;
        String UserName = editTextUserName.getText().toString();
        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
            textInputLayoutUserName.setText("Please enter valid username!");
        } else {
            if (UserName.length() > 5) {
                valid = true;
                textInputLayoutUserName.setText(null);
            } else {
                valid = false;
                textInputLayoutUserName.setText("Username is to short!");
            }
        }
        return valid;
    }

    public boolean validate() {
        boolean valid = false;

        String UserName = editTextUserName.getText().toString();
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
            textInputLayoutUserName.setText("Please enter valid username!");
        } else {
            if (UserName.length() > 5) {
                valid = true;
                textInputLayoutUserName.setText(null);
            } else {
                valid = false;
                textInputLayoutUserName.setText("Username is to short!");
            }
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            textInputLayoutEmail.setText("Please enter valid email!");
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