package hu.inf.szte.model;

import com.google.type.Date;

public class Movie {
    private final String name;
    private final Integer duration;
    private final String picture;

    private final Date releaseDate;

    public Movie(String name, Integer duration, String picture, Date releaseDate) {
        this.name = name;
        this.duration = duration;
        this.picture = picture;
        this.releaseDate = releaseDate;
    }

    public Movie() {
        this.name = "";
        this.duration = 0;
        this.picture = "";
        this.releaseDate = null;
    }

    public String getName() {
        return name;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getPicture() {
        return picture;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }


}
