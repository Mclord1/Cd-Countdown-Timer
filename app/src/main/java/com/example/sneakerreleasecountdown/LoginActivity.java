package com.example.sneakerreleasecountdown;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.muddzdev.styleabletoast.StyleableToast;

public class LoginActivity extends AppCompatActivity {
    String username = "BobSmith";
    String password = "sneakers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText userNameField = findViewById(R.id.userNameField);
        final EditText passwordField = findViewById(R.id.passwordField);

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fromUserNameField = userNameField.getText().toString().trim();
                final String fromPasswordField = passwordField.getText().toString().trim();

                if (TextUtils.isEmpty(userNameField.getText()) || TextUtils.isEmpty(passwordField.getText())) {
                    showWarningToast(LoginActivity.this, "Please enter a username or password");
                } else {
                    if (fromUserNameField.equals(username) && fromPasswordField.equals(password)) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        showSuccessToast(LoginActivity.this, "Login Successful");
                    } else {
                        showWarningToast(LoginActivity.this, "Invalid credentials, please enter the correct username or password");
                    }
                }
            }
        });

        findViewById(R.id.forgotPasswordButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSuccessToast(LoginActivity.this, "Password Reset Successful");
            }
        });
    }


    public static void showSuccessToast(Activity activity, String message) {
        new StyleableToast.Builder(activity)
                .text(message)
                .textColor(ContextCompat.getColor(activity, R.color.white))
                .backgroundColor(ContextCompat.getColor(activity, R.color.success))
                .font(R.font.google_sans_regular)
                .cornerRadius(10)
                .show();
    }

    public static void showWarningToast(Activity activity, String message) {
        new StyleableToast.Builder(activity)
                .text(message)
                .textColor(ContextCompat.getColor(activity, R.color.fontColor))
                .backgroundColor(ContextCompat.getColor(activity, R.color.ashColor))
                .font(R.font.google_sans_regular)
                .cornerRadius(10)
                .show();
    }
}
