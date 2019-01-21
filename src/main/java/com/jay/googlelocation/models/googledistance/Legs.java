package com.jay.googlelocation.models.googledistance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 * */
public class Legs{
  @SerializedName("duration")
  @Expose
  private Duration duration;
  @SerializedName("start_location")
  @Expose
  private Start_location start_location;
  @SerializedName("distance")
  @Expose
  private Distance distance;
  @SerializedName("start_address")
  @Expose
  private String start_address;
  @SerializedName("end_location")
  @Expose
  private End_location end_location;
  @SerializedName("end_address")
  @Expose
  private String end_address;
  @SerializedName("steps")
  @Expose
  private List<Steps> steps;

  public void setDuration(Duration duration){
   this.duration=duration;
  }
  public Duration getDuration(){
   return duration;
  }
  public void setStart_location(Start_location start_location){
   this.start_location=start_location;
  }
  public Start_location getStart_location(){
   return start_location;
  }
  public void setDistance(Distance distance){
   this.distance=distance;
  }
  public Distance getDistance(){
   return distance;
  }
  public void setStart_address(String start_address){
   this.start_address=start_address;
  }
  public String getStart_address(){
   return start_address;
  }
  public void setEnd_location(End_location end_location){
   this.end_location=end_location;
  }
  public End_location getEnd_location(){
   return end_location;
  }
  public void setEnd_address(String end_address){
   this.end_address=end_address;
  }
  public String getEnd_address(){
   return end_address;
  }

  public void setSteps(List<Steps> steps){
   this.steps=steps;
  }
  public List<Steps> getSteps(){
   return steps;
  }
}