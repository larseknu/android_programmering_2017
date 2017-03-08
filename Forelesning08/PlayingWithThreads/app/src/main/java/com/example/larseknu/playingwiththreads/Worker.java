package com.example.larseknu.playingwiththreads;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Worker {
    Context mContext;
    private static boolean mUseGpsToGetLocation = true;

    public Worker(Context context) {
        mContext = context;
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

    public void saveToFile(Location location, String address, String fileName) {
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
            Log.e("Worker.saveToFile", ex.getMessage());
        }

        addDelay();
    }

    public JSONObject getJSONObjectFromURL(String urlString)  {

        try {
            URL url = new URL(urlString);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            String jsonString = sb.toString();

            addDelay();
            return new JSONObject(jsonString);
        }
        catch (JSONException jsone) {
            Log.e("Worket.getJSON", jsone.getMessage());
        }
        catch (IOException ioe) {
            Log.e("Worker.getJSON", ioe.getMessage());
        }

        addDelay();
        return new JSONObject();

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
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
