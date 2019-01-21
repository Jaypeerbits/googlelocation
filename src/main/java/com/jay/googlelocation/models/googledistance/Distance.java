package com.jay.googlelocation.models.googledistance;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 * */
public class Distance{
  @SerializedName("text")
  @Expose
  private String text;
  @SerializedName("value")
  @Expose
  private String value;
  public void setText(String text){
   this.text=text;
  }
  public String getText(){
   return text;
  }
  public void setValue(String value){
   this.value=value;
  }
  public String getValue(){
   return value;
  }
}