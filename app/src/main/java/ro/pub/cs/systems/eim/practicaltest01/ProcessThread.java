package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Context;
import android.content.Intent;

public class ProcessThread extends Thread{

    private int sum;
    private int medieAritmetica = 0;
    private int medieGeometrica = 0;
    private boolean isRunning = true;
    private Context context;

    public ProcessThread(Context context, int input1, int input2) {
        this.context = context;
        this.sum = input1 + input2;
        this.medieAritmetica = (input1 + input2) / 2;
        this.medieGeometrica = (int) Math.sqrt(input1 * input2);
    }

    @Override
    public void run() {
        while (isRunning) {
            sleep();
            sendMessage();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(Constants.SLEEP_TIME);
        } catch (InterruptedException interruptedException) {

        }
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_STRING);

        String broadcast = "Suma: " + sum + " Medie Aritmetica: " + medieAritmetica + " Medie Geometrica: " + medieGeometrica;
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA, broadcast);
        context.sendBroadcast(intent);
    }

    public void stopThread() {
        isRunning = false;
    }
}
