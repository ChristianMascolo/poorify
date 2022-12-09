package track;

import album.AlbumBean;
import profile.ArtistBean;
import java.util.Collection;

public class TrackBean {
    private int id;
    private String title;
    private int index;
    private int duration;
    private int plays;

    private AlbumBean album;

    private Collection<ArtistBean> featuring;

    public TrackBean() {}

    public TrackBean(int id, String title, int index, int duration, int plays) {
        this.id = id;
        this.title = title;
        this.index = index;
        this.duration = duration;
        this.plays = plays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }

    public AlbumBean getAlbum() {
        return album;
    }

    public void setAlbum(AlbumBean album) {
        this.album = album;
    }

    public Collection<ArtistBean> getFeaturing() {
        return featuring;
    }

    public void setFeaturing(Collection<ArtistBean> featuring) {
        this.featuring = featuring;
    }
}
