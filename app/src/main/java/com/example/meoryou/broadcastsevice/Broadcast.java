package com.example.meoryou.broadcastsevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Broadcast extends AppCompatActivity {
    private static final String TAG = "NO";
    TextView text, text2 ;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate  :On create Thread Name: " + Thread.currentThread().getName());
        setContentView(R.layout.activity_broadcast);
        text = (TextView) findViewById(R.id.textView1);
        text2 = (TextView) findViewById(R.id.textView);
    //    btn = (Button) findViewById(R.id.button1);

    }
public void onClickbutton(View v){
    Log.i(TAG, "onClickbutton  :On create Thread Name: " + Thread.currentThread().getName());
       Intent intent = new Intent(Broadcast.this, StartedService.class);
        intent.putExtra("sleepTime", 10);
        startService(intent);
}

public void onclickstop(View v){
    Log.i(TAG, "nonclcickstop :On create Thread Name: " + Thread.currentThread().getName());
    Intent intent = new Intent(Broadcast.this, StartedService.class);
    stopService(intent);

}
    @Override
    protected void onResume() {
        Log.i(TAG, "onResume  :On create Thread Name: " + Thread.currentThread().getName());
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("service.to.activity");
        registerReceiver(myStartedServiceReceiver, intentFilter);
    }

    private BroadcastReceiver myStartedServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "BroadcastReciever  :On create Thread Name: " + Thread.currentThread().getName());
            // rec intent
            String result = intent.getStringExtra("startServiceResult");
            text.setText(result);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause  :On create Thread Name: " + Thread.currentThread().getName());
        unregisterReceiver(myStartedServiceReceiver);
    }
}
