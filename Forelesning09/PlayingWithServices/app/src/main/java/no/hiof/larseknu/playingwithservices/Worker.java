package no.hiof.larseknu.playingwithservices;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.HandlerThread;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Worker {
    Context mContext;
	private final boolean mUseGpsToGetLocation = true;


    HandlerThread mGpsHandlerThread;
    LocationListener mLocationListener;
    LocationManager mLocationManager;

    public Worker(Context context) {
        mContext = context;
    }

    public void monitorGpsInBackground(){
        if(mUseGpsToGetLocation) {
            mGpsHandlerThread = new HandlerThread("GPSThread");
            mGpsHandlerThread.start();

            mLocationListener = new NoOpLocationListener();

            mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener, mGpsHandlerThread.getLooper());
        }
    }

    public void stopGpsMonitoring(){
        if(mLocationManager != null)
            mLocationManager.removeUpdates(mLocationListener);

        if(mGpsHandlerThread != null)
            mGpsHandlerThread.quit();
    }


    public Location getLocation() {
        Location lastLocation = null;

        if (mUseGpsToGetLocation)
        {
            LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        if(lastLocation == null)
            lastLocation = createLocationManually();

        addDelay();
        return lastLocation;
    }

    public String reverseGeocode(Location location) {
        String addressDescription = null;

        try  {
            Geocoder geocoder = new Geocoder(mContext);
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
            if(!addressList.isEmpty())
            {
                Address firstAddress = addressList.get(0);

                StringBuilder addressBuilder = new StringBuilder();
                for (int i = 0; i <= firstAddress.getMaxAddressLineIndex(); i++)
                {
                    if(i != 0)
                        addressBuilder.append(", ");
                    addressBuilder.append(firstAddress.getAddressLine(i));
                }
                addressDescription = addressBuilder.toString();
            }
            else {
                return "B.R.A. veien 4, 1757 Halden";
            }
        }
        catch (IOException ex) {
            Log.e("Worker.reverseGeocode", "IOException Error");
        }
        catch (Exception ex) {
            Log.e("Worker.reverseGeocode", "Error");
        }

        addDelay();
        return addressDescription;
    }

    public void save(Location location, String address, String fileName) {
        try {
            File targetDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if(!targetDir.exists())
                targetDir.mkdirs();

            File outFile = new File(targetDir, fileName);
            FileWriter fileWriter = new FileWriter(outFile, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            String outLine = String.format(Locale.getDefault(), "%s - %f/%f\n",
                    new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(location.getTime()),
                    location.getLatitude(),
                    location.getLongitude());
            writer.write(outLine);
            writer.write(address + "\n");

            writer.flush();
            writer.close();
            fileWriter.close();
        }
        catch (Exception ex){
            Log.e("Worker.save", "Error");
        }

        addDelay();
    }

    private Location createLocationManually() {
        Location lastLocation = new Location("Hiof");
        Date now = new Date();
        lastLocation.setTime(now.getTime());
        lastLocation.setLatitude(59.128229);
        lastLocation.setLongitude(11.352860);

        return lastLocation;
    }

    private void addDelay() {
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class NoOpLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
        }

        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        public void onProviderEnabled(String s) {
        }

        public void onProviderDisabled(String s) {
        }
    }
}
