package com.webnexs.flicknexs;


class audioplayertitle extends videos {

    private String title,  year;
    private String genre,duration,cattitle;

    public audioplayertitle() {
    }

    public audioplayertitle(String title, String genre, String year, String duration, String cattitle) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.duration=duration;
        this.cattitle=cattitle;
    }



    public String getCatitle() {
        return cattitle;
    }

    public void setCattitle(String cattitle) {
        this.cattitle = cattitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
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

