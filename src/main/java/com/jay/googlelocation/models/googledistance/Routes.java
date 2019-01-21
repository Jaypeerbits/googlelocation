package com.jay.googlelocation.models.googledistance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 * */
public class Routes{
  @SerializedName("summary")
  @Expose
  private String summary;
  @SerializedName("copyrights")
  @Expose
  private String copyrights;
  @SerializedName("legs")
  @Expose
  private List<Legs> legs;
  @SerializedName("bounds")
  @Expose
  private Bounds bounds;
  @SerializedName("overview_polyline")
  @Expose
  private Overview_polyline overview_polyline;
  public void setSummary(String summary){
   this.summary=summary;
  }
  public String getSummary(){
   return summary;
  }
  public void setCopyrights(String copyrights){
   this.copyrights=copyrights;
  }
  public String getCopyrights(){
   return copyrights;
  }
  public void setLegs(List<Legs> legs){
   this.legs=legs;
  }
  public List<Legs> getLegs(){
   return legs;
  }
  public void setBounds(Bounds bounds){
   this.bounds=bounds;
  }
  public Bounds getBounds(){
   return bounds;
  }
  public void setOverview_polyline(Overview_polyline overview_polyline){
   this.overview_polyline=overview_polyline;
  }
  public Overview_polyline getOverview_polyline(){
   return overview_polyline;
  }
}