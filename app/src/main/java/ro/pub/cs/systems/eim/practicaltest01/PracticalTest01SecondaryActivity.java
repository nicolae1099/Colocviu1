package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    Button okButton, cancelButton;

    TextView displaySum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        displaySum = findViewById(R.id.suma);

        okButton = findViewById(R.id.ok_button);
        cancelButton = findViewById(R.id.cancel_button);

        Intent intent = getIntent();
        int sum = intent.getIntExtra(Constants.SUM, 0);

        displaySum.setText(String.valueOf(sum));

        okButton.setOnClickListener(v -> {
            Intent result = new Intent();
            result.putExtra(Constants.SUM, sum * 2);
            setResult(RESULT_OK, result);
            finish();
        });

        cancelButton.setOnClickListener(v -> {
            Intent result = new Intent();
            setResult(RESULT_CANCELED, result);
            finish();
        });
    }
}