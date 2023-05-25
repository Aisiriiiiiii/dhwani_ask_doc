package com.example.d;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AudioActivity extends AppCompatActivity implements AudioAdapter.OnAudioItemClickListener {
    private RecyclerView recyclerView;
    private AudioAdapter audioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        recyclerView = findViewById(R.id.audioRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAudioItemsFromFirestore();
    }

    private void getAudioItemsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Audio")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<AudioItem> audioItems = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String title = documentSnapshot.getString("title");
                        String url = documentSnapshot.getString("url");

                        AudioItem audioItem = new AudioItem(title, url);
                        audioItems.add(audioItem);
                    }

                    audioAdapter = new AudioAdapter(audioItems, AudioActivity.this);
                    recyclerView.setAdapter(audioAdapter);
                });
    }

    @Override
    public void onAudioItemClick(AudioItem audioItem) {
        String title = audioItem.getTitle();
        Toast.makeText(this, "Clicked on audio: " + title, Toast.LENGTH_SHORT).show();
        playAudio(audioItem);

    }

    private void playAudio(AudioItem audioItem) {
        TextView titleTextView = findViewById(R.id.audioTitle);
        titleTextView.setText(audioItem.getTitle());

        
        SimpleExoPlayer exoPlayer = new SimpleExoPlayer.Builder(this).build();

       
        PlayerView playerView = findViewById(R.id.exoplayer);
        playerView.setPlayer(exoPlayer);

        
        Uri audioUri = Uri.parse(audioItem.getUrl());
        MediaSource mediaSource = buildMediaSource(audioUri);

        // Prepare the player with the media source
        exoPlayer.setMediaSource(mediaSource);
        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);

    }

    private MediaSource buildMediaSource(Uri audioUri) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, "Your Application Name");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(audioUri));
    }
}
