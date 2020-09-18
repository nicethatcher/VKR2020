package com.homechef;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLogin extends Activity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    EditText usermail,password;
    Button login,register;

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isPassword(EditText text) {
        CharSequence pass = text.getText().toString();
        return (pass.length()>5);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        mAuth = FirebaseAuth.getInstance();
        usermail = findViewById(R.id.userEmail);
        password = findViewById(R.id.userPassword);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser()!=null){
            Intent intent = new Intent(UserLogin.this, Chefs.class);
            intent.putExtra("ID",mAuth.getUid());
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.login:
                String usermail1;
                String password1;

                usermail1 = usermail.getText().toString().trim();
                password1 = password.getText().toString().trim();

                if(!isEmail(usermail)) {
                    usermail.setError("Enter valid email!");
                    break;
                }
                if (!isPassword(password)) {
                    password.setError("Password must be at least 6 characters!");
                    break;
                }
                else
                    mAuth.signInWithEmailAndPassword(usermail1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(UserLogin.this, Chefs.class);
                                intent.putExtra("ID", mAuth.getUid());
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid login details", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                break;

            case R.id.register:
                Intent intent = new Intent(UserLogin.this,UserRegister.class);
                startActivity(intent);
                finish();
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
