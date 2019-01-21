package com.jay.googlelocation.models.googledistance;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 * */
public class Steps{
  @SerializedName("duration")
  @Expose
  private Duration duration;
  @SerializedName("start_location")
  @Expose
  private Start_location start_location;
  @SerializedName("distance")
  @Expose
  private Distance distance;
  @SerializedName("travel_mode")
  @Expose
  private String travel_mode;
  @SerializedName("html_instructions")
  @Expose
  private String html_instructions;
  @SerializedName("end_location")
  @Expose
  private End_location end_location;
  @SerializedName("polyline")
  @Expose
  private Polyline polyline;
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
  public void setTravel_mode(String travel_mode){
   this.travel_mode=travel_mode;
  }
  public String getTravel_mode(){
   return travel_mode;
  }
  public void setHtml_instructions(String html_instructions){
   this.html_instructions=html_instructions;
  }
  public String getHtml_instructions(){
   return html_instructions;
  }
  public void setEnd_location(End_location end_location){
   this.end_location=end_location;
  }
  public End_location getEnd_location(){
   return end_location;
  }
  public void setPolyline(Polyline polyline){
   this.polyline=polyline;
  }
  public Polyline getPolyline(){
   return polyline;
  }
}