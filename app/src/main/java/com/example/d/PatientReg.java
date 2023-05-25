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
import com.google.android.play.core.integrity.v;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PatientReg extends AppCompatActivity {
    private static final String TAG = "PatientRegistration";

    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FULLNAME = "full name";
    private static final String KEY_DOCTORID = "doctor id";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_MEDICALISSUE = "medical issue";


    EditText email, password, fullname, doctorid, bday, medissue, movetolog;
    Button signuppat;
    FirebaseAuth mAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference notebookRef = db.collection("Patient Information");

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_patient_reg);

            mAuth = FirebaseAuth.getInstance();
            email = findViewById(R.id.editTextTextEmailAddress);
            password = findViewById(R.id.editTextTextPassword2);
            signuppat = findViewById(R.id.button8);
            fullname = findViewById(R.id.editTextText2);
            doctorid = findViewById(R.id.editTextText8);
            bday = findViewById(R.id.editTextText6);
            medissue = findViewById(R.id.editTextText7);

            movetolog = findViewById(R.id.textView52);
            movetolog.setOnClickListener(v -> click());




            signuppat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String emailst, passwordst;
                    emailst = String.valueOf(email.getText());
                    passwordst = String.valueOf(password.getText());

                    if (TextUtils.isEmpty(emailst)) {
                        Toast.makeText(PatientReg.this, "Enter email", Toast.LENGTH_SHORT).show();
                    }

                    if (TextUtils.isEmpty(passwordst)) {
                        Toast.makeText(PatientReg.this, "Enter password", Toast.LENGTH_SHORT).show();

                    }

                    mAuth.createUserWithEmailAndPassword(emailst, passwordst)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(PatientReg.this, "Account created!.",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), PatientLogin.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Toast.makeText(PatientReg.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
            });


        }

    private void click() {
        Intent intent = new Intent(this, PatientLogin.class);
        startActivity(intent);
    }

    public void saveNotep (View v) {
            String em = email.getText().toString();
            String pass = password.getText().toString();
            String fn = fullname.getText().toString();
            String did = doctorid.getText().toString();
            String bdy = bday.getText().toString();
            String ms = medissue.getText().toString();


            Map<String, Object> note = new HashMap<>();
            note.put(KEY_EMAIL, em);
            note.put(KEY_PASSWORD, pass);
            note.put(KEY_FULLNAME, fn);
            note.put(KEY_DOCTORID, did);
            note.put(KEY_BIRTHDAY, bdy);
            note.put(KEY_MEDICALISSUE, ms);

            db.collection("Patient Information").add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(PatientReg.this, "Registered!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PatientReg.this, "Error!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });
        }

}
