package com.jay.googlelocation.models.googledistance;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 * */
public class Southwest{
  @SerializedName("lng")
  @Expose
  private String lng;
  @SerializedName("lat")
  @Expose
  private String lat;
  public void setLng(String lng){
   this.lng=lng;
  }
  public String getLng(){
   return lng;
  }
  public void setLat(String lat){
   this.lat=lat;
  }
  public String getLat(){
   return lat;
  }
}