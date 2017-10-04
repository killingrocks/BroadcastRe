package com.example.meoryou.broadcastsevice;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by me or you on 10/3/2017.
 */

public class StartedService extends Service {
    private static final String TAG = "NO";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "nBind  :On create Thread Name: " + Thread.currentThread().getName());
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate :On create Thread Name: " + Thread.currentThread().getName());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand : On create Thread Name: " + Thread.currentThread().getName());
        int sleepTime = intent.getIntExtra("sleepTime",1);
      /*  try {
            Thread.sleep(1000*sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/

      new MyAsyncTask().execute(sleepTime);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy :On create Thread Name: " + Thread.currentThread().getName());
        super.onDestroy();
    }
    class MyAsyncTask extends AsyncTask<Integer,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "onPreExecute :On create Thread Name: " + Thread.currentThread().getName());
        }

        // only things that works in background
        @Override
        protected String doInBackground(Integer... param) {
            Log.i(TAG, "doInBackground :On create Thread Name: " + Thread.currentThread().getName());
            int sleepTime =param[0];
            int ctr = 1;
            while(ctr<= sleepTime) {
                publishProgress("counter is now " + ctr);
                try {
                    Thread.sleep(250 * sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctr++;
            }
            return "counter stopped at " + ctr + "seconds";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Log.i(TAG, values[0] +"  onProgressUpdate :" + Thread.currentThread().getName());
            Toast.makeText(StartedService.this,values[0],Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            Log.i(TAG, "onPostExecute :On create Thread Name: " + Thread.currentThread().getName());
            stopSelf();

            Intent  intent = new Intent("service.to.activity");
            intent.putExtra("startServiceResult", str);
            sendBroadcast(intent); // when this is exc it is receive ny the broadcast receiver
        }
    }
}
