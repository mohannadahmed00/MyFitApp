package com.example.myfit.activities.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfit.R;
import com.example.myfit.activities.models.UserModel;
import com.example.myfit.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    ActivitySignUpBinding signUpBinding;
    FirebaseAuth firebaseAuth;
    UserModel user;
    String verifyWay=null;
    boolean name = false, email = false, phone = false, pass = false, rePass = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        signUpBinding=DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        signUpBinding.setLifecycleOwner(this);

        user=new UserModel();
        firebaseAuth = FirebaseAuth.getInstance();
        signUpBinding.ivShowPassActivitySignUp.setOnClickListener(this);
        signUpBinding.ivShowRepassActivitySignUp.setOnClickListener(this);
        signUpBinding.btnNextActivitySignUp.setOnClickListener(this);
        signUpBinding.tvLoginActivitySignUp.setOnClickListener(this);
        signUpBinding.rbMailActivitySignUp.setOnClickListener(this);
        signUpBinding.rbPhoneActivitySignUp.setOnClickListener(this);
        signUpBinding.etFullNameActivitySignUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                name = charSequence.length() != 0;
                activeButton(signUpBinding.btnNextActivitySignUp, name, email, phone, pass, rePass,verifyWay);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signUpBinding.etEmailActivitySignUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                email = charSequence.toString().contains("@");
                activeButton(signUpBinding.btnNextActivitySignUp, name, email, phone, pass, rePass,verifyWay);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signUpBinding.etNumberActivitySignUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                phone = charSequence.length() == 11;
                activeButton(signUpBinding.btnNextActivitySignUp, name, email, phone, pass, rePass,verifyWay);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signUpBinding.etPassActivitySignUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pass = checkPassword(String.valueOf(charSequence), signUpBinding.tvPassHintActivitySignUp);
                if (String.valueOf(charSequence).equals(signUpBinding.etRepassActivitySignUp.getText().toString())) {
                    signUpBinding.tvRepassHintActivitySignUp.setVisibility(View.GONE);
                    rePass = true;
                } else {
                    if (signUpBinding.etRepassActivitySignUp.getText().toString().length()!=0){
                        signUpBinding.tvRepassHintActivitySignUp.setVisibility(View.VISIBLE);
                    }else {
                        signUpBinding.tvRepassHintActivitySignUp.setVisibility(View.GONE);
                    }
                    rePass = false;
                }
                activeButton(signUpBinding.btnNextActivitySignUp, name, email, phone, pass, rePass,verifyWay);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signUpBinding.etRepassActivitySignUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (String.valueOf(charSequence).equals(signUpBinding.etPassActivitySignUp.getText().toString())) {
                    signUpBinding.tvRepassHintActivitySignUp.setVisibility(View.GONE);
                    rePass = true;
                } else {
                    if (charSequence.length()!=0){
                        signUpBinding.tvRepassHintActivitySignUp.setVisibility(View.VISIBLE);
                    }else {
                        signUpBinding.tvRepassHintActivitySignUp.setVisibility(View.GONE);
                    }
                    rePass = false;
                }
                activeButton(signUpBinding.btnNextActivitySignUp, name, email, phone, pass, rePass,verifyWay);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    boolean checkPassword(String pass, TextView textView) {
        boolean isSpecial = false, isCapital = false, isDigit = false, isAlpha = false, total = false;
        String specialCharactersString = "!@#$%&*()'+,-./:;<=>?[]^_`{|}";
        for (int i = 0; i < pass.length(); i++) {
            if (specialCharactersString.contains(String.valueOf(pass.charAt(i)))) {
                isSpecial = true;
            } else if (Character.isUpperCase(pass.charAt(i))) {
                isCapital = true;
            } else if (Character.isDigit(pass.charAt(i))) {
                isDigit = true;
            } else if (Character.isLowerCase(pass.charAt(i))) {
                isAlpha = true;
            }
        }
        if (isCapital && isSpecial && isDigit && isAlpha && pass.length() >= 8) {
            textView.setVisibility(View.GONE);
            total = true;
        } else if (!isCapital && !isSpecial && !isDigit && !isAlpha && pass.length() < 8 && pass.length() != 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("Password is too short must be at least 8 characters\nPassword must contain Uppercase characters (A-Z) and Lowercase characters (a-z) and\nPassword must contain Special characters ($,%,^,~,!,<,>,&,@,#)\nPassword must contain Digits (0-9)");
        } else {
            if (pass.length()!=0) {
                textView.setText("");
                textView.setVisibility(View.VISIBLE);
                if (pass.length() < 8) {
                    if (textView.getText().length() == 0) {
                        textView.setText("Password is too short must be at least 8 characters");
                    } else {
                        textView.setText(textView.getText() + "\nPassword is too short must be at least 8 characters");
                    }
                }
                if (!isCapital) {
                    if (textView.getText().length() == 0) {
                        textView.setText("Password must contain Uppercase characters (A-Z)");
                    } else {
                        textView.setText(textView.getText() + "\nPassword must contain Uppercase characters (A-Z)");
                    }
                }
                if (!isAlpha) {
                    if (textView.getText().length() == 0) {
                        textView.setText("Password must contain Lowercase characters (a-z)");
                    } else {
                        textView.setText(textView.getText() + "\nPassword must contain Lowercase characters (a-z)");
                    }
                }
                if (!isSpecial) {
                    if (textView.getText().length() == 0) {
                        textView.setText("Password must contain Special characters ($,%,^,~,!,<,>,&,@,#)");
                    } else {
                        textView.setText(textView.getText() + "\nPassword must contain Special characters ($,%,^,~,!,<,>,&,@,#)");
                    }
                }
                if (!isDigit) {
                    if (textView.getText().length() == 0) {
                        textView.setText("Password must contain Digits (0-9)");
                    } else {
                        textView.setText(textView.getText() + "\nPassword must contain Digits (0-9)");
                    }
                }
            }else {
                textView.setVisibility(View.GONE);
            }
        }
        return total;
    }
    void activeButton(Button button, boolean etFlag1, boolean etFlag2, boolean etFlag3, boolean etFlag4, boolean etFlag5,String rbFlag6) {
        if (etFlag1 & etFlag2 & etFlag3 & etFlag4 & etFlag5 & rbFlag6!=null) {
            button.setEnabled(true);
            button.setBackgroundTintList(ContextCompat.getColorStateList(getBaseContext(), R.color.purple_700));
        } else {
            //return it
            button.setEnabled(true);
            button.setBackgroundTintList(ContextCompat.getColorStateList(getBaseContext(), R.color.light_gray));
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_show_pass_activity_sign_up:
                if (signUpBinding.etPassActivitySignUp.getTransformationMethod() != HideReturnsTransformationMethod.getInstance()) {
                    signUpBinding.ivShowPassActivitySignUp.setImageResource(R.drawable.eye_close);
                    signUpBinding.etPassActivitySignUp.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    signUpBinding.ivShowPassActivitySignUp.setImageResource(R.drawable.eye_open);
                    signUpBinding.etPassActivitySignUp.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                signUpBinding.etPassActivitySignUp.setSelection(signUpBinding.etPassActivitySignUp.getText().length());
                break;
            case R.id.iv_show_repass_activity_sign_up:
                if (signUpBinding.etRepassActivitySignUp.getTransformationMethod() != HideReturnsTransformationMethod.getInstance()) {
                    signUpBinding.ivShowRepassActivitySignUp.setImageResource(R.drawable.eye_close);
                    signUpBinding.etRepassActivitySignUp.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    signUpBinding.ivShowRepassActivitySignUp.setImageResource(R.drawable.eye_open);
                    signUpBinding.etRepassActivitySignUp.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                signUpBinding.etRepassActivitySignUp.setSelection(signUpBinding.etRepassActivitySignUp.getText().length());
                break;
            case R.id.btn_next_activity_sign_up:
                user.setName(signUpBinding.etFullNameActivitySignUp.getText().toString());
                user.setEmail(signUpBinding.etEmailActivitySignUp.getText().toString());
                user.setPhone(signUpBinding.etNumberActivitySignUp.getText().toString());
                user.setPassword(signUpBinding.etPassActivitySignUp.getText().toString());
                user.setVerify(verifyWay);
                Intent intent=new Intent(SignUpActivity.this,VerifyActivity.class);
                intent.putExtra("userInfo",user);
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           Toast.makeText(SignUpActivity.this, "created", Toast.LENGTH_SHORT).show();
                           startActivity(intent);
                       }else {
                           Toast.makeText(SignUpActivity.this, "not created", Toast.LENGTH_SHORT).show();
                       }
                    }
                });
                break;
            case R.id.tv_login_activity_sign_up:
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                break;
            case R.id.rb_mail_activity_sign_up:
                verifyWay="mail";
                activeButton(signUpBinding.btnNextActivitySignUp, name, email, phone, pass, rePass, verifyWay);

                break;
            case R.id.rb_phone_activity_sign_up:
                verifyWay="sms";
                activeButton(signUpBinding.btnNextActivitySignUp, name, email, phone, pass, rePass,verifyWay);

                break;
            default:
                Toast.makeText(this, "sad :(", Toast.LENGTH_SHORT).show();

        }
    }

}