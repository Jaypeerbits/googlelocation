package com.jay.googlelocation.models.googledistance;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 * */
public class Geocoded_waypoints{
  @SerializedName("geocoder_status")
  @Expose
  private String geocoder_status;
  @SerializedName("place_id")
  @Expose
  private String place_id;
  public void setGeocoder_status(String geocoder_status){
   this.geocoder_status=geocoder_status;
  }
  public String getGeocoder_status(){
   return geocoder_status;
  }
  public void setPlace_id(String place_id){
   this.place_id=place_id;
  }
  public String getPlace_id(){
   return place_id;
  }
}