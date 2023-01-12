package track;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import playlist.PlaylistBean;
import playlist.PlaylistDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class TrackDAOTest {

    private Connection open() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poorify","poorify","poorify");
        return connection;
    }

    @Test
    public void getTest() throws Exception {
        Connection connection = open();
        TrackDAO trackDAO = new TrackDAO(connection);

        TrackBean track = trackDAO.get(1);

        JSONObject jsonObject = new JSONObject(track);
        System.out.println(jsonObject);

        assert(track != null);
    }

    @Test
    public void getFromAlbumTest() throws Exception {
        Connection connection = open();
        TrackDAO trackDAO = new TrackDAO(connection);

        Collection<TrackBean> tracks = trackDAO.getFromAlbum(1);

        for(TrackBean track: tracks) {
            JSONObject jsonObject = new JSONObject(track);
            System.out.println(jsonObject);
        }

        assert(tracks.size() > 0);
    }

    @Test
    public void getTopFiveFromArtistTest() throws Exception {
        Connection connection = open();
        TrackDAO trackDAO = new TrackDAO(connection);

        Collection<TrackBean> tracks = trackDAO.getTopFiveFromArtist(3);

        for(TrackBean track: tracks) {
            JSONObject jsonObject = new JSONObject(track);
            System.out.println(jsonObject);
        }

        assert(tracks.size() > 0);
    }

    @Test
    public void addPlayTest() throws Exception {
        Connection connection = open();
        TrackDAO trackDAO = new TrackDAO(connection);

        connection.setAutoCommit(false);

        trackDAO.addPlay(2, 1);

        connection.rollback();
    }

    @Test
    public void searchTrackByTitleTest() throws Exception {
        Connection connection = open();
        TrackDAO trackDAO = new TrackDAO(connection);

        Collection<TrackBean> tracks = trackDAO.searchTracksByTitle("a");

        for(TrackBean track: tracks) {
            JSONObject jsonObject = new JSONObject(track);
            System.out.println(jsonObject);
        }

        assert(tracks.size() > 0);
    }

    @Test
    public void addTest() throws Exception {
        Connection connection = open();
        TrackDAO trackDAO = new TrackDAO(connection);

        connection.setAutoCommit(false);

        boolean outcome = trackDAO.add(1, "Title", 1, 1, 1);

        connection.rollback();
        assert(outcome == true);
    }

    @Test
    public void getIndexTest() throws Exception {
        Connection connection = open();
        TrackDAO trackDAO = new TrackDAO(connection);

        int index = trackDAO.get(1, "Parachutes");

        assert(index > 0);
    }

    @Test
    public void generateFeedTest() throws Exception {
        Connection connection = open();
        TrackDAO trackDAO = new TrackDAO(connection);

        Collection<TrackBean> tracks = trackDAO.generateFeed(2);

        for(TrackBean track: tracks) {
            JSONObject jsonObject = new JSONObject(track);
            System.out.println(jsonObject);
        }

        assert(tracks.size() > 0);
    }

    @Test
    public void shuffleTest() throws Exception {
        Connection connection = open();
        TrackDAO trackDAO = new TrackDAO(connection);

        int index = trackDAO.shuffle(2);

        System.out.println(index);
        assert(index > 0);
    }
}