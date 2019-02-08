package com.jay.googlelocation.Globle;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.jay.googlelocation.retrofit.ApisInterface;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Retrofit;

public class GoogleDistanceAPI {

    public interface GoogleDistance {
        public void onDistanceFetch(String distance);
        public void onTimingFetch(String time);
        public void getRoutes(ArrayList<LatLng> route);
    }

//    public void getDirectionFromDirectionApiServer(double originLat,double originLng , double destinationLat , double destinationLng, String key,final GoogleDistance googleDistanceInterface) {
//        String origin = String.valueOf(originLat) + "," + String.valueOf(originLng);
//        String destination = String.valueOf(destinationLat) + "," + String.valueOf(destinationLng);
//
//        final GoogleDistance googleDistance= googleDistanceInterface;
//        ApisInterface apiInterface = APIClient.getClient().create(ApisInterface.class);
//
//
//        /**
//         GET List Resources
//         **/
//        Call<ResponseBody> call = apiInterface.APICall(origin, destination, key);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                try {
//                    String data = response.body().string();
//                    JSONObject jsonObject = new JSONObject(data);
//                    if (!jsonObject.optString("status").equalsIgnoreCase("REQUEST_DENIED")) {
//                        GsonBuilder gsonBuilder = new GsonBuilder();
//                        Gson gson = gsonBuilder.create();
//                        Example example = gson.fromJson(data, Example.class);
//                        if(googleDistance !=null){
//                            googleDistance.onDistanceFetch(example.getRoutes().get(0).getLegs().get(0).getDistance().getText());
//                            googleDistance.onTimingFetch(example.getRoutes().get(0).getLegs().get(0).getDuration().getText());
//                            googleDistance.getRoutes(example.getRoutes());
//                        }
//
//                    } else {
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                call.cancel();
//            }
//        });
//
//    }


    public void getDirectionDetails(final double lat, final double lng, final double restLat, final double restLong, String api, Context context, final GoogleDistance googleDistanceInterface) {
        final GoogleDistance googleDistance= googleDistanceInterface;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/directions/")
                .build();
        ApisInterface service = retrofit.create(ApisInterface.class);
        try {
            service.getMethod(generateDirectionsUrl(new LatLng(lat, lng), new LatLng(restLat, restLong),api))
                    .enqueue(new JSONCallback(context) {
                        @Override
                        protected void onFailed(int statusCode, String message) {
                        }

                        @SuppressLint("StaticFieldLeak")
                        @Override
                        protected void onSuccess(int statusCode, JSONObject jsonObject) {
                            new DirectionsAsync(jsonObject) {
                                @Override
                                public void onGetRoute(ArrayList<LatLng> route, String distance, String duration) {
                                    try {

                                        googleDistance.onDistanceFetch(distance);
                                        googleDistance.onTimingFetch(duration);
                                        googleDistance.getRoutes(route);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.execute();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String generateDirectionsUrl(LatLng origin, LatLng dest,String api) {
        String url = "json?" + "origin=" + origin.latitude + "," + origin.longitude + "&" +
                "destination=" + dest.latitude + "," + dest.longitude + "&" +
                "sensor=false" + "&key=" +
                api;
        return url;
    }
}
