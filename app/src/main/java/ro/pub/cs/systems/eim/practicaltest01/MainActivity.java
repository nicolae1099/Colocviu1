package ro.pub.cs.systems.eim.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button pressMe, pressMeToo, navigateToSecondaryActivity;
    EditText input1, input2;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private final IntentFilter intentFilter = new IntentFilter();

    private final MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private static class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, Objects.requireNonNull(intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA)));
        }
    }

    int leftNumber = 0;
    int rightNumber = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pressMe = findViewById(R.id.press_me_button);
        pressMeToo = findViewById(R.id.press_me_too_button);
        navigateToSecondaryActivity = findViewById(R.id.navigate_to_second_activity);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);

        input1.setText("0");
        input2.setText("0");


        pressMe.setOnClickListener(v -> {
            leftNumber++;
            input1.setText(String.valueOf(leftNumber));
            startServiceIfConditionIsMet(leftNumber, rightNumber);
        });

        pressMeToo.setOnClickListener(v -> {
            rightNumber++;
            input2.setText(String.valueOf(rightNumber));

            startServiceIfConditionIsMet(leftNumber, rightNumber);
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() == null) {
                    return;
                }
                int sum = Objects.requireNonNull(result.getData().getExtras()).getInt(Constants.SUM);
                Toast.makeText(this, "The activity returned with OK  " + sum, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "The activity returned with CANCEL " , Toast.LENGTH_LONG).show();
            }
        });

        navigateToSecondaryActivity.setOnClickListener(v -> {

            Intent intent = new Intent(this, PracticalTest01SecondaryActivity.class);

            int sum = Integer.parseInt(input1.getText().toString()) + Integer.parseInt(input2.getText().toString());
            intent.putExtra(Constants.SUM, sum);

            activityResultLauncher.launch(intent);
        });

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }

    }

    private void startServiceIfConditionIsMet(int leftNumber, int rightNumber) {
        if (leftNumber + rightNumber > Constants.NUMBER_OF_CLICKS_THRESHOLD) {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
            intent.putExtra(Constants.INPUT1, leftNumber);
            intent.putExtra(Constants.INPUT2, rightNumber);
            getApplicationContext().startService(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(messageBroadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED);
        } else {
            registerReceiver(messageBroadcastReceiver, intentFilter);
        }
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);

        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.INPUT1, input1.getText().toString());
        outState.putString(Constants.INPUT2, input2.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey(Constants.INPUT1)) {
            input1.setText(savedInstanceState.getString(Constants.INPUT1));
        } else {
            input1.setText("0");
        }

        if (savedInstanceState.containsKey(Constants.INPUT2)) {
            input2.setText(savedInstanceState.getString(Constants.INPUT2));
        } else {
            input2.setText("0");
        }
    }
}