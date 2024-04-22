package hu.inf.szte.model;

public class Movie {
    private final String name;
    private final String genre;
    private final int year;

    public Movie(String name, String genre, int year) {
        this.name = name;
        this.genre = genre;
        this.year = year;
    }

    public Movie() {
        this.name = "";
        this.genre = "";
        this.year = 0;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }
}
