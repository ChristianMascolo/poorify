package navigation;

public class Page {

    public enum Type {HOME, USER, PROFILE, PLAYLIST, ARTIST, ALBUM};

    private int id;
    private Type type;

    public Page() {}

    public Page(int id, Type type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) { this.type = type; }

}
