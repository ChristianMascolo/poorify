package playlist;

import profile.UserBean;
import track.TrackBean;

import java.util.Collection;
import java.util.TreeSet;

public class PlaylistBean {

    private enum Order{DATE, TITLE, DURATION, ARTIST, ALBUM}

    private int id;
    private UserBean host;
    private String title;
    private int tracks;
    private int duration;
    private boolean isPublic;
    private int likes;
    private boolean isCollaborative;
    private String lastAccessTime;
    private Collection<UserBean> guests;
    private Collection<AddedBean> tracklist;

    public PlaylistBean() {}

    public PlaylistBean(int id, String title, int tracks, int duration, boolean isPublic, boolean isCollaborative, String lastAccessTime) {
        this.id = id;
        this.title = title;
        this.tracks = tracks;
        this.duration = duration;
        this.isPublic = isPublic;
        this.isCollaborative = isCollaborative;
        this.lastAccessTime = lastAccessTime;
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

    public String getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(String lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public UserBean getHost() {
        return host;
    }

    public void setHost(UserBean host) {
        this.host = host;
    }

    public Collection<UserBean> getGuests() {
        return guests;
    }

    public void setGuests(Collection<UserBean> guests) {
        this.guests = guests;
    }

    public Collection<AddedBean> getTracklist() {
        return tracklist;
    }

    public Collection<AddedBean> getTracklist(Order order) {
        Collection<AddedBean> toReturn = null;

        switch(order) {
            case DATE: toReturn = new TreeSet<>((AddedBean a, AddedBean b) -> a.getDate().compareTo(b.getDate())); break;
            case TITLE: toReturn = new TreeSet<>((AddedBean a, AddedBean b) -> a.getTrack().getTitle().compareTo(b.getTrack().getTitle())); break;
            case DURATION: toReturn = new TreeSet<>((AddedBean a, AddedBean b) -> a.getTrack().getDuration() - b.getTrack().getDuration()); break;
            case ARTIST: toReturn = new TreeSet<>((AddedBean a, AddedBean b) -> a.getTrack().getAlbum().getArtist().getAlias().compareTo(b.getTrack().getAlbum().getArtist().getAlias()) ); break;
            case ALBUM: toReturn = new TreeSet<>((AddedBean a, AddedBean b) -> a.getTrack().getAlbum().getTitle().compareTo(b.getTrack().getAlbum().getTitle())); break;
        }

        for(AddedBean a: tracklist)
            toReturn.add(a);

        return toReturn;
    }

    public void setTracklist(Collection<AddedBean> tracklist) {
        this.tracklist = tracklist;
    }
}
