package playlist;

import album.AlbumBean;
import album.AlbumDAO;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistDAOTest {

    private Connection open() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poorify","poorify","poorify");
        return connection;
    }

    @Test
    public void getTest() throws Exception {
        Connection connection = open();
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);

        PlaylistBean playlist = playlistDAO.get(1);

        JSONObject jsonObject = new JSONObject(playlist);
        System.out.println(jsonObject);

        assert(playlist != null);
    }

    @Test
    public void addTest() throws Exception {
        Connection connection = open();
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);

        connection.setAutoCommit(false);

        boolean outcome = playlistDAO.add(2, "Title");

        connection.rollback();

        assert(outcome == true);
    }

    @Test
    public void getLastFromUserTest() throws Exception {
        Connection connection = open();
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);

        int result = playlistDAO.getLastFromUser(2);

        System.out.println(result);

        assert(result > 0);
    }

    @Test
    public void getFromUserTest() throws Exception {
        Connection connection = open();
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);

        Collection<PlaylistBean> playlists = playlistDAO.getFromUser(2);

        for(PlaylistBean playlist: playlists) {
            JSONObject jsonObject = new JSONObject(playlist);
            System.out.println(jsonObject);
        }

        assert(playlists.size() > 0);
    }

    @Test
    public void getFromLikesTest() throws Exception {
        Connection connection = open();
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);

        Collection<PlaylistBean> playlists = playlistDAO.getFromLikes(2);

        for(PlaylistBean playlist: playlists) {
            JSONObject jsonObject = new JSONObject(playlist);
            System.out.println(jsonObject);
        }

        assert(playlists != null);
    }

    @Test
    public void getPublicFromUserTest() throws Exception {
        Connection connection = open();
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);

        Collection<PlaylistBean> playlists = playlistDAO.getPublicFromUser(2);

        for(PlaylistBean playlist: playlists) {
            JSONObject jsonObject = new JSONObject(playlist);
            System.out.println(jsonObject);
        }

        assert(playlists != null);
    }

    @Test
    public void getAddedTest() throws Exception {
        Connection connection = open();
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);

        Collection<AddedBeanProxy> added = playlistDAO.getAdded(1);

        for(AddedBeanProxy a: added) {
            JSONObject jsonObject = new JSONObject(a);
            System.out.println(jsonObject);
        }

        assert(added != null);
    }

    @Test
    public void getLikesTest() throws Exception {
        Connection connection = open();
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);

        int result = playlistDAO.getLikes(1);

        System.out.println(result);

        assert(result > -1);
    }

    @Test
    public void getCountFromUserTest() throws Exception {
        Connection connection = open();
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);

        int result = playlistDAO.getCountFromUser(2);

        System.out.println(result);

        assert(result > -1);
    }

    @Test
    public void addTrackToPlaylistTest() throws Exception {
        Connection connection = open();
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);

        connection.setAutoCommit(false);

        boolean outcome = playlistDAO.addTrackToPlaylist(2, 1, 1);

        connection.rollback();

        assert(outcome == true);
    }

    @Test
    public void removeTrackToPlaylistTest() throws Exception {
        Connection connection = open();
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);

        connection.setAutoCommit(false);

        playlistDAO.removeTrackFromPlaylist(1, 1);

        connection.rollback();
    }

    @Test
    public void removeTest() throws Exception {
        Connection connection = open();
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);

        connection.setAutoCommit(false);

        boolean outcome = playlistDAO.remove(1);

        connection.rollback();

        assert(outcome == true);
    }

    @Test
    public void changeTitleTest() throws Exception {
        Connection connection = open();
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);

        connection.setAutoCommit(false);

        boolean outcome = playlistDAO.changeTitle(1, "Title");

        connection.rollback();

        assert(outcome == true);
    }

    @Test
    public void updateLastTimeAccessTest() throws Exception {
        Connection connection = open();
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);

        connection.setAutoCommit(false);

        playlistDAO.updateLastAccess(1);

        connection.rollback();
    }

    @Test
    public void searchPlaylistsByTitleTest() throws Exception {
        Connection connection = open();
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);

        Collection<PlaylistBean> playlists = playlistDAO.searchPlaylistsByTitle("a");

        for(PlaylistBean playlist: playlists) {
            JSONObject jsonObject = new JSONObject(playlist);
            System.out.println(jsonObject);
        }

        assert(playlists != null);
    }
}