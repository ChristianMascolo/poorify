package album;

import profile.ArtistBean;
import track.TrackBean;
import java.util.Collection;

public class AlbumBean {

    private int id;
    private String title;
    private int tracks;
    private int duration;
    private int year;
    private String type;
    private ArtistBean artist;
    private Collection<TrackBean> tracklist;

    public AlbumBean() {}

    public AlbumBean(int id, String title, int tracks, int duration, int year, String type) {
        this.id = id;
        this.title = title;
        this.tracks = tracks;
        this.duration = duration;
        this.year = year;
        this.type = type;
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

    public String getType() { return type; }

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

    public void setType(String type) { this.type = type; }

    public void setArtist(ArtistBean artist) {
        this.artist = artist;
    }

    public Collection<TrackBean> getTracklist() {
        return tracklist;
    }

    public void setTracklist(Collection<TrackBean> tracklist) {
        this.tracklist = tracklist;
    }

}
