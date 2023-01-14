package profile;

import album.AlbumBean;
import album.AlbumDAO;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ProfileDAOTest {

    private Connection open() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poorify","poorify","poorify");
        return connection;
    }

    @Test
    public void getUserTest() throws Exception {
        Connection connection = open();
        ProfileDAO profileDAO = new ProfileDAO(connection);

        ProfileBean profileBean = profileDAO.get("alessandro@gmail.com", "alessandro");

        JSONObject jsonObject = new JSONObject(profileBean);
        System.out.println(jsonObject);

        assert(profileBean != null);
    }

    @Test
    public void getArtistTest() throws Exception {
        Connection connection = open();
        ProfileDAO profileDAO = new ProfileDAO(connection);

        ProfileBean profileBean = profileDAO.get(3);

        JSONObject jsonObject = new JSONObject(profileBean);
        System.out.println(jsonObject);

        assert(profileBean != null);
    }

    @Test
    public void getOverseerTest() throws Exception {
        Connection connection = open();
        ProfileDAO profileDAO = new ProfileDAO(connection);

        ProfileBean profileBean = profileDAO.get(4, "O");

        JSONObject jsonObject = new JSONObject(profileBean);
        System.out.println(jsonObject);

        assert(profileBean != null);
    }

    @Test
    public void checkTest() throws Exception {
        Connection connection = open();
        ProfileDAO profileDAO = new ProfileDAO(connection);

        boolean check = profileDAO.check("alessandro@gmail.com", "alessandro");

        JSONObject jsonObject = new JSONObject(check);
        System.out.println(jsonObject);

        assert(check == true);
    }

    @Test
    public void getAllNationsTest() throws Exception {
        Connection connection = open();
        ProfileDAO profileDAO = new ProfileDAO(connection);

        Collection<NationBean> nationBeans = profileDAO.getAllNations();

        for(NationBean nationBean: nationBeans) {
            JSONObject jsonObject = new JSONObject(nationBean);
            System.out.println(jsonObject);
        }

        assert(nationBeans != null);
    }

    @Test
    public void getFollowersFromUserTest() throws Exception {
        Connection connection = open();
        ProfileDAO profileDAO = new ProfileDAO(connection);

        Collection<UserBean> users = profileDAO.getFollowersFromUser(5);

        for(UserBean user: users) {
            JSONObject jsonObject = new JSONObject(user);
            System.out.println(jsonObject);
        }

        assert(users != null);
    }

    @Test
    public void getFollowingFromUserTest() throws Exception {
        Connection connection = open();
        ProfileDAO profileDAO = new ProfileDAO(connection);

        Collection<UserBean> users = profileDAO.getFollowingFromUser(2);

        for(UserBean user: users) {
            JSONObject jsonObject = new JSONObject(user);
            System.out.println(jsonObject);
        }

        assert(users != null);
    }

    @Test
    public void getArtistFromUserTest() throws Exception {
        Connection connection = open();
        ProfileDAO profileDAO = new ProfileDAO(connection);

        Collection<ArtistBean> artists = profileDAO.getArtistsFromUser(2);

        for(ArtistBean artist: artists) {
            JSONObject jsonObject = new JSONObject(artist);
            System.out.println(jsonObject);
        }

        assert(artists != null);
    }

    @Test
    public void getFeaturingFromUserTest() throws Exception {
        Connection connection = open();
        ProfileDAO profileDAO = new ProfileDAO(connection);

        Collection<ArtistBean> artists = profileDAO.getFeaturingFromTrack(1);

        for(ArtistBean artist: artists) {
            JSONObject jsonObject = new JSONObject(artist);
            System.out.println(jsonObject);
        }

        assert(artists != null);
    }

    @Test
    public void getFromAlbumTest() throws Exception {
        Connection connection = open();
        ProfileDAO profileDAO = new ProfileDAO(connection);

        ArtistBean artist = profileDAO.getFromAlbum(1);

        JSONObject jsonObject = new JSONObject(artist);
        System.out.println(jsonObject);

        assert(artist != null);
    }

    @Test
    public void getFromTrackTest() throws Exception {
        Connection connection = open();
        ProfileDAO profileDAO = new ProfileDAO(connection);

        ArtistBean artist = profileDAO.getFromTrack(1);

        JSONObject jsonObject = new JSONObject(artist);
        System.out.println(jsonObject);

        assert(artist != null);
    }

    @Test
    public void getHostFromPlaylistTest() throws Exception {
        Connection connection = open();
        ProfileDAO profileDAO = new ProfileDAO(connection);

        UserBean user = profileDAO.getHostFromPlaylist(1);

        JSONObject jsonObject = new JSONObject(user);
        System.out.println(jsonObject);

        assert(user != null);
    }

    @Test
    public void getGuestsFromPlaylistTest() throws Exception {
        Connection connection = open();
        ProfileDAO profileDAO = new ProfileDAO(connection);

        Collection<UserBean> users = profileDAO.getGuestsFromPlaylist(1);

        for(UserBean user: users) {
            JSONObject jsonObject = new JSONObject(user);
            System.out.println(jsonObject);
        }

        assert(users != null);
    }

    @Test
    public void addUserTest() throws Exception {
        Connection connection = open();
        ProfileDAO profileDAO = new ProfileDAO(connection);

        connection.setAutoCommit(false);

        UserBean userBean = new UserBean(0, "email", "password", "alias", "2000-01-01", new NationBean("ITA", "Italy"), false);
        boolean outcome = profileDAO.add(userBean);

        connection.rollback();
        assert(outcome == true);
    }

    @Test
    public void addArtistTest() throws Exception {
        Connection connection = open();
        ProfileDAO profileDAO = new ProfileDAO(connection);

        connection.setAutoCommit(false);

        ArtistBean artistBean = new ArtistBean(0, "email", "password", "alias", "bio");
        boolean outcome = profileDAO.add(artistBean);

        connection.rollback();
        assert(outcome == true);
    }

    @Test
    public void addOverseerTest() throws Exception {
        Connection connection = open();
        ProfileDAO profileDAO = new ProfileDAO(connection);

        connection.setAutoCommit(false);

        OverseerBean overseerBean = new OverseerBean(0, "email", "password");
        boolean outcome = profileDAO.add(overseerBean);

        connection.rollback();
        assert(outcome == true);
    }

    @Test
    public void removeUserTest() throws Exception {
        Connection connection = open();
        ProfileDAO profileDAO = new ProfileDAO(connection);

        connection.setAutoCommit(false);

        profileDAO.removeUser(2);

        connection.rollback();
    }

    @Test
    public void removeArtistTest() throws Exception {
        Connection connection = open();
        ProfileDAO profileDAO = new ProfileDAO(connection);

        connection.setAutoCommit(false);

        profileDAO.removeArtist(3);

        connection.rollback();
    }

}

