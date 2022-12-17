package profile;

import playlist.PlaylistBean;

import java.util.Collection;

public class UserBean extends ProfileBean {

    private String alias;
    private String birthdate;
    private NationBean nation;
    private boolean isPublic;
    private Collection<PlaylistBean> playlists;
    private Collection<PlaylistBean> likedPlaylists;
    private Collection<ArtistBean> artists;
    private Collection<UserBean> followers;
    private Collection<UserBean> following;

    public UserBean() {
        super(Role.USER);
    }
    
    public UserBean(int id, String email, String password, String alias, String birthdate, NationBean nation, boolean isPublic) {
        super(id, email, password, Role.USER);
        this.alias = alias;
        this.birthdate = birthdate;
        this.nation = nation;
        this.isPublic = isPublic;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public NationBean getNation() {
        return nation;
    }

    public void setNation(NationBean nation) {
        this.nation = nation;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Collection<PlaylistBean> getPlaylists() { return playlists; }

    public void setPlaylists(Collection<PlaylistBean> playlists) {
        this.playlists = playlists;
    }

    public Collection<PlaylistBean> getLikedPlaylists() {
        return likedPlaylists;
    }

    public void setLikedPlaylists(Collection<PlaylistBean> likedPlaylists) {
        this.likedPlaylists = likedPlaylists;
    }

    public Collection<ArtistBean> getArtists() {
        return artists;
    }

    public void setArtists(Collection<ArtistBean> artists) {
        this.artists = artists;
    }

    public Collection<UserBean> getFollowers() {
        return followers;
    }

    public void setFollowers(Collection<UserBean> followers) {
        this.followers = followers;
    }

    public Collection<UserBean> getFollowing() {
        return following;
    }

    public void setFollowing(Collection<UserBean> following) {
        this.following = following;
    }
}
