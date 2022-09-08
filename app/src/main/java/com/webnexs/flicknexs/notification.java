package com.webnexs.flicknexs;

class notification {

    private String title,  year,id;
    private String genre,duration;

    public notification() {
    }

    public notification(String title, String genre, String year, String duration, String id) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.duration=duration;
        this.id=id;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public String getDuration() {
        return duration;
    }

    public String getGenre() {
        return genre ;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
