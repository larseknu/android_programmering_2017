package com.example.larseknu.playingwiththreads;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    private TextView statusText;
    private String tempText;
    Thread workerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = (TextView) findViewById(R.id.status_text);

        statusText.setText("Ready");

        StrictMode.enableDefaults();
    }

    public void doWork(View view) {
        //doWork();
        AsyncTaskWorker asyncTaskWorker = new AsyncTaskWorker();
        asyncTaskWorker.execute(statusText);
    }


    public void doWork() {
       workerThread = new Thread(new Runnable() {public void run() {
                Worker worker = new Worker(MainActivity.this);
                updateUI("Starting");

                JSONObject jsonObject = worker.getJSONObjectFromURL("http://www.it-stud.hiof.no/android/data/randomData.php");
                updateUI("Retrieved JSON");

                Location location = worker.getLocation();
                updateUI("Retrieved Location");

                String address = worker.reverseGeocode(location);
                updateUI("Retrieved Address");

                worker.saveToFile(location, address, "FancyFileName.out");
                updateUI("Done");
            }
        });
        workerThread.start();
    }

    public class AsyncTaskWorker extends AsyncTask<TextView, String, Boolean> {
        private TextView textView;

        @Override
        protected Boolean doInBackground(TextView... textViews) {
            boolean returnValue = false;
            textView = textViews[0];

            if(textViews.length > 0) {
                Worker worker = new Worker(MainActivity.this);
                publishProgress("Starting");

                JSONObject jsonObject = worker.getJSONObjectFromURL("http://www.it-stud.hiof.no/android/data/randomData.php");
                publishProgress("Retrieved JSON");

                Location location = worker.getLocation();
                publishProgress("Retrieved Location");

                String address = worker.reverseGeocode(location);
                publishProgress("Retrieved Address");

                worker.saveToFile(location, address, "FancyFileName.out");
                publishProgress("Done");
                returnValue = true;
            }
            return returnValue;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            textView.setText(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result)
                textView.setText("Done");
            else
                textView.setText("Something failed");
        }


    }


    public void updateUI(String message) {
        tempText = message;
        statusText.post(new Runnable() {
            public void run() {
                statusText.setText(tempText);
            }
        });
    }



}
