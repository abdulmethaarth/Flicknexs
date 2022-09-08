package com.webnexs.flicknexs;

class videodetails {

    private String name, url,id,year,language,genre,runningtime;


    public videodetails() {
    }

    public videodetails(String name, String year, String url, String id, String language, String genre, String runningtime ) {
        this.name = name;
        this.genre=genre;
        this.language=language;
        this.year=year;
        this.runningtime=runningtime;
        this.url = url;
        this.id=id;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRunningtime() {
        return runningtime;
    }

    public void setRunningtime(String runningtime) {
        this.runningtime = runningtime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }





}
