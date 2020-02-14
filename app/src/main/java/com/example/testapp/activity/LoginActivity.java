package com.example.testapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText mEmailId_et, mPassword_et;
    MaterialTextView mForgetPassword_tv;
    MaterialButton mLogin_Btn, mSignUp_Btn;

    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mEmailId_et = findViewById(R.id.et_emailId);
        mPassword_et = findViewById(R.id.et_password);
        mForgetPassword_tv = findViewById(R.id.tv_forgot_password);
        mLogin_Btn = findViewById(R.id.btn_logIn);
        mSignUp_Btn = findViewById(R.id.btn_signUp);

        mLogin_Btn.setOnClickListener(this);
        mSignUp_Btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logIn:
                if (check_fields())
                    login();
                break;
            case R.id.btn_signUp:
                openRegisterActivity();
                break;
            case R.id.tv_forgot_password:
                break;
            default:
                break;
        }
    }

    private boolean check_fields() {
        email = mEmailId_et.getText().toString();
        password = mPassword_et.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "fill any empty fields", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailId_et.requestFocus();
            mEmailId_et.setError("enter valid email");
            return false;
        } else if (password.length() < 8) {
            mPassword_et.requestFocus();
            mPassword_et.setError("minimum 8 characters");
            return false;
        }
        return true;
    }

    private void login() {
        ProgressBar progressBar;
        AlertDialog alertDialogProgress;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.layout_pb_circular, null);
        TextView textView = view.findViewById(R.id.tv_processing);
        String logging = "logging in...";
        textView.setText(logging);
        progressBar = view.findViewById(R.id.pb);
        builder.setView(view);

        alertDialogProgress = builder.create();
        alertDialogProgress.setCancelable(false);
        alertDialogProgress.setCanceledOnTouchOutside(false);
        alertDialogProgress.setView(view, 0, 15, 0, 15);
        alertDialogProgress.show();

//        progressBar.setVisibility(View.VISIBLE);
//        mauth.signInWithEmailAndPassword(email,password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressBar.setVisibility(View.GONE);
//
//                        if(task.isSuccessful()){
//                            Toast.makeText(LoginActivity.this, "sign in successful", Toast.LENGTH_SHORT).show();
//
//                            Intent intent = new Intent(LoginActivity.this,ProfileActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
//
//                        }else{
//                                Toast.makeText(LoginActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    });
    }


    private void openRegisterActivity() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

}
