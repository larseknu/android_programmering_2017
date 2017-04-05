package no.hiof.larseknu.playingwithgooglemaps;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Property;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NONE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN;

public class MapsActivity extends Activity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, AdapterView.OnItemSelectedListener {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 1;

    private GoogleMap map;

    private int kittyCounter = 0;
    private ArrayList<Marker> kittyMarkers;
    private GMapV2Direction mapDirection;

    private static final LatLng HIOF = new LatLng(59.128889, 11.352814);
    private static final LatLng FREDRIKSTAD = new LatLng(59.21047628, 10.93994737);
    private LatLng myPosition = new LatLng(59.128889, 11.352814);

    private KittenLocation kittenLocation;
    private ArrayList<KittenLocation> kittenLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        kittyMarkers = new ArrayList<>();
        mapDirection = new GMapV2Direction();


        if (savedInstanceState != null) {
            // We get our KittenLocation from the savedInstance bundle
            kittenLocation = savedInstanceState.getParcelable("found_kitten");
            kittenLocations = savedInstanceState.getParcelableArrayList("found_all_kittens");
        }

        Spinner spinner = (Spinner) findViewById(R.id.layers_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.layers_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // We want to save one of the kittens if one exists
        if (!kittyMarkers.isEmpty()) {
            // Gets the first kitten
            Marker kittyMarker = kittyMarkers.get(0);
            // Instantiate KittenLocation, which is a parcelable class, based on data from the marker
            KittenLocation kittenLocation = new KittenLocation(kittyMarker.getTitle(), new LatLng(kittyMarker.getPosition().latitude, kittyMarker.getPosition().longitude));
            // Put the KittenLocation data into the bundle
            outState.putParcelable("found_kitten", kittenLocation);

            ArrayList<KittenLocation> kittenLocations = new ArrayList<>();

            for (Marker marker:
                 kittyMarkers) {
                kittenLocations.add(new KittenLocation(marker.getTitle(), new LatLng(marker.getPosition().latitude, marker.getPosition().longitude)));
            }

            outState.putParcelableArrayList("found_all_kittens", kittenLocations);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Middle of the world"));
        map.addMarker(new MarkerOptions().position(HIOF).title("HiØ"));

        //map.moveCamera(CameraUpdateFactory.newLatLng(HIOF));
        //map.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(HIOF, 15, 0, 0)));
        //map.animateCamera(CameraUpdateFactory.newLatLng(FREDRIKSTAD), 2000, null);

        map.setOnMapLongClickListener(this);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{ android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
        }
        else
            map.setMyLocationEnabled(true);


        setUiSettings();

        if (kittenLocations != null) {
            for (KittenLocation kitten:
                 kittenLocations) {
                addKittenMarker(kitten.getLatLng(), kitten.getName());
            }
        }

        if (kittenLocation != null) {
            // We add the kitten marker to the map
            addKittenMarker(kittenLocation.getLatLng(), "Found Kitten");
        }
        else {
            // Move the camera to the "starting position" at Østfold University College, with 13 in zoom and no tilt and bearing north
            map.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(HIOF, 13, 0, 0)));
            // Animate the camera from the position to Fredrikstad Kino over a duration of 2 seconds
            map.animateCamera(CameraUpdateFactory.newLatLng(FREDRIKSTAD), 2000, null);

            new DrawRoute().execute();
        }

    }

    private void setUiSettings() {
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setCompassEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMapToolbarEnabled(false);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        addKittenMarker(latLng, "Kitten Attack");
    }

    private void addKittenMarker(LatLng kittenLocation, String snippet) {
        // Get a kittenIcon from the drawable resources. Must be named "kitten_0X", where X is a number.
        BitmapDescriptor kittenIcon = BitmapDescriptorFactory.fromResource(getResources().getIdentifier("kitten_0" + (kittyCounter%3+1), "drawable", this.getPackageName()));
        // One more kitten is to be added
        kittyCounter++;
        // Create all the marker options for the kitty marker
        MarkerOptions markerOptions = new    MarkerOptions().position(kittenLocation)
                .title("Mittens the " + kittyCounter + ".")
                .snippet(snippet)
                .icon(kittenIcon);
        // Add the marker to the map
        Marker kittyMarker = map.addMarker(markerOptions);
        // Add the marker to the kittyMarkersArray
        kittyMarkers.add(kittyMarker);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    // Show the user that we actually need this permission
                    Toast.makeText(this, "You have not given all the required permissions. The application may not function as intended.", Toast.LENGTH_LONG).show();

                }
                return;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String layerType = (String) parent.getItemAtPosition(position);

        if (map != null) {
            if (layerType.equals(getString(R.string.hybrid))) {
                map.setMapType(MAP_TYPE_HYBRID);

            } else if (layerType.equals(getString(R.string.satellite))) {
                map.setMapType(MAP_TYPE_SATELLITE);

            } else if (layerType.equals(getString(R.string.terrain))) {
                map.setMapType(MAP_TYPE_TERRAIN);

            } else if (layerType.equals(getString(R.string.none))) {
                map.setMapType(MAP_TYPE_NONE);

            } else {
                map.setMapType(MAP_TYPE_NORMAL);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class DrawRoute extends AsyncTask<Void, Void, PolylineOptions> {
        @Override
        protected PolylineOptions doInBackground(Void... params) {
            Document doc = mapDirection.getDocument(myPosition, FREDRIKSTAD, GMapV2Direction.MODE_DRIVING);

            ArrayList<LatLng> directionPoint = mapDirection.getDirection(doc);
            PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.BLUE);

            for (int i = 0; i < directionPoint.size(); i++) {
                rectLine.add(directionPoint.get(i));
            }

            return rectLine;
        }

        @Override
        protected void onPostExecute(PolylineOptions rectLine) {
            map.addPolyline(rectLine);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.kitty_attack:
                for (Marker kittyMarker : kittyMarkers)
                    animateMarker(kittyMarker, FREDRIKSTAD);
                break;
            case R.id.remove_kittens:
                removeAllKittyMarkers();
                break;
            case R.id.draw_route:
                LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                criteria.setPowerRequirement(Criteria.POWER_HIGH);

                locationManager.requestSingleUpdate(criteria, new NoOpLocationListener(), null);

                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

                myPosition = new LatLng(location.getLatitude(), location.getLongitude());
                new DrawRoute().execute();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    static void animateMarker(Marker marker, LatLng finalPosition) {
        TypeEvaluator<LatLng> typeEvaluator = new TypeEvaluator<LatLng>() {
            @Override
            public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {
                double lat = (endValue.latitude - startValue.latitude) * fraction + startValue.latitude;
                double lng = (endValue.longitude - startValue.longitude) * fraction + startValue.longitude;
                return new LatLng(lat, lng);
            }
        };

        Property<Marker, LatLng> property = Property.of(Marker.class, LatLng.class, "position");
        ObjectAnimator animator = ObjectAnimator.ofObject(marker, property, typeEvaluator, finalPosition);
        animator.setDuration(1000);
        animator.start();
    }

    private void removeAllKittyMarkers() {
        for (Marker kittyMarker : kittyMarkers) {
            kittyMarker.remove();
        }
        kittyMarkers.clear();
        kittyCounter = 0;
    }


    class NoOpLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
