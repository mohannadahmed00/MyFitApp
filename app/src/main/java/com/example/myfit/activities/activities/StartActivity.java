package com.example.myfit.activities.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.myfit.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_start);
        Button btnLogin;
        TextView tvSignUp;
        btnLogin=findViewById(R.id.btn_login_activity_start);
        tvSignUp=findViewById(R.id.tv_create_activity_start);
        btnLogin.setOnClickListener(e->{
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            /*overridePendingTransition(R.anim.slide_out_right,
                    R.anim.slide_in_left);*/
        });
        tvSignUp.setOnClickListener(e->{
            Intent intent = new Intent(StartActivity.this, SignUpActivity.class);
            startActivity(intent);
            /*overridePendingTransition(R.anim.slide_out_right,
                    R.anim.slide_in_left);*/
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void changeStatBarColor(AppCompatActivity activity, int color) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(activity, (int) Color.TRANSPARENT));
        //???????????????????
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        //???????????????????
    }
}