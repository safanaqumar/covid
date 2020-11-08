package com.example.covid.map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.covid.R;
import com.example.covid.retro.APIService;
import com.example.covid.retro.RetrofitClientInstanceGoogleServices;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Locale;


public class SelectLocationOnMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Marker marker;
    //    private GPSTracker gps;
    private double curlat;
    private double curlng;
    private FusedLocationProviderClient fusedLocationClient;
    private String cityName = "";
    private Button bSelectLocation;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location_on_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        bSelectLocation = findViewById(R.id.bSelectLocation);
        progressBar = findViewById(R.id.progressBar);

        // https://stackoverflow.com/questions/23765372/google-maps-android-get-longitude-latitude-by-moving-a-marker-in-the-map
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        123);
            }
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            curlat = location.getLatitude();
                            curlng = location.getLongitude();
                            getCityNameFromLocation(curlat, curlng);
                        }
//                        curlat=24.9344795;
//                        curlng=67.0301765;
                    }
                });
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.GOOGLE_APIKEY));
        }

    }

    private void getCityNameFromLocation(double curlat, double curlng) {
        bSelectLocation.setEnabled(true);
//        getAddressFromGeocoderWebService();
    }

    private void getAddressFromGeocoderWebService() {
        cityName = "";
        progressBar.setVisibility(View.VISIBLE);
        APIService apiService = RetrofitClientInstanceGoogleServices.getRetrofitInstance().create(APIService.class);
        Call<JsonElement> call = apiService.getAddressFromLocation(curlat + "," + curlng, getString(R.string.google_places_key_for_web));

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    JsonArray results = response.body().getAsJsonObject().get("results").getAsJsonArray();
                    if (results.size() > 0) {
                        JsonArray addressComponents = results.get(0).getAsJsonObject().get("address_components").getAsJsonArray();

                        for (int i = 0; i < addressComponents.size(); i++) {
                            JsonPrimitive type = new Gson().fromJson("locality", JsonPrimitive.class);
                            if (addressComponents.get(i).getAsJsonObject().get("types").getAsJsonArray().contains(type)) {
                                cityName = addressComponents.get(i).getAsJsonObject().get("long_name").getAsString();
                                break;
                            }
                        }

                        if (cityName.equals("")) {
                            for (int i = 0; i < addressComponents.size(); i++) {
                                JsonPrimitive type = new Gson().fromJson("administrative_area_level_2", JsonPrimitive.class);
                                if (addressComponents.get(i).getAsJsonObject().get("types").getAsJsonArray().contains(type)) {
                                    cityName = addressComponents.get(i).getAsJsonObject().get("long_name").getAsString();
                                    break;
                                }
                            }
                        }
                    } else {
                        Toast.makeText(SelectLocationOnMapActivity.this, "No city name found for the selected location", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SelectLocationOnMapActivity.this, "Failed to get city name", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                bSelectLocation.setEnabled(true);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(SelectLocationOnMapActivity.this, "Failed to get city name", Toast.LENGTH_SHORT).show();
                bSelectLocation.setEnabled(true);
                progressBar.setVisibility(View.GONE);
            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng currentpos = new LatLng(0.0, 0.0);
        marker = mMap.addMarker(new MarkerOptions().position(currentpos)
                .title("Draggable Marker")
                .snippet("Long press and move the marker if needed.")
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                if (curlat != 0.0 & curlng != 0.0) {
                    resetMarker();
                    Toast.makeText(SelectLocationOnMapActivity.this, "setOnMapLoadedCallback", Toast.LENGTH_SHORT).show();
                }
                initSearchLocation();
            }
        });


        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                Toast.makeText(SelectLocationOnMapActivity.this, "==camera idle==" + mMap.getCameraPosition().target + "", Toast.LENGTH_SHORT).show();
                LatLng latLng = new LatLng(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
                marker.setPosition(latLng);

            }

        });

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                LatLng midLatLng = mMap.getCameraPosition().target;
                if (marker != null) marker.setPosition(midLatLng);

            }
        });

        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int reason) {
                marker.setDraggable(true);
                if (reason == REASON_GESTURE) {
                    Toast.makeText(getApplicationContext(), "The user gestured on the map.",
                            Toast.LENGTH_SHORT).show();
                } else if (reason == REASON_API_ANIMATION) {
                    Toast.makeText(getApplicationContext(), "The user tapped something on the map.",
                            Toast.LENGTH_SHORT).show();
                } else if (reason == REASON_DEVELOPER_ANIMATION) {
                    Toast.makeText(getApplicationContext(), "The app moved the camera.",
                            Toast.LENGTH_SHORT).show();
                }
            }


        });

    }

    private void resetMarker() {
        mMap.clear();
        LatLng currentpos = new LatLng(curlat, curlng);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(curlat, curlng), 16));

        marker = mMap.addMarker(new MarkerOptions().position(currentpos)
                .title("Draggable Marker")
                .snippet("Long press and move the marker if needed.")
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));

        getCityNameFromLocation(curlat, curlng);


        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub
//                Log.d("Marker", "Dragging");
            }

            @Override
            public void onMarkerDragEnd(Marker arg0) {
//                         TODO Auto-generated method stub
                LatLng markerLocation = marker.getPosition();
                curlat = markerLocation.latitude;
                curlng = markerLocation.longitude;
//                        Toast.makeText(SelectLocationOnMapActivity.this, markerLocation.toString(), Toast.LENGTH_LONG).show();

                getCityNameFromLocation(curlat, curlng);
//                Log.d("Marker", "finished");
            }

            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
//                Log.d("Marker", "Started");

            }
        });
    }

    private void initSearchLocation() {
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

// Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
//                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
//                Toast.makeText(SelectLocationOnMapActivity.this, "" + place.getLatLng().toString(), Toast.LENGTH_SHORT).show();
                curlat = place.getLatLng().latitude;
                curlng = place.getLatLng().longitude;
                resetMarker();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
//                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }



    public void onLocationSelected(View view) {
        if (marker != null) {
            Intent data = new Intent();
            data.putExtra("lat", curlat);
            data.putExtra("lng", curlng);
            data.putExtra("city", cityName);
            setResult(RESULT_OK, data);
            finish();
        }
    }
}