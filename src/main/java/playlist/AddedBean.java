package playlist;

import profile.UserBean;
import track.TrackBean;

public class AddedBean {
    private UserBean user;
    private TrackBean track;
    private PlaylistBean playlist;
    private String date;

    public AddedBean() {}

    public AddedBean(UserBean user, PlaylistBean playlist, String date, TrackBean track) {
        this.user = user;
        this.playlist = playlist;
        this.track = track;
        this.date = date;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public TrackBean getTrack() {
        return track;
    }

    public void setTrack(TrackBean track) {
        this.track = track;
    }

    public PlaylistBean getPlaylist() {
        return playlist;
    }

    public void setPlaylist(PlaylistBean playlist) {
        this.playlist = playlist;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
