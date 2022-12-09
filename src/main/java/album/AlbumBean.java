package album;

import profile.ArtistBean;

import java.util.TreeSet;

public class AlbumBean {

    private int id;
    private String title;
    private int tracks;
    private int duration;
    private int year;
    private ArtistBean artist;

    public AlbumBean(int id, String title, int tracks, int duration, int year) {
        this.id = id;
        this.title = title;
        this.tracks = tracks;
        this.duration = duration;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getTracks() {
        return tracks;
    }

    public int getDuration() {
        return duration;
    }

    public int getYear() {
        return year;
    }

    public ArtistBean getArtist() {
        return artist;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setArtist(ArtistBean artist) {
        this.artist = artist;
    }



}
