package com.jay.googlelocation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.jay.googlelocation.Globle.Common;
import com.jay.googlelocation.Globle.Constants;


/**
 * Created by lg on 8/25/2016.
 */
public class LocationFetcher extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private final int MY_PERMISSIONS_REQUEST_LOCATION = 0;
    private final int LOCATION_DIALOG_REQUEST_CODE = 1;
    private final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    double userLat = 0.0, userLng = 0.0;
    Context mContext;
    private String[] permissions;
    private Location mLastLocation;
    public LocationRequest mLocationRequest;
    private LocationSettingsRequest.Builder locationSettingBuilder;
    public GoogleApiClient mGoogleApiClient;
    private int type;
    private final int REQUEST_PLACE_PICKER = 0;

    public static String LATITUDE="curr_latitude";
    public static String LONGITUDE="curr_longitude";
    @Override
    public void onConnected(Bundle bundle) {
        int permissionCheck = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION);
        switch (permissionCheck) {
            case PackageManager.PERMISSION_GRANTED:
                createLocationRequest();
                locationSettingBuilder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest).setAlwaysShow(true);
                showLocationEnabledDialog();
                break;
            case PackageManager.PERMISSION_DENIED:
                if (ActivityCompat.shouldShowRequestPermissionRationale(LocationFetcher.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //Toast.makeText(this,"Dude,You need to Grant This permission.",Toast.LENGTH_LONG).show();
                }
                ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST_LOCATION);
                break;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

//        LinearLayout linearLayout = new LinearLayout(LocationFetcher.this);
//        setContentView(linearLayout);
        init();
        buildGoogleApiClient();
    }


    private void init() {
        type = getIntent().getIntExtra(Constants.TYPE, 0);
        mContext = getApplicationContext();

        permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};

    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void showLocationEnabledDialog() {
        PendingResult<LocationSettingsResult> pendingResult = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, locationSettingBuilder.build());
        pendingResult.setResultCallback(new ResultCallback<LocationSettingsResult>() {

            @Override
            public void onResult(LocationSettingsResult arg0) {
                // TODO Auto-generated method stub
                Status status = arg0.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(LocationFetcher.this, LOCATION_DIALOG_REQUEST_CODE);
                        } catch (IntentSender.SendIntentException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private void getLocation() {
        if (type == Constants.LOCATION_FETCH) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                userLat = mLastLocation.getLatitude();
                userLng = mLastLocation.getLongitude();

                rplyLocDetails(Constants.GRANTED);
            }
            else {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        userLat = location.getLatitude();
                        userLng = location.getLongitude();
                        rplyLocDetails(Constants.GRANTED);
                        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                        finish();
                    }
                });
            }
            Intent intent_locationService = new Intent(this,LocationService.class);
            startService(intent_locationService);
        }else if(type==Constants.LOCATION_PICK){
            try {
                PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                Intent intent = intentBuilder.build(this);
                startActivityForResult(intent, REQUEST_PLACE_PICKER);
            }catch(Exception e){}
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (Common.checkInternetConnection(this)) {
                        createLocationRequest();
                        locationSettingBuilder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest).setAlwaysShow(true);
                        showLocationEnabledDialog();
                    } else {
                        Common.alertDialog(LocationFetcher.this, Common.INTERNET_NOT_FOUND_MSG);
                    }
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    rplyLocDetails(Constants.DENIED);
                    ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST_LOCATION);
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LOCATION_DIALOG_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    getLocation();
                } else {
                    rplyLocDetails(Constants.GRANTED);
                }
                break;
            case REQUEST_PLACE_PICKER:
                if (resultCode == Activity.RESULT_OK) {
                    final Place place = PlacePicker.getPlace(data, this);
                    if(place!=null) {
                        LatLng latLng = place.getLatLng();
                        userLat = latLng.latitude;
                        userLng = latLng.longitude;
                    }
                }
                rplyLocDetails(Constants.GRANTED);
                break;
        }
    }
    private void rplyLocDetails(int permission){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Constants.PERMISSION,permission);
        resultIntent.putExtra(Constants.LATITUDE, userLat);
        resultIntent.putExtra(Constants.LONGITUDE, userLng);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
