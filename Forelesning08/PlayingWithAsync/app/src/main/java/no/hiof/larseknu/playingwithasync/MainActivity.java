package no.hiof.larseknu.playingwithasync;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends Activity {

    TextView textView;
    Thread workerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = (TextView) findViewById(R.id.header);

    }

    public void logIn(View view) {
        doWork();
    }


    InputStream inputStream;

    public void doWork() {
        workerThread = new Thread(new Runnable() {
            public void run() {

                try {
                    URL url = new URL("http://www.it-stud.hiof.no/android/data/randomData.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setRequestMethod("GET");


                    conn.connect();

                    int status = conn.getResponseCode();

                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    JSONObject jsonObject = new JSONObject(sb.toString());

                    updateUI(jsonObject.getString("title"));


                }
                catch (Exception e) {
                    Log.e("Authentication", e.toString());
                }
            }
        });
        workerThread.start();
    }

    // Get a handler that can be used to post to the main thread

    String tempMessage = "hello";

    public void updateUI(String message) {
        tempMessage = message;

        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(tempMessage);
            }
        });

    }


}
