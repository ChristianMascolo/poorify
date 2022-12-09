package track;
public class TrackBean {
    private int id;
    private String title;
    private int index;
    private int duration;
    private int plays;

    public TrackBean() {
    }

    public TrackBean(int id, String title, int index, int duration, int plays) {
        this.id = id;
        this.title = title;
        this.index = index;
        this.duration = duration;
        this.plays = plays;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }
}
