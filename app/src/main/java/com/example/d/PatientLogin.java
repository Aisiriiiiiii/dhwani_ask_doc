package com.example.d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PatientLogin extends AppCompatActivity {
    TextView movetoreg;
    EditText email, password;
    Button signup;
    FirebaseAuth mAuth;


        @Override
        public void onStart() {
            super.onStart();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                Intent intent = new Intent(getApplicationContext(), PatientHome.class);
                startActivity(intent);
                finish();

            }
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_patient_login);
            mAuth = FirebaseAuth.getInstance();
            email = findViewById(R.id.editTextTextEmailAddress4);
            password = findViewById(R.id.editTextTextPassword7);
            signup = findViewById(R.id.button5);
            movetoreg = findViewById(R.id.textView49);

            movetoreg = findViewById(R.id.textView49);
            movetoreg.setOnClickListener(v -> click());


            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String emailst, passwordst;
                    emailst = String.valueOf(email.getText());
                    passwordst = String.valueOf(password.getText());

                    if (TextUtils.isEmpty(emailst)){
                        Toast.makeText(PatientLogin.this, "Enter email", Toast.LENGTH_SHORT).show();
                    }

                    if (TextUtils.isEmpty(passwordst)){
                        Toast.makeText(PatientLogin.this, "Enter password", Toast.LENGTH_SHORT).show();
                    }
                    mAuth.signInWithEmailAndPassword(emailst, passwordst)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(PatientLogin.this, "Login successful!.",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), PatientHome.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(PatientLogin.this, "Email ID or Password Incorrect.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
            });
        }

    private void click() {
        Intent intent = new Intent(this, PatientReg.class);
        startActivity(intent);
    }
}
