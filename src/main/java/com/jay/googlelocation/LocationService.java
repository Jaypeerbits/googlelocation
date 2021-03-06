package com.jay.googlelocation;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.media.AudioAttributes;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.jay.googlelocation.Globle.Constants;

import static androidx.core.app.NotificationCompat.PRIORITY_MIN;


/**
 * Created by lg on 5/9/2017.
 */

public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener {
    private final long UPDATE_INTERVAL_IN_MILLISECONDS = 2000;
    private final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    public LocationRequest mLocationRequest;
    private LocationSettingsRequest.Builder locationSettingBuilder;
    public GoogleApiClient mGoogleApiClient;

    public interface InterfaceLocationUpdates{
        void getLocation(double latitude,double longitude);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        startServiceOreoCondition();
        buildGoogleApiClient();
        createLocationRequest();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
//    @Override
//    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
//        Log.e("onStartCommand","1");
//        return START_STICKY;
//    }

    private void sendUpdate(double latitude,double longitude){
        Intent intent = new Intent(Constants.LOCATION_BROADCAST_KEY);
        intent.putExtra(Constants.LATITUDE,latitude);
        intent.putExtra(Constants.LONGITUDE,longitude);
        getApplicationContext().sendBroadcast(intent);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null){
            sendUpdate(location.getLatitude(),location.getLongitude());
        }
    }
    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext()).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            stopSelf();
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void startServiceOreoCondition(){
        if (Build.VERSION.SDK_INT >= 26) {


            String CHANNEL_ID = "my_service";
            String CHANNEL_NAME = "My Background Service";

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .build();
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(notificationChannel);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
            notificationBuilder
                    .setSmallIcon(getNotificationIcon())
                    .setLargeIcon(notificationIcon(R.mipmap.ic_launcher))
                    .setContentTitle(getString(R.string.app_name))
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000})
                    .setLights(Color.RED, 3000, 3000)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .setPriority(PRIORITY_MIN);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setCategory(Notification.CATEGORY_SERVICE).setSmallIcon(R.mipmap.ic_launcher).setPriority(PRIORITY_MIN).build();

            startForeground(101, notification);
        }
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.mipmap.ic_rider_top : R.mipmap.ic_launcher;
    }
    private Bitmap notificationIcon(int ic_notification_rider) {
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), ic_notification_rider);
        return largeIcon;
    }
}
