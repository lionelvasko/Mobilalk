package hu.inf.szte.model;

import com.google.type.Date;

public class Movie {
    private String id;
    private final String name;
    private final Integer duration;
    private final String picture;

    private final Date releaseDate;

    public Movie(String id, String name, Integer duration, String picture, Date releaseDate) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.picture = picture;
        this.releaseDate = releaseDate;
    }

    public Movie() {
        this.id = "";
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
