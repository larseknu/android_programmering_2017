package no.hiof.larseknu.playingwithservices;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by larseknu on 21/03/2017.
 */

public class WorkerService extends Service {
    private Worker worker;
    private ExecutorService executorService;
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void onCreate() {
        worker = new Worker(this);
        worker.monitorGpsInBackground();
        executorService = Executors.newCachedThreadPool();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogHelper.ProcessAndThreadId("WorkerService.onStartCommand");
        ServiceRunnable runnable = new ServiceRunnable(this, startId);

        executorService.execute(runnable);

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        worker.stopGpsMonitoring();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class ServiceRunnable implements Runnable {
        WorkerService workerService;
        int startId;

        public ServiceRunnable(WorkerService workerService, int startId) {
            this.workerService = workerService;
            this.startId = startId;
        }

        @Override
        public void run() {
            LogHelper.ProcessAndThreadId("WorkerService.ServiceRunnable");

            Location location = worker.getLocation();

            String address = worker.reverseGeocode(location);

            worker.save(location, address, "WorkerService.out");

            DelayedStopRequest stopRequest = new DelayedStopRequest(workerService, startId);

            workerService.scheduledExecutorService.schedule(stopRequest, 10, TimeUnit.SECONDS);
        }
    }

    class DelayedStopRequest implements Runnable {
        WorkerService workerService;
        int startId;

        public DelayedStopRequest(WorkerService workerService, int startId) {
            this.workerService = workerService;
            this.startId = startId;
        }


        @Override
        public void run() {
            LogHelper.ProcessAndThreadId("WorkerService.DelayedStopRequest");

            boolean stopping = workerService.stopSelfResult(startId);

            Log.i("no.hiof.larseknu", "Service with startid: " + startId + " stopping: " + stopping);
        }
    }
}
