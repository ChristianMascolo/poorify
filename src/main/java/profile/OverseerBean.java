package profile;

public class OverseerBean extends ProfileBean {

    public OverseerBean(int id, String email, String password) {
        super(id, email, password, Role.OVERSEER);
    }

}
