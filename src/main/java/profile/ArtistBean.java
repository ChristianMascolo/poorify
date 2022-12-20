package profile;

import track.TrackBean;

import java.util.Collection;

public class ArtistBean extends ProfileBean {

    private String alias;
    private String bio;
    private Collection<TrackBean> tracks;

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

    public Collection<TrackBean> getTracks() { return tracks; }

    public void setTracks(Collection<TrackBean> tracks) { this.tracks = tracks; }
}
