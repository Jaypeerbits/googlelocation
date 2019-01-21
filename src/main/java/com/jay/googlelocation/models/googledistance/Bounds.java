package com.jay.googlelocation.models.googledistance;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 * */
public class Bounds{
  @SerializedName("southwest")
  @Expose
  private Southwest southwest;
  @SerializedName("northeast")
  @Expose
  private Northeast northeast;
  public void setSouthwest(Southwest southwest){
   this.southwest=southwest;
  }
  public Southwest getSouthwest(){
   return southwest;
  }
  public void setNortheast(Northeast northeast){
   this.northeast=northeast;
  }
  public Northeast getNortheast(){
   return northeast;
  }
}