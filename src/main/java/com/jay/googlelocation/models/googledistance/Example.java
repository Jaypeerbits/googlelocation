package com.jay.googlelocation.models.googledistance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 * */
public class Example{
  @SerializedName("routes")
  @Expose
  private List<Routes> routes;
  @SerializedName("geocoded_waypoints")
  @Expose
  private List<Geocoded_waypoints> geocoded_waypoints;
  @SerializedName("status")
  @Expose
  private String status;
  public void setRoutes(List<Routes> routes){
   this.routes=routes;
  }
  public List<Routes> getRoutes(){
   return routes;
  }
  public void setGeocoded_waypoints(List<Geocoded_waypoints> geocoded_waypoints){
   this.geocoded_waypoints=geocoded_waypoints;
  }
  public List<Geocoded_waypoints> getGeocoded_waypoints(){
   return geocoded_waypoints;
  }
  public void setStatus(String status){
   this.status=status;
  }
  public String getStatus(){
   return status;
  }
}