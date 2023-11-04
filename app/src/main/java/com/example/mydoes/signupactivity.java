package com.example.mydoes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mydoes.utils.log_sign_Database;

public class signupactivity extends AppCompatActivity {
    Button signUpButton;
    EditText emailEditText, passwordEditText, confirmPasswordEditText;
    TextView dologin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupactivity);

        signUpButton = findViewById(R.id.signUpButton);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        dologin = findViewById(R.id.dologin);


        dologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inext;
                inext = new Intent(signupactivity.this, loginactivity.class);
                startActivity(inext);
                // Toast.makeText(loginactivity.this, "Log in Successfully", Toast.LENGTH_SHORT).show();
            }
        });


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String signusername = emailEditText.getText().toString();
                String signpassword = passwordEditText.getText().toString();
                String signconfirmpass = confirmPasswordEditText.getText().toString();
                //ACCESSING THE DATABASE

                log_sign_Database dob=new log_sign_Database(getApplicationContext(),"log_sign_data",null,1);


                if (signusername.length() == 0 || signpassword.length() == 0 || signconfirmpass.length() == 0)
                    Toast.makeText(signupactivity.this, "Please fill All details", Toast.LENGTH_SHORT).show();
                else {
                    if (signpassword.compareTo(signconfirmpass) == 0) {
                        if (isValid(signusername, signpassword)) {
                            dob.signup(signusername,signpassword); //CALLING THE DATABASE FUNCTION IF VALID
                            Toast.makeText(signupactivity.this, "Signin Successfully", Toast.LENGTH_SHORT).show();
                            Intent inext;
                            inext = new Intent(signupactivity.this, MainActivity.class);
                            startActivity(inext);
                        } else {
                            Toast.makeText(signupactivity.this, "Invalid email or password format", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(signupactivity.this, "Confirm Password didn't match", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    public static boolean isValid(String username, String password) {
        // Check email validity
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            return false;
        }

        // Check password criteria
        int f1 = 0, f2 = 0, f3 = 0;
        if (password.length() < 8) {
            return false;
        } else {
            for (int p = 0; p < password.length(); p++) {
                if (Character.isLetter(password.charAt(p)))
                    f1 = 1;
            }
            for (int r = 0; r < password.length(); r++) {
                if (Character.isDigit(password.charAt(r)))
                    f2 = 1;
            }
            for (int S = 0; S < password.length(); S++) {
                char c = password.charAt(S);
                if (c >= 33 && c <= 46 || c == 64)
                    f3 = 1;
            }
            if (f1 == 1 && f2 == 1 && f3 == 1)
                return true;
        }

        return false;

    }


}

