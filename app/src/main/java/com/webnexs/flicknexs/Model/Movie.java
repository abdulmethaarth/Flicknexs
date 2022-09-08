package com.webnexs.flicknexs.Model;


public class Movie {

   private String title,id,ppv_status;
   private String image,mobile_image;


    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMobile_image() {
        return mobile_image;
    }

    public void setMobile_image(String mobile_image) {
        this.mobile_image = mobile_image;
    }

    public String getId()
    {
        return id;
    }

    public String getPpv_status()
    {
        return ppv_status;
    }

    public void setPpv_status(String ppv_status)
    {
        this.ppv_status=ppv_status;
    }

    public void setId(String id)
    {
        this.id=id;
    }


}




