package com.example.d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DoctorRegistration extends AppCompatActivity {
    private static final String TAG = "DoctorRegistration";

    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FULLNAME = "full name";
    private static final String KEY_DOCTORID = "doctor id";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_HOSPITALNAME = "hospital/clinic name";


    EditText email, password, fullname, doctorid, bday, hospname;
    Button signup;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference notebookRef = db.collection("Doctor Information");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword3);
        signup = findViewById(R.id.button9);
        fullname = findViewById(R.id.editTextText18);
        doctorid = findViewById(R.id.editTextText20);
        bday = findViewById(R.id.editTextText22);
        hospname = findViewById(R.id.editTextText21);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailst, passwordst;
                emailst = String.valueOf(email.getText());
                passwordst = String.valueOf(password.getText());

                if (TextUtils.isEmpty(emailst)) {
                    Toast.makeText(DoctorRegistration.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passwordst)) {
                    Toast.makeText(DoctorRegistration.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(emailst, passwordst)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(DoctorRegistration.this, "Account created!",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), DoctorLogin.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(DoctorRegistration.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    public void saveNote(View v) {
        String em = email.getText().toString();
        String pass = password.getText().toString();
        String fn = fullname.getText().toString();
        String did = doctorid.getText().toString();
        String bdy = bday.getText().toString();
        String hn = hospname.getText().toString();

        Map<String, Object> note = new HashMap<>();
        note.put(KEY_EMAIL, em);
        note.put(KEY_PASSWORD, pass);
        note.put(KEY_FULLNAME, fn);
        note.put(KEY_DOCTORID, did);
        note.put(KEY_BIRTHDAY, bdy);
        note.put(KEY_HOSPITALNAME, hn);

        db.collection("Doctor Information").add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(DoctorRegistration.this, "Registered!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DoctorRegistration.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}
