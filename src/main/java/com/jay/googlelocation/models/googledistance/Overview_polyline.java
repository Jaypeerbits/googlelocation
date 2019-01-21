package com.jay.googlelocation.models.googledistance;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 * */
public class Overview_polyline{
  @SerializedName("points")
  @Expose
  private String points;
  public void setPoints(String points){
   this.points=points;
  }
  public String getPoints(){
   return points;
  }
}