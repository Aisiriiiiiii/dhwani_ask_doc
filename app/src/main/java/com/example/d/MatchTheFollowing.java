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
    ImageView wheelchairImage;
    ImageView hearingAidImage;
    ImageView prostheticLimbImage;

    TextView legsTarget;
    TextView earsTarget;
    TextView limbTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_the_following);

        wheelchairImage = findViewById(R.id.imageView15);
        hearingAidImage = findViewById(R.id.imageView);
        prostheticLimbImage = findViewById(R.id.imageView14);

        legsTarget = findViewById(R.id.textView46);
        earsTarget = findViewById(R.id.textView47);
        limbTarget = findViewById(R.id.textView48);

        wheelchairImage.setOnLongClickListener(this);
        hearingAidImage.setOnLongClickListener(this);
        prostheticLimbImage.setOnLongClickListener(this);

        legsTarget.setOnDragListener(this);
        earsTarget.setOnDragListener(this);
        limbTarget.setOnDragListener(this);
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
                if (draggedView.getId() == wheelchairImage.getId() && targetView.getId() == legsTarget.getId()) {
                    // Correct match for wheelchair and legs
                    Toast.makeText(this, "Correct match!", Toast.LENGTH_SHORT).show();
                } else if (draggedView.getId() == hearingAidImage.getId() && targetView.getId() == earsTarget.getId()) {
                    // Correct match for hearing aid and ears
                    Toast.makeText(this, "Correct match!", Toast.LENGTH_SHORT).show();
                } else if (draggedView.getId() == prostheticLimbImage.getId() && targetView.getId() == limbTarget.getId()) {
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
