package com.example.d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class PatientHome extends AppCompatActivity {
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FULLNAME = "full name";
    private static final String KEY_DOCTORID = "doctor id";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_MEDICALISSUE = "medical issue";

    TextView emailtw, passwordtw, docidtw, fullnametw, bdaytw, medissuetw, pname;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    ImageButton video,audio,drag;

    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        emailtw = findViewById(R.id.textView13);
        docidtw = findViewById(R.id.textView26);
        fullnametw = findViewById(R.id.textView27);
        bdaytw = findViewById(R.id.textView28);
        logout = findViewById(R.id.button12);
        medissuetw = findViewById(R.id.textView29);
        pname = findViewById(R.id.textView35);
        video = findViewById(R.id.imageButton9);
        audio =findViewById(R.id.imageButton8);
        drag = findViewById(R.id.imageButton7);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String patientEmail = user.getEmail();
            CollectionReference patientRef = db.collection("Patient Information");
            Query query = patientRef.whereEqualTo("email", patientEmail);

            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);

                        // Retrieve patient details from the document
                        String em = documentSnapshot.getString(KEY_EMAIL);
                        String pass = documentSnapshot.getString(KEY_PASSWORD);
                        String fn = documentSnapshot.getString(KEY_FULLNAME);
                        String mdi = documentSnapshot.getString(KEY_MEDICALISSUE);
                        String dcid = documentSnapshot.getString(KEY_DOCTORID);
                        String bdy = documentSnapshot.getString(KEY_BIRTHDAY);
                        String pn = documentSnapshot.getString(KEY_FULLNAME);

                        // Display the patient data in the appropriate TextViews
                        emailtw.setText(em);
                        passwordtw.setText(pass);
                        fullnametw.setText(fn);
                        docidtw.setText(dcid);
                        medissuetw.setText(mdi);
                        bdaytw.setText(bdy);
                        pname.setText(pn);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Handle failure
                }
            });
        } else {
            Intent intent = new Intent(getApplicationContext(), PatientLogin.class);
            startActivity(intent);
            finish();
        }
    }
}
