package ca.myandroid.mueataz;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import ca.myandroid.mueataz.model.MyRating;
public class MainActivity extends AppCompatActivity {
    private Spinner spinner;
    private ImageView imageView;
    private TextView rateTextView;
    private TextView nameTextView;
    private RatingBar ratingBar;
    private Button addButton, showAllButton, mealButton, saladButton;

    private List<String> mealList;
    private List<String> saladList;
    private List<MyRating> allRatings;
    private static final int REQUEST_CODE_RESULT_ACTIVITY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mealRatingTextView = findViewById(R.id.mealRatingTextView);
        spinner = findViewById(R.id.spinner);
        imageView = findViewById(R.id.imageView);
        rateTextView = findViewById(R.id.rateTextView);
        ratingBar = findViewById(R.id.ratingBar);
        addButton = findViewById(R.id.addButton);
        showAllButton = findViewById(R.id.showAllButton);
        mealButton = findViewById(R.id.mealButton);
        saladButton = findViewById(R.id.saladButton);
        nameTextView = findViewById(R.id.nameTextView);

        mealRatingTextView.setText("Meal Rating");
        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("Poutine");
        spinnerItems.add("Salmon");
        spinnerItems.add("Sushi");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                updateImageView(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        allRatings = new ArrayList<>();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                Toast.makeText(MainActivity.this, "Rating: " + rating, Toast.LENGTH_SHORT).show();
                addRating(); // Call method to add rating
            }
        });

        showAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResultRatingActivity.class);
                intent.putParcelableArrayListExtra("ratings", new ArrayList<>(allRatings));
                startActivityForResult(intent, REQUEST_CODE_RESULT_ACTIVITY);
            }
        });

        mealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> mealItems = new ArrayList<>();
                mealItems.add("Poutine");
                mealItems.add("Salmon");
                mealItems.add("Sushi");

                ArrayAdapter<String> mealAdapter = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_spinner_item, mealItems);

                spinner.setAdapter(mealAdapter);
            }
        });

        saladButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> saladItems = new ArrayList<>();
                saladItems.add("Salad");
                saladItems.add("Chicken");
                saladItems.add("Greek");
                ArrayAdapter<String> saladAdapter = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_spinner_item, saladItems);
                spinner.setAdapter(saladAdapter);
            }
        });
    }
    private void updateImageView(String selectedItem) {
        if (selectedItem.equals("Poutine")) {
            imageView.setImageResource(R.drawable.poutine);
        } else if (selectedItem.equals("Salmon")) {
            imageView.setImageResource(R.drawable.salmon);
        } else if (selectedItem.equals("Sushi")) {
            imageView.setImageResource(R.drawable.sushi);
        } else if (selectedItem.equals("Salad")) {
            imageView.setImageResource(R.drawable.salad);
        } else if (selectedItem.equals("Chicken")) {
            imageView.setImageResource(R.drawable.chicken);
        } else if (selectedItem.equals("Greek")) {
            imageView.setImageResource(R.drawable.greek);
        }
    }
    private void addRating() {
        String selectedItem = spinner.getSelectedItem().toString();
        float ratingValue = ratingBar.getRating();
        MyRating rating = new MyRating(selectedItem, ratingValue);
        allRatings.add(rating);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_RESULT_ACTIVITY && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            nameTextView.setText("Name: " + name);
        }
    }
}