package com.webnexs.flicknexs.Model;



public class HomeBodyResponse {

    private String status;
    private HomeData[] genre_movies;


    public String getStatus() { return status; }

    public void setStatus(String value) { this.status = value; }

    public HomeData[] getGenreMovies() { return genre_movies; }

    public void setGenreMovies(HomeData[] value) { this.genre_movies = value; }
}
