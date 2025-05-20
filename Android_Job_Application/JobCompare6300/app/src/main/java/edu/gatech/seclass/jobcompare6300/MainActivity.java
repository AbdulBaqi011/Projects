package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    Button currentJobButton = (Button) findViewById(R.id.currentjobbuttonID);
    Button comparisonSettingsButton = (Button) findViewById(R.id.comparisonSettingsButtonID);
    Button compareJobsButton = (Button) findViewById(R.id.compareJobsButtonID);
    Button jobOffersButton = (Button) findViewById(R.id.jobOffersButtonID);

    // Set an OnClickListener on the button to handle clicks

        //set button to navigate to the current job details xml
        currentJobButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Create an Intent to start CurrentJobDetailsActivity
            Intent intent = new Intent(MainActivity.this, CurrentJobDetailsActivity.class);
            startActivity(intent);
        }
    });

        //set button to navigate to the Comparison Settings xml
        comparisonSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start CurrentJobDetailsActivity
                Intent intent = new Intent(MainActivity.this, ComparisonSettingsActivity.class);
                startActivity(intent);
            }
        });

        // set button to navigate to the Job Offers xml
        jobOffersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start CurrentJobDetailsActivity
                Intent intent = new Intent(MainActivity.this, JobOffersActivity.class);
                startActivity(intent);
        }
});
        compareJobsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JobTableActivity.class);
                startActivity(intent);
            }
        });

    }
}