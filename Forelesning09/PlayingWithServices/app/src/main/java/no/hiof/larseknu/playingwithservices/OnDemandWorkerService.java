package no.hiof.larseknu.playingwithservices;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.location.Location;

/**
 * Created by larseknu on 21/03/2017.
 */

public class OnDemandWorkerService extends IntentService {
    public static String FILE_NAME = "fileName";

    public OnDemandWorkerService() {
        super("OnDemandWorkerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogHelper.ProcessAndThreadId("OnDemandWorkerService.onHandleIntent");

        String fileName = intent.getStringExtra(FILE_NAME);

        if (fileName == null)
            fileName = "ServiceOutputFile.out";

        Worker worker = new Worker(this);

        Location location = worker.getLocation();

        String address = worker.reverseGeocode(location);

        worker.save(location, address, fileName);
    }
}
