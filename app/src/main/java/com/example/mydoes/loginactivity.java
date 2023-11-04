package com.example.mydoes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mydoes.utils.log_sign_Database;

public class loginactivity extends AppCompatActivity {
    Button loginButton;
    EditText emailEditText1,passwordEditText1;
    TextView dosignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);


        loginButton=findViewById(R.id.loginButton);
        emailEditText1=findViewById(R.id.emailEditText1);
        passwordEditText1=findViewById(R.id.passwordEditText1);
        dosignin=findViewById(R.id.dosignin);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String signusername=emailEditText1.getText().toString();
                String signpassword=passwordEditText1.getText().toString();
                log_sign_Database dob=new log_sign_Database(getApplicationContext(),"log_sign_data",null,1);

                if(signusername.length()==0||signpassword.length()==0)
                    Toast.makeText(loginactivity.this, "Please fill All details", Toast.LENGTH_SHORT).show();
                else{
                    if(dob.login(signusername,signpassword)==1) {
                        Toast.makeText(loginactivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences=getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("signusername",signusername);
                        //TO SAVE THE DATA WITH KEY AND VALUE
                        editor.apply();
                        Intent inext;
                        inext=new Intent(loginactivity.this, MainActivity.class);
                        startActivity(inext);

                    }else
                        Toast.makeText(loginactivity.this, "Invalid email or password format", Toast.LENGTH_SHORT).show();

                }
         }
        });

        dosignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inext;
                inext=new Intent(loginactivity.this, signupactivity.class);
                startActivity(inext);
            }
        });

    }
}

