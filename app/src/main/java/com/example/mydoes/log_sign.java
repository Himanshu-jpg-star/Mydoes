package com.example.mydoes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class log_sign extends AppCompatActivity {
    Button sign, log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_sign);

        sign = findViewById(R.id.sign);
        log = findViewById(R.id.log);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inext;
                inext = new Intent(log_sign.this, signupactivity.class);
                startActivity(inext);
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inext;
                inext = new Intent(log_sign.this, loginactivity.class);
                startActivity(inext);
            }
        });
    }
}
