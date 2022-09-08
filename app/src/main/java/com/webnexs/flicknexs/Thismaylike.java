package com.webnexs.flicknexs;

class Thismaylike {

    private String name, url,id,ppvstatus;
    private String image,mobile_image;

    public Thismaylike() {
    }

    public Thismaylike(String name, String image, String url, String id, String ppvstatus, String mobile_image ) {
        this.name = name;
        this.image = image;
        this.url = url;
        this.id=id;
        this.ppvstatus=ppvstatus;
        this.mobile_image=mobile_image;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMobile_image()
    {
        return mobile_image;
    }

    public void setMobile_image(String mobile_image)
    {
        this.mobile_image=mobile_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPpvstatus()
    {
        return ppvstatus;
    }

    public void setPpvstatus(String ppvstatus)
    {
        this.ppvstatus=ppvstatus;
    }


}
