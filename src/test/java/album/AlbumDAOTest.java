package album;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class AlbumDAOTest {

    private Connection open() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poorify","poorify","poorify");
        return connection;
    }

    @Test
    public void getTest() throws Exception {
        Connection connection = open();
        AlbumDAO albumDAO = new AlbumDAO(connection);

        AlbumBean album = albumDAO.get(1);

        JSONObject jsonObject = new JSONObject(album);
        System.out.println(jsonObject);

        assert(album != null);
    }

    @Test
    public void getAltTest() throws Exception {
        Connection connection = open();
        AlbumDAO albumDAO = new AlbumDAO(connection);

        int result = albumDAO.get(3, "X&Y");

        System.out.println(result);

        assert(result > 0);
    }

    @Test
    public void getFromArtistTest() throws Exception {
        Connection connection = open();
        AlbumDAO albumDAO = new AlbumDAO(connection);

        Collection<AlbumBean> albums = albumDAO.getFromArtist(3);

        for(AlbumBean album: albums) {
            JSONObject jsonObject = new JSONObject(album);
            System.out.println(jsonObject);
        }

        assert(albums.size() > 0);
    }

    @Test
    public void getFromTrackTest() throws Exception {
        Connection connection = open();
        AlbumDAO albumDAO = new AlbumDAO(connection);

        AlbumBean album = albumDAO.getFromTrack(1);

        JSONObject jsonObject = new JSONObject(album);
        System.out.println(jsonObject);

        assert(album != null);
    }

    @Test
    public void searchAlbumByTitleTest() throws Exception {
        Connection connection = open();
        AlbumDAO albumDAO = new AlbumDAO(connection);

        Collection<AlbumBean> albums = albumDAO.searchAlbumsByTitle("a");

        for(AlbumBean album: albums) {
            JSONObject jsonObject = new JSONObject(album);
            System.out.println(jsonObject);
        }

        assert(albums.size() > 0);
    }

    @Test
    public void addTest() throws Exception {
        Connection connection = open();
        AlbumDAO albumDAO = new AlbumDAO(connection);

        connection.setAutoCommit(false);

        boolean outcome = albumDAO.add(3, "Title", 1, 1, 2000, "Album");

        connection.rollback();

        assert(outcome == true);
    }

    @Test
    public void removeTest() throws Exception {
        Connection connection = open();
        AlbumDAO albumDAO = new AlbumDAO(connection);

        connection.setAutoCommit(false);

        albumDAO.remove(1);

        connection.rollback();
    }


}