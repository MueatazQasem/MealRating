package ca.myandroid.mueataz;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import ca.myandroid.mueataz.model.MyRating;
public class ResultRatingActivity extends AppCompatActivity {
    private TextView ratingResultTextView;
    private RadioGroup starRadioGroup;
    private RadioButton oneStarRadioButton, twoStarsRadioButton, threeStarsRadioButton;
    private RadioGroup sortRadioGroup;
    private RadioButton ascendingRadioButton, descendingRadioButton;
    private ListView ratingListView;
    private TextView registerTextView;
    private EditText nameEditText;
    private Button backButton;
    private ArrayList<MyRating> allRatings;
    private ArrayList<MyRating> filteredRatings;
    private ArrayAdapter<MyRating> ratingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_rating);

        ratingResultTextView = findViewById(R.id.ratingResultTextView);
        starRadioGroup = findViewById(R.id.starRadioGroup);
        oneStarRadioButton = findViewById(R.id.oneStarRadioButton);
        twoStarsRadioButton = findViewById(R.id.twoStarsRadioButton);
        threeStarsRadioButton = findViewById(R.id.threeStarsRadioButton);
        sortRadioGroup = findViewById(R.id.sortRadioGroup);
        ascendingRadioButton = findViewById(R.id.ascendingRadioButton);
        descendingRadioButton = findViewById(R.id.descendingRadioButton);
        ratingListView = findViewById(R.id.ratingListView);
        registerTextView = findViewById(R.id.registerTextView);
        nameEditText = findViewById(R.id.nameEditText);
        backButton = findViewById(R.id.backButton);

        ratingResultTextView.setText("Rating Result");

        Intent intent = getIntent();
        allRatings = intent.getParcelableArrayListExtra("ratings");

        filteredRatings = new ArrayList<>(allRatings);

        ratingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredRatings);
        ratingListView.setAdapter(ratingAdapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", name);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        starRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                filterRatings();
            }
        });

        sortRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                filterRatings();
            }
        });

        filterRatings();
    }
    private void filterRatings() {
        filteredRatings.clear();

        filteredRatings.addAll(allRatings);

        int selectedStarId = starRadioGroup.getCheckedRadioButtonId();
        int selectedStarCount = 0;
        if (selectedStarId == R.id.oneStarRadioButton) {
            selectedStarCount = 1;
        } else if (selectedStarId == R.id.twoStarsRadioButton) {
            selectedStarCount = 2;
        } else if (selectedStarId == R.id.threeStarsRadioButton) {
            selectedStarCount = 3;
        }

        if (selectedStarCount > 0) {
            int finalSelectedStarCount = selectedStarCount;
            filteredRatings.removeIf(rating -> rating.getRating() != finalSelectedStarCount);
        }

        boolean ascending = ascendingRadioButton.isChecked();

        if (!ascending) {
            Collections.reverse(filteredRatings);
        }

        ratingAdapter.notifyDataSetChanged();
    }
}
