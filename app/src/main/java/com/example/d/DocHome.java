package com.example.d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;

public class DocHome extends AppCompatActivity {

    private static final String TAG = "DoctorRegistration";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FULLNAME = "full name";
    private static final String KEY_DOCTORID = "doctor id";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_HOSPITALNAME = "hospital/clinic name";
    private static final String KEY_MEDICALISSUE = "medical issue";


    TextView nametw, docidtw;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Button logout;

    ImageButton video,audio,drag;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_home);
        nametw = findViewById(R.id.textView37);
        docidtw = findViewById(R.id.textView40);
        logout = findViewById(R.id.button10);
        video = findViewById(R.id.imageButton9);
        audio =findViewById(R.id.imageButton8);
        drag = findViewById(R.id.imageButton7);

        video = findViewById(R.id.imageButton9);
        video.setOnClickListener(v -> click());

        audio = findViewById(R.id.imageButton8);
        audio.setOnClickListener(v -> click1());

        drag = findViewById(R.id.imageButton7);
        drag.setOnClickListener(v -> click2());

        List<Item> items = new ArrayList<>();
        recyclerView = findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter adapter = new Adapter(this, items);
        recyclerView.setAdapter(adapter);

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
            String docEmail = user.getEmail();
            CollectionReference docRef = db.collection("Doctor Information");
            Query query = docRef.whereEqualTo("email", docEmail);

            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);

                        // Retrieve doctor details from the document
                        String fn = documentSnapshot.getString(KEY_FULLNAME);
                        String did = documentSnapshot.getString(KEY_DOCTORID);

                        nametw.setText(fn);
                        docidtw.setText(did);

                        // Retrieve patients linked to the doctor
                        String doctorId = documentSnapshot.getString(KEY_DOCTORID);
                        if (doctorId != null) {
                            CollectionReference patientRef = db.collection("Patient Information");
                            Query patientQuery = patientRef.whereEqualTo(KEY_DOCTORID, doctorId);

                            patientQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    items.clear(); // Clear previous patient data

                                    for (DocumentSnapshot patientSnapshot : queryDocumentSnapshots.getDocuments()) {
                                        // Create a new Item object for each document
                                        String email = patientSnapshot.getString(KEY_EMAIL);
                                        String password = patientSnapshot.getString(KEY_PASSWORD);
                                        String fullName = patientSnapshot.getString(KEY_FULLNAME);
                                        String patientDoctorId = patientSnapshot.getString(KEY_DOCTORID);
                                        String birthday = patientSnapshot.getString(KEY_BIRTHDAY);
                                        String medicalIssue = patientSnapshot.getString(KEY_MEDICALISSUE);

                                        Item item = new Item(email, password, fullName, birthday, medicalIssue);
                                        items.add(item); // Add item to the list
                                    }

                                    adapter.notifyDataSetChanged(); // Notify the adapter of data change
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure
                                }
                            });
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Handle failure
                }
            });
        } else {
            Intent intent = new Intent(getApplicationContext(), DoctorLogin.class);
            startActivity(intent);
            finish();
        }
    }

    private void click2() {
        Intent intent = new Intent(this, MatchTheFollowing.class);
        startActivity(intent);
    }

    private void click1() {
        Intent intent = new Intent(this, AudioActivity.class);
        startActivity(intent);
    }

    private void click() {
        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
    }

}
