package navigation;

public class Page {

    public enum Type {HOME, USER, PROFILE, PLAYLIST, ARTIST, ALBUM};

    private int id;
    private Type type;

    public Page(int id, Type type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

}
