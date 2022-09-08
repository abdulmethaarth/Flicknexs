package com.webnexs.flicknexs;

class Audiocatpage {

    private String name, url,id,duration,ppvstatus;
    private String image;

    public Audiocatpage() {
    }

    public Audiocatpage(String name, String image, String url, String id, String duration, String ppvstatus ) {
        this.name = name;
        this.image = image;
        this.url = url;
        this.id=id;
        this.duration=duration;
        this.ppvstatus=ppvstatus;

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

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        this.duration=duration;
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
