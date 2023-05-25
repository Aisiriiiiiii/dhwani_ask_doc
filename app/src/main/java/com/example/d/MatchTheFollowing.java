package com.example.d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MatchTheFollowing extends AppCompatActivity implements View.OnLongClickListener, View.OnDragListener {
    ImageView hearingaid, leg, prosleg, walk, crutch, ear;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_the_following);

        hearingaid= findViewById(R.id.hearingaid);
        prosleg = findViewById(R.id.imageView14);
        crutch = findViewById(R.id.imageView15);
        ear = findViewById(R.id.imageView16);
        leg = findViewById(R.id.imageView19);
        walk = findViewById(R.id.imageView20);

        hearingaid.setOnLongClickListener(this);
        prosleg.setOnLongClickListener(this);
        crutch.setOnLongClickListener(this);

        ear.setOnDragListener(this);
        leg.setOnDragListener(this);
        walk.setOnDragListener(this);
    }

    @Override
    public boolean onLongClick(View view) {
        // Start the drag when the view is long-clicked
        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
        ClipData dragData = new ClipData(view.getTag().toString(), new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

        // Start dragging the view
        view.startDrag(dragData, shadowBuilder, view, 0);
        return true;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();

        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // Called when the drag is started
                break;

            case DragEvent.ACTION_DRAG_ENTERED:
                // Called when a dragged view enters a target view
                break;

            case DragEvent.ACTION_DRAG_EXITED:
                // Called when a dragged view exits a target view
                break;

            case DragEvent.ACTION_DROP:
                // Called when the dragged view is dropped on a target view
                View draggedView = (View) event.getLocalState();
                TextView targetView = (TextView) v;

                // Check if the match is correct
                if (draggedView.getId() == hearingaid.getId() && targetView.getId() == ear.getId()) {
                    // Correct match for wheelchair and legs
                    Toast.makeText(this, "Correct match!", Toast.LENGTH_SHORT).show();
                } else if (draggedView.getId() == crutch.getId() && targetView.getId() == walk.getId()) {
                    // Correct match for hearing aid and ears
                    Toast.makeText(this, "Correct match!", Toast.LENGTH_SHORT).show();
                } else if (draggedView.getId() == prosleg.getId() && targetView.getId() == leg.getId()) {
                    // Correct match for prosthetic limb and limb
                    Toast.makeText(this, "Correct match!", Toast.LENGTH_SHORT).show();
                } else {
                    // Incorrect match
                    Toast.makeText(this, "Incorrect match!", Toast.LENGTH_SHORT).show();
                }

                break;

            case DragEvent.ACTION_DRAG_ENDED:
                // Called when the drag operation ends
                break;

            default:
                break;
        }

        return true;
    }
}
