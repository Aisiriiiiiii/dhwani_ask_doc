package com.example.d;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.d.R;
import com.example.d.VideoAdapter;
import com.example.d.VideoItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private VideoAdapter mVideoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getVideoItemsFromFirestore();
    }

    private void getVideoItemsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Videos")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<VideoItem> videoItems = new ArrayList<>();
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                            String title = documentSnapshot.getString("title");
                            String url = documentSnapshot.getString("url");

                            VideoItem videoItem = new VideoItem(title, url);
                            videoItems.add(videoItem);
                        }

                        mVideoAdapter = new VideoAdapter(VideoActivity.this, videoItems);
                        mRecyclerView.setAdapter(mVideoAdapter);
                    }
                });
    }
}


