package com.webnexs.flicknexs.Model;


public class HomeData {


    private String genre_name,genre_id;
    private Movie[] movies;


    public String getGenreName() { return genre_name; }

    public void setGenreName(String value) { this.genre_name = value; }

    public String getGenre_id()
    {
        return genre_id;
    }

    public void setGenre_id(String genre_id) {
        this.genre_id = genre_id;
    }

    public Movie[] getMovies() { return movies; }

    public void setMovies(Movie[] value) { this.movies = value; }
}
