package com.example.appelaunda.activites;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appelaunda.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }
    public void signin(View view) {

        String userEmail = email.getText().toString();
        String userPasswd = password.getText().toString();

        if(TextUtils.isEmpty(userEmail)){

            Toast.makeText(this, "Enter Email!", Toast.LENGTH_SHORT).show();
            return;

        }

        if(TextUtils.isEmpty(userPasswd)){

            Toast.makeText(this, "Enter Password!", Toast.LENGTH_SHORT).show();
            return;

        }

        if(userPasswd.length() < 6){
            Toast.makeText(this, "Password must be >= 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(userEmail, userPasswd)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, " Login Failed"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }

            });

        startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }

public void signup(View view) {
    startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
}
}