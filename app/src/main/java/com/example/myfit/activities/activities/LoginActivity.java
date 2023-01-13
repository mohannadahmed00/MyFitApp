package com.example.myfit.activities.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.UiAutomation;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myfit.R;
import com.example.myfit.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        ActivityLoginBinding loginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        loginBinding.setLifecycleOwner(this);
        loginBinding.btnLoginActivityLogin.setOnClickListener(e-> startActivity(new Intent(LoginActivity.this,MainActivity.class)));
        loginBinding.ivShowPassActivityLogin.setOnClickListener(e->{
            if (loginBinding.etPassActivityLogin.getTransformationMethod()!=HideReturnsTransformationMethod.getInstance()){
                loginBinding.ivShowPassActivityLogin.setImageResource(R.drawable.eye_close);
                loginBinding.etPassActivityLogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else {
                loginBinding.ivShowPassActivityLogin.setImageResource(R.drawable.eye_open);
                loginBinding.etPassActivityLogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            loginBinding.etPassActivityLogin.setSelection(loginBinding.etPassActivityLogin.getText().length());

        });
        loginBinding.etPassActivityLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


}