package com.example.myfit.activities.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.myfit.R;
import com.example.myfit.activities.models.UserModel;
import com.example.myfit.databinding.ActivityVerifyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityVerifyBinding verifyBinding;
    UserModel user;
    String sentCode;
    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callBacks;
    //PhoneAuthProvider.OnVerificationStateChangedCallbacks callBacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        /*FirebaseApp.initializeApp(*//*context=*//* this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                SafetyNetAppCheckProviderFactory.getInstance());*/
        auth=FirebaseAuth.getInstance();
        verifyBinding = DataBindingUtil.setContentView(this, R.layout.activity_verify);
        verifyBinding.setLifecycleOwner(this);
        Bundle bundle=getIntent().getExtras();
        user= (UserModel) bundle.get("userInfo");
        callBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                sentCode = phoneAuthCredential.getSmsCode();
                if (sentCode!=null){
                    verifyBinding.etOtpActivityVerification.setText(sentCode);
                    verifyCode(sentCode);
                }
                //createAccount();

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(VerifyActivity.this, "OTP sent", Toast.LENGTH_SHORT).show();
                turnTimer();
                sentCode=s;
            }
        };
        sendVerificationCode("+2"+user.getPhone());
        verifyBinding.tvVerifyWayActivityVerification.setText("+2"+user.getPhone());
        verifyBinding.etOtpActivityVerification.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()==6){
                    btnSwitch(verifyBinding.btnContinueActivityVerification,"on");
                }else {
                    btnSwitch(verifyBinding.btnContinueActivityVerification,"off");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        turnTimer();
        verifyBinding.tvSendCodeActivityVerification.setOnClickListener(this);
        //sendVerificationCode("+2" + user.getPhone());
        /*callBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                createAccount();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                turnTimer();
                sentCode=s;
            }
        };*/
        /*PhoneAuthOptions phoneAuthOptions= PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+2"+user.getPhone())
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callBacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);*/
    }
    void btnSwitch(Button button,String status){
        switch (status){
            case "on":
                button.setEnabled(true);
                button.setBackgroundTintList(ContextCompat.getColorStateList(getBaseContext(), R.color.purple_700));
                break;
            case "off":
                button.setEnabled(false);
                button.setBackgroundTintList(ContextCompat.getColorStateList(getBaseContext(), R.color.light_gray));
            default:
                Toast.makeText(this, "sad :(", Toast.LENGTH_SHORT).show();


        }
    }
    void turnTimer(){
        new CountDownTimer(60000,1000){
            public void onTick(long millisUntilFinished) {
                verifyBinding.tvSendCodeActivityVerification.setTextColor(getResources().getColor(R.color.gray));
                verifyBinding.tvSendCodeActivityVerification.setEnabled(false);
                verifyBinding.tvSecondsActivityVerification.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                verifyBinding.tvSendCodeActivityVerification.setTextColor(getResources().getColor(R.color.purple_700));
                verifyBinding.tvSendCodeActivityVerification.setEnabled(true);
                //mTextField.setText("done!");
            }

        }.start();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_send_code_activity_verification:
                turnTimer();
                sendVerificationCode("+2"+user.getPhone());
                break;
            case R.id.btn_continue_activity_verification:
                verifyCode(sentCode);
                /*if (sentCode!=null && sentCode.equals(verifyBinding.etOtpActivityVerification.getText().toString())){

                    createAccount();
                }else {
                    Toast.makeText(this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                }*/
                break;
        }
    }
    private void createAccount(){
        auth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(VerifyActivity.this, "Account created ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(VerifyActivity.this,LoginActivity.class));
                }else {
                    Toast.makeText(VerifyActivity.this, "Account not created ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendVerificationCode(String phoneNumber){
        PhoneAuthOptions phoneAuthOptions= PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callBacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
    }
    private void verifyCode(String sentCode) {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verifyBinding.etOtpActivityVerification.getText().toString(),sentCode);
        if (auth.signInWithCredential(credential).isSuccessful()){
            Toast.makeText(VerifyActivity.this, "Verified :)", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(VerifyActivity.this, "Not Verified :(", Toast.LENGTH_SHORT).show();
        }
    }
}