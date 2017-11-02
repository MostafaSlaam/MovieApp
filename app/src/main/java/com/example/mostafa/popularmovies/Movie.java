package com.example.mostafa.popularmovies;

import java.util.ArrayList;

/**
 * Created by mostafa on 10/3/2017.
 */

public class Movie {
    private String Movie_ID;
    private String title;
    private String image;
    private String overview;
    private String vote_average;
    private String date;
    public Movie()
    {}
    public void setMovie(String Movie_ID,String title,String image,String overview,String vote_average,String date)
    {
        this.Movie_ID=Movie_ID;
        this.title=title;
        this.image=image;
        this.overview=overview;
        this.vote_average=vote_average;
        this.date=date;

    }
    public String getMovie_ID(){return Movie_ID;}
    public String getTitle()
    {
        return title;
    }
    public String getImage()
    {
        return image;
    }
    public String getOverview()
    {
        return overview;
    }
    public String getVote_average()
    {
        return vote_average;
    }
    public String getDate()
    {
        return date;
    }

}
