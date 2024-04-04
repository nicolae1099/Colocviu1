package ro.pub.cs.systems.eim.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
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

    Button startService, stopService;


    private final MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private static class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        pressMe = findViewById(R.id.press_me_button);
        pressMeToo = findViewById(R.id.press_me_too_button);
        navigateToSecondaryActivity = findViewById(R.id.navigate_to_second_activity);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);

        input1.setText("0");
        input2.setText("0");


        pressMe.setOnClickListener(v -> {
            input1.setText(String.valueOf(Integer.parseInt(input1.getText().toString()) + 1));
        });

        pressMeToo.setOnClickListener(v -> {
            input2.setText(String.valueOf(Integer.parseInt(input2.getText().toString()) + 1));
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
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

        startService = findViewById(R.id.startService);
        startService.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PracticalTest01Service.class);
            intent.putExtra(Constants.INPUT1, Integer.valueOf(input1.getText().toString()));
            intent.putExtra(Constants.INPUT2, Integer.valueOf(input1.getText().toString()));
            startForegroundService(intent);
        });


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.INPUT1, input1.getText().toString());
        outState.putString(Constants.INPUT2, input2.getText().toString());
    }



    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_STRING);
        registerReceiver(messageBroadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageBroadcastReceiver);
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