package com.webnexs.flicknexs.Model;


import java.util.List;


public class Data {

    List<Movie> movies;
    String genre_name;


    public Data(List<Movie> movies, String genre_name) {
        this.movies = movies;
        this.genre_name = genre_name;
    }

    public List<Movie> getList() {
        return movies;
    }

    public String getGenre_name() {
        return genre_name;
    }

}
