package com.webnexs.flicknexs;

class quality {

    private String name, url,id,ppvstatus,expire;
    private String image;

    public quality() {
    }

    public quality(String name, String image, String url, String id, String ppvstatus, String expire) {
        this.name = name;
        this.image = image;
        this.url = url;
        this.id=id;
        this.ppvstatus=ppvstatus;
        this.expire=expire;
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

    public String getPpvstatus()
    {
        return ppvstatus;
    }

    public void setPpvstatus(String ppvstatus) {
        this.ppvstatus=ppvstatus;
    }

    public String getExpire()
    {
        return expire;
    }
    public void setExpire(String expire)
    {
        this.expire=expire;
    }
}
