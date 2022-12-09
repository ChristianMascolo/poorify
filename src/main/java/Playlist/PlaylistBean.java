package Playlist;

import profile.NationBean;
import profile.UserBean;
import track.TrackBean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class PlaylistBean {
    private int id;
    private UserBean host;
    private String title;
    private int tracks;
    private int duration;
    private boolean isPublic;
    private int likes;
    private boolean isCollaborative;
    private Set<UserBean> guests;
    private Collection<TrackBean> tracklist;
    private enum Order{DATE, TITLE, DURATION, ARTIST, ALBUM}


    public PlaylistBean(){ }
    public PlaylistBean(int id, String title, int tracks, int duration, boolean isPublic, boolean isCollaborative) {
        this.id = id;
        this.title = title;
        this.tracks = tracks;
        this.duration = duration;
        this.isPublic = isPublic;
        this.isCollaborative = isCollaborative;
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

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isCollaborative() {
        return isCollaborative;
    }

    public void setCollaborative(boolean collaborative) {
        isCollaborative = collaborative;
    }

    public UserBean getHost() {
        return host;
    }

    public void setHost(UserBean host) {
        this.host = host;
    }

    public Set<UserBean> getGuests() {
        return guests;
    }

    public void setGuests(Set<UserBean> guests) {
        this.guests = guests;
    }

    public Collection<TrackBean> getTracklist() {
        return tracklist;
    }

    public void setTracklist(Collection<TrackBean> tracklist) {
        this.tracklist = tracklist;
    }
}
