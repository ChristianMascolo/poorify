package profile;

public class OverseerBean extends ProfileBean {

    public OverseerBean() {
        super(Role.OVERSEER);
    }

    public OverseerBean(int id, String email, String password) {
        super(id, email, password, Role.OVERSEER);
    }

}
