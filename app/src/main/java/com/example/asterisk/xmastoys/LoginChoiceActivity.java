package com.example.asterisk.xmastoys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LoginChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_choice);

        setTitle(R.string.login_choice);

        Button eMailButton = findViewById(R.id.email_button);
        Button phoneButton = findViewById(R.id.phone_button);
        Button googleButton = findViewById(R.id.google_button);

        eMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginChoiceActivity.this,
                        EmailLoginActivity.class));
                // Removing activity from back stack
                finish();
            }
        });

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo change name of the class that would be opened after click on this button
                startActivity(new Intent(LoginChoiceActivity.this, MainActivity.class));
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo change name of the class that would be opened after click on this button
                startActivity(new Intent(LoginChoiceActivity.this, MainActivity.class));
            }
        });
    }
}