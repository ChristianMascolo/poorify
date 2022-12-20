package profile;

import album.AlbumBean;
import track.TrackBean;

import java.util.Collection;

public class ArtistBean extends ProfileBean {

    private String alias;
    private String bio;
    private Collection<AlbumBean> albums;
    private Collection<TrackBean> topTracks;

    public ArtistBean() {
        super(Role.ARTIST);
    }

    public ArtistBean(int id, String email, String password, String alias, String bio) {
        super(id, email, password, Role.ARTIST);
        this.alias = alias;
        this.bio = bio;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Collection<AlbumBean> getAlbums() {
        return albums;
    }

    public void setAlbums(Collection<AlbumBean> albums) {
        this.albums = albums;
    }

    public Collection<TrackBean> getTopTracks() { return topTracks; }

    public void setTopTracks(Collection<TrackBean> topTracks) { this.topTracks = topTracks; }
}
