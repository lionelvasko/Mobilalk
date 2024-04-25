package hu.inf.szte.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

public class Movie {
    private String id;
    private final String name;
    private final Integer duration;
    private final String picture;
    private final Timestamp releaseDate;

    public Movie(String id, String name, Integer duration, String picture, Timestamp releaseDate) {
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

    public Movie(String name, int duration, Timestamp releaseDate, String pictureUrl) {
        this.name = name;
        this.duration = duration;
        this.picture = pictureUrl;
        this.releaseDate = releaseDate;
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

    public Timestamp getReleaseDate() {
        return releaseDate;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }
}