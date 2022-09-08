package com.webnexs.flicknexs;

class trendingmovies {

    private String name, url,id,ppvstatus;
    private String image,genre;

    public trendingmovies() {
    }

    public trendingmovies(String name, String image, String url, String id , String genre, String ppvstatus) {
        this.name = name;
        this.image = image;
        this.url = url;
        this.id=id;
        this.genre=genre;
        this.ppvstatus=ppvstatus;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPpvstatus()
    {
        return ppvstatus;
    }

    public void setPpvstatus(String ppvstatus)
    {
        this.ppvstatus=ppvstatus;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }




}
