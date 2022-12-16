package playlist;

public class AddedBeanProxy {

    private int user;
    private int track;
    private int playlist;
    private String date;

    public AddedBeanProxy() {}

    public AddedBeanProxy(int user, int track, int playlist, String date) {
        this.user = user;
        this.track = track;
        this.playlist = playlist;
        this.date = date;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public int getPlaylist() {
        return playlist;
    }

    public void setPlaylist(int playlist) {
        this.playlist = playlist;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
