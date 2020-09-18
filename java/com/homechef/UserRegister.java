package com.homechef;

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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegister extends AppCompatActivity {

    EditText name;
    EditText phone;
    EditText address;
    EditText email;
    EditText password;
    Button register;

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isPassword(EditText text) {
        CharSequence pass = text.getText().toString();
        return (pass.length()>5);
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);

        final FirebaseAuth mAuth;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users");

        name = findViewById(R.id.userFullName);
        phone = findViewById(R.id.userPhoneNumber);
        address = findViewById(R.id.userAddress);
        email = findViewById(R.id.userEmailRegister);
        password = findViewById(R.id.userPassword);
        register = findViewById(R.id.userRegister);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String uname = name.getText().toString().trim();
                final String uphone = phone.getText().toString().trim();
                final String uaddress = address.getText().toString().trim();
                final String uemail = email.getText().toString().trim();
                final String upassword = password.getText().toString().trim();

                if (isEmpty(name)) {
                    name.setError("Full name is required!");
                }

                if (isEmpty(phone)) {
                    phone.setError("Phone number is required!");
                }

                if (isEmpty(address)) {
                    address.setError("Address is required!");
                }

                if (!isEmail(email)) {
                    email.setError("Enter valid email!");
                }
                if (!isPassword(password)) {
                    password.setError("Enter valid password!");
                }

                else

                mAuth.createUserWithEmailAndPassword(uemail, upassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                String uid = mAuth.getUid();

                                User user = new User(uid, uname, uphone, uemail, uaddress);

                                myRef.push().setValue(user);

                                Toast.makeText(UserRegister.this, "Done", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(UserRegister.this, Chefs.class);
                                intent.putExtra("ID", mAuth.getUid());
                                startActivity(intent);
                                finish();
                            }
                        });
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, UserLogin.class);
        startActivity(intent);
        finish();
    }
}
