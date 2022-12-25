package navigation;

import album.AlbumBean;
import playlist.PlaylistBean;
import profile.ArtistBean;
import profile.UserBean;
import track.TrackBean;

import java.util.Collection;

public class ResultsContainer {

    private Collection<UserBean> users;
    private Collection<ArtistBean> artists;
    private Collection<PlaylistBean> playlists;
    private Collection<AlbumBean> albums;
    private Collection<TrackBean> tracks;

    public ResultsContainer() {}

    public boolean isEmpty() {
        return users.isEmpty() && artists.isEmpty() && playlists.isEmpty() && albums.isEmpty() && tracks.isEmpty();
    }

    public Collection<UserBean> getUsers() {
        return users;
    }

    public void setUsers(Collection<UserBean> users) {
        this.users = users;
    }

    public Collection<ArtistBean> getArtists() {
        return artists;
    }

    public void setArtists(Collection<ArtistBean> artists) {
        this.artists = artists;
    }

    public Collection<PlaylistBean> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Collection<PlaylistBean> playlists) {
        this.playlists = playlists;
    }

    public Collection<AlbumBean> getAlbums() {
        return albums;
    }

    public void setAlbums(Collection<AlbumBean> albums) {
        this.albums = albums;
    }

    public Collection<TrackBean> getTracks() {
        return tracks;
    }

    public void setTracks(Collection<TrackBean> tracks) {
        this.tracks = tracks;
    }
}
