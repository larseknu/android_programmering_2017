package no.hiof.larseknu.playingwithservices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogHelper.ProcessAndThreadId("MainActivity.onCreate");
    }

    public void startOnDemandService(View view) {
        Intent intent = new Intent(this, OnDemandWorkerService.class);
        intent.putExtra(OnDemandWorkerService.FILE_NAME, "ActivityFileName.out");
        startService(intent);

        Toast.makeText(this, "Work has started", Toast.LENGTH_LONG).show();
    }

    public void startWorkerService(View view) {
        Intent intent = new Intent(this, WorkerService.class);
        startService(intent);

        Toast.makeText(this, "Work has started", Toast.LENGTH_LONG).show();
    }
}
