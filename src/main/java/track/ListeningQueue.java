package track;

import java.util.ArrayDeque;
import java.util.Queue;

public class ListeningQueue {

    private Queue<Integer> userQueue;
    private Queue<Integer> autoQueue;

    public ListeningQueue() {
        userQueue = new ArrayDeque<>();
        autoQueue = new ArrayDeque<>();
    }

    public void addToUserQueue(int id) {
        userQueue.add(id);
    }

    public void addToAutoQueue(int id) {
        autoQueue.add(id);
    }

    public void clearAutoQueue() {
        autoQueue.clear();
    }

    public int poll() {
        if(!userQueue.isEmpty())
            return userQueue.poll();
        if(!autoQueue.isEmpty())
            return autoQueue.poll();
        return 0;
    }

}
