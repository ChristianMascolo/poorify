package profile;

public class UserBean extends ProfileBean {

    private String alias;
    private String birthdate;
    private NationBean nation;
    private boolean isPublic;

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
}
