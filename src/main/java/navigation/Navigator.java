package navigation;

import java.util.Stack;

public class Navigator {

    private Stack<Page> prev;
    private Page current;
    private Stack<Page> next;

    public Navigator() {
        this.prev = new Stack<>();
        this.next = new Stack<>();
    }

    public Page getCurrent() {
        return current;
    }

    public void setCurrent(Page current) {
        this.current = current;
    }

    public void save() {
        prev.add(current);
        next.clear();
    }

    public Page prev() {
        if(hasPrev()) {
            next.add(current);
            current = prev.pop();
            return current;
        }
        return null;
    }

    public Page next() {
        if(hasNext()) {
            prev.add(current);
            current = next.pop();
            return current;
        }
        return null;
    }

    public boolean hasPrev() {
        return !prev.empty();
    }

    public boolean hasNext() {
        return !next.empty();
    }

}
