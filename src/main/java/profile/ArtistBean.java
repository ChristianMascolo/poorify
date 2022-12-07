package profile;

public class ArtistBean extends ProfileBean {

    private String alias;
    private String bio;

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

}
