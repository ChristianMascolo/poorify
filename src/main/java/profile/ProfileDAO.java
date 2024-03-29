package profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.TreeSet;

public class ProfileDAO {

    private Connection connection;

    public ProfileDAO(Connection connection) {
        this.connection = connection;
    }

    //RETRIEVE
    public ProfileBean get(int id) throws SQLException {
        ProfileBean profile = null;

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Profile p WHERE p.id = ?");
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        if(rs.next())
            profile = get(rs.getInt("id"), rs.getString("role"));

        rs.close(); stmt.close();

        return profile;
    }

    public ProfileBean get(String email, String password) throws SQLException {
        ProfileBean profile = null;

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Profile p WHERE p.email = ? AND p.password = ?");
        stmt.setString(1, email);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();
        if(rs.next())
            profile = get(rs.getInt("id"), rs.getString("role"));

        rs.close(); stmt.close();

        return profile;
    }

    public ProfileBean get(int id, String role) throws SQLException {
        ProfileBean profile = null;
        PreparedStatement stmt = null;

        if(role.equals("U")) {
            stmt = connection.prepareStatement("SELECT * FROM Profile p, EndUser u, Nation n " +
                    "WHERE p.id = u.profile AND u.nation = n.iso " +
                    "AND p.id = ?");
        }
        else if(role.equals("A")) {
            stmt = connection.prepareStatement("SELECT * FROM Profile p, Artist a " +
                    "WHERE p.id = a.profile " +
                    "AND p.id = ?");
        }
        else if(role.equals("O")) {
            stmt = connection.prepareStatement("SELECT * FROM Profile p, Overseer o " +
                    "WHERE p.id = o.profile " +
                    "AND p.id = ?");
        }
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        if(rs.next())
            profile = resultToBean(rs);

        rs.close(); stmt.close();

        return profile;
    }

    public boolean check(String email, String password) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Profile p WHERE p.email = ? AND p.password = ?");
        stmt.setString(1, email);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();

        boolean outcome = rs.next();
        rs.close(); stmt.close();
        return outcome;
    }

    public Collection<NationBean> getAllNations() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Nation n");
        ResultSet rs = stmt.executeQuery();

        TreeSet<NationBean> nations = new TreeSet<>((NationBean a, NationBean b) -> a.getName().compareTo(b.getName()));
        while(rs.next())
            nations.add(new NationBean(rs.getString("iso"), rs.getString("name")));

        return nations;
    }

    //RETRIEVE - PART II
    public Collection<UserBean> getFollowersFromUser(int id) throws SQLException {
        Collection<UserBean> followers = new TreeSet<>((UserBean a, UserBean b) -> a.getAlias().compareTo(b.getAlias()));

        PreparedStatement stmt = connection.prepareStatement(" " +
                "SELECT p.id AS id, p.email AS email, p.password AS password, p.role AS role, u.alias AS alias, u.birthdate AS birthdate, n.iso AS iso, n.name AS name, u.isPublic AS isPublic " +
                "FROM Profile p, EndUser u, FollowUser f, Nation n " +
                "WHERE p.id = u.profile AND u.profile = f.follower AND u.nation = n.iso " +
                "AND f.followed = ?");
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        while(rs.next())
            followers.add((UserBean) resultToBean(rs));

        rs.close(); stmt.close();
        return followers;
    }

    public Collection<UserBean> getFollowingFromUser(int id) throws SQLException {
        Collection<UserBean> following = new TreeSet<>((UserBean a, UserBean b) -> a.getAlias().compareTo(b.getAlias()));

        PreparedStatement stmt = connection.prepareStatement(" " +
                "SELECT p.id AS id, p.email AS email, p.password AS password, p.role AS role, u.alias AS alias, u.birthdate AS birthdate, n.iso AS iso, n.name AS name, u.isPublic AS isPublic " +
                "FROM Profile p, EndUser u, FollowUser f, Nation n " +
                "WHERE p.id = u.profile AND u.profile = f.followed AND u.nation = n.iso " +
                "AND f.follower = ?");
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        while(rs.next())
            following.add((UserBean) resultToBean(rs));

        rs.close(); stmt.close();
        return following;
    }

    public Collection<ArtistBean> getArtistsFromUser(int id) throws SQLException {
        Collection<ArtistBean> artists = new TreeSet<>((ArtistBean a, ArtistBean b) -> a.getAlias().compareTo(b.getAlias()));

        PreparedStatement stmt = connection.prepareStatement(" " +
                "SELECT p.id AS id, p.email AS email, p.password AS password, p.role AS role, a.alias AS alias, a.bio AS bio " +
                "FROM Profile p, Artist a, FollowArtist f " +
                "WHERE p.id = a.profile AND a.profile = f.artist " +
                "AND f.enduser = ?");
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        while(rs.next())
            artists.add((ArtistBean) resultToBean(rs));

        rs.close(); stmt.close();
        return artists;
    }

    public Collection<ArtistBean> getFeaturingFromTrack(int id) throws SQLException {
        Collection<ArtistBean> artists = new TreeSet<>((ArtistBean a, ArtistBean b) -> a.getAlias().compareTo(b.getAlias()));

        PreparedStatement stmt = connection.prepareStatement(" " +
                "SELECT p.id AS id, p.email AS email, p.password AS password, p.role AS role, a.alias AS alias, a.bio AS bio " +
                "FROM Profile p, Artist a, Featuring f " +
                "WHERE p.id = a.profile AND a.profile = f.artist " +
                "AND f.track = ?");
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        while(rs.next())
            artists.add((ArtistBean) resultToBean(rs));

        rs.close(); stmt.close();
        return artists;
    }

    public ArtistBean getFromAlbum(int id) throws SQLException {
        ArtistBean artist = null;

        PreparedStatement stmt = connection.prepareStatement(" " +
                "SELECT p.id AS id, p.email AS email, p.password AS password, p.role AS role, a.alias AS alias, a.bio AS bio " +
                "FROM Profile p, Artist a, Album l " +
                "WHERE p.id = a.profile AND a.profile = l.artist " +
                "AND l.id = ?");
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        if(rs.next())
            artist = (ArtistBean) resultToBean(rs);

        rs.close(); stmt.close();
        return artist;
    }

    public ArtistBean getFromTrack(int id) throws SQLException {
        ArtistBean artist = null;

        PreparedStatement stmt = connection.prepareStatement(" " +
                "SELECT p.id AS id, p.email AS email, p.password AS password, p.role AS role, a.alias AS alias, a.bio AS bio " +
                "FROM Profile p, Artist a, Album l, Track t " +
                "WHERE p.id = a.profile AND a.profile = l.artist AND l.id = t.album " +
                "AND t.id = ?");
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        if(rs.next())
            artist = (ArtistBean) resultToBean(rs);

        rs.close(); stmt.close();
        return artist;
    }

    public UserBean getHostFromPlaylist(int id) throws SQLException {
        UserBean host = null;

        PreparedStatement stmt = connection.prepareStatement(" " +
                "SELECT p.id AS id, p.email AS email, p.password AS password, p.role AS role, u.alias AS alias, u.birthdate AS birthdate, n.iso AS iso, n.name AS name, u.isPublic AS isPublic " +
                "FROM Profile p, EndUser u, Playlist g, Nation n " +
                "WHERE p.id = u.profile AND u.profile = g.enduser AND u.nation = n.iso " +
                "AND g.id = ?");
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        if(rs.next())
            host = (UserBean) resultToBean(rs);

        rs.close(); stmt.close();
        return host;
    }

    public Collection<UserBean> getGuestsFromPlaylist(int id) throws SQLException {
        Collection<UserBean> guests = new TreeSet<>((UserBean a, UserBean b) -> a.getAlias().compareTo(b.getAlias()));

        PreparedStatement stmt = connection.prepareStatement(" " +
                "SELECT p.id AS id, p.email AS email, p.password AS password, p.role AS role, u.alias AS alias, u.birthdate AS birthdate, n.iso AS iso, n.name AS name, u.isPublic AS isPublic " +
                "FROM Profile p, EndUser u, Guests g, Nation n " +
                "WHERE p.id = u.profile AND u.profile = g.guest AND u.nation = n.iso " +
                "AND g.playlist = ?");
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        while(rs.next())
            guests.add((UserBean) resultToBean(rs));

        rs.close(); stmt.close();
        return guests;
    }

    //CREATE
    public boolean add(UserBean user) throws SQLException {
        boolean outcome = false;
        int id = addProfile(user.getEmail(), user.getPassword(), "U");
        if(id > 0) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO EndUser (profile, alias, birthdate, nation, isPublic) VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, id);
            stmt.setString(2, user.getAlias());
            stmt.setString(3, user.getBirthdate());
            stmt.setString(4, user.getNation().getIso());
            stmt.setBoolean(5, user.isPublic());
            outcome = stmt.executeUpdate() > 0;
            stmt.close();
        }
        return outcome;
    }

    public boolean add(ArtistBean artist) throws SQLException {
        boolean outcome = false;
        int id = addProfile(artist.getEmail(), artist.getPassword(), "A");
        if(id > 0) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Artist (profile, alias, bio) VALUES (?, ?, ?)");
            stmt.setInt(1, id);
            stmt.setString(2, artist.getAlias());
            stmt.setString(3, artist.getBio());
            outcome = stmt.executeUpdate() > 0;
            stmt.close();
        }
        return outcome;
    }

    public boolean add(OverseerBean overseer) throws SQLException {
        boolean outcome = false;
        int id = addProfile(overseer.getEmail(), overseer.getPassword(), "O");
        if(id > 0) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Overseer (profile) VALUES (?)");
            stmt.setInt(1, id);
            outcome = stmt.executeUpdate() > 0;
            stmt.close();
        }
        return outcome;
    }

    //SERVICE METHODS
    private int addProfile(String email, String password, String role) throws SQLException {
        int id = -1;

        //CREATE NEW PROFILE
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Profile (email, password, role) VALUES (?, ?, ?)");
        stmt.setString(1, email);
        stmt.setString(2, password);
        stmt.setString(3, role);

        if(stmt.executeUpdate() > 0) {
            stmt.close();

            //GET ID FROM THE NEW PROFILE
            stmt = connection.prepareStatement("SELECT * FROM Profile p WHERE p.email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                id = rs.getInt("id");

            rs.close(); stmt.close();

        } else stmt.close();

        return id;
    }

    private ProfileBean resultToBean(ResultSet rs) throws SQLException {
        ProfileBean profile = null;

        int id = rs.getInt("id");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String role = rs.getString("role");

        if(role.equals("U")) {
            String alias = rs.getString("alias");
            String birthdate = rs.getString("birthdate");

            String iso = rs.getString("iso");
            String name = rs.getString("name");
            NationBean nation = new NationBean(iso, name);

            boolean isPublic = rs.getBoolean("isPublic");

            UserBean user = new UserBean(id, email, password, alias, birthdate, nation, isPublic);
            profile = user;
        }
        else if(role.equals("A")) {
            String alias = rs.getString("alias");
            String bio = rs.getString("bio");

            ArtistBean artist = new ArtistBean(id, email, password, alias, bio);
            profile = artist;
        }
        else if(role.equals("O")) {
            OverseerBean overseer = new OverseerBean(id, email, password);
            profile = overseer;
        }

        return profile;
    }

    public boolean followArtist(int user, int artist) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("INSERT INTO FollowArtist (enduser, artist) VALUES (?, ?)");
        statement.setInt(1, user);
        statement.setInt(2, artist);
        return statement.executeUpdate() > 0;
    }

    public boolean unfollowArtist(int user, int artist) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM FollowArtist WHERE endUser = ? AND artist = ?");
        statement.setInt(1, user);
        statement.setInt(2, artist);
        return statement.executeUpdate() > 0;
    }

    public boolean followUser(int follower, int followed) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("INSERT INTO FollowUser(follower, followed) VALUES (?, ?)");
        statement.setInt(1,follower);
        statement.setInt(2,followed);
        return statement.executeUpdate() > 0;
    }

    public boolean unfollowUser(int follower, int followed) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM FollowUser WHERE follower = ? AND followed = ?");
        statement.setInt(1,follower);
        statement.setInt(2,followed);
        return statement.executeUpdate() > 0;
    }

    public Collection<UserBean> searchUsersByAlias(String search) throws SQLException {
        Collection<UserBean> users = new TreeSet<>((UserBean a, UserBean b) -> a.getAlias().compareTo(b.getAlias()));
        PreparedStatement stmt = connection.prepareStatement("" +
                "SELECT p.id AS id, p.email AS email, p.password AS password, p.role AS role, u.alias AS alias, u.birthdate AS birthdate, n.iso AS iso, n.name AS name, u.isPublic AS isPublic " +
                "FROM Profile p, EndUser u, Nation n " +
                "WHERE p.id = u.profile AND u.nation = n.iso " +
                "AND u.alias LIKE ?" +
                "LIMIT 10 ");
        search = '%' + search +  '%';
        stmt.setString(1, search);

        ResultSet rs = stmt.executeQuery();
        while(rs.next())
            users.add((UserBean) resultToBean(rs));

        rs.close(); stmt.close();
        return users;
    }

    public Collection<ArtistBean> searchArtistsByAlias(String search) throws SQLException {
        Collection<ArtistBean> artists = new TreeSet<>((ArtistBean a, ArtistBean b) -> a.getAlias().compareTo(b.getAlias()));
        PreparedStatement stmt = connection.prepareStatement("" +
                "SELECT p.id AS id, p.email AS email, p.password AS password, p.role AS role, a.alias AS alias, a.bio AS bio " +
                "FROM Profile p, Artist a " +
                "WHERE p.id = a.profile " +
                "AND a.alias LIKE ? " +
                "LIMIT 10");
        search = '%' + search +  '%';
        stmt.setString(1, search);

        ResultSet rs = stmt.executeQuery();
        while(rs.next())
            artists.add((ArtistBean) resultToBean(rs));

        rs.close(); stmt.close();
        return artists;
    }

    public void changeAlias(int id, String alias) throws SQLException {

        PreparedStatement st1 = connection.prepareStatement("UPDATE EndUser SET alias = ? WHERE profile = ?");
        st1.setString(1, alias);
        st1.setInt(2, id);
        st1.executeUpdate();
        st1.close();

        PreparedStatement st2 = connection.prepareStatement("UPDATE Artist SET alias = ? WHERE profile = ?");
        st2.setString(1, alias);
        st2.setInt(2, id);
        st2.executeUpdate();
        st2.close();

    }

    public void changePassword(int id, String password) throws SQLException {

        PreparedStatement st1 = connection.prepareStatement("UPDATE Profile SET password = ? WHERE id = ?");
        st1.setString(1, password);
        st1.setInt(2, id);
        st1.executeUpdate();
        st1.close();

    }

    public void changePublic(int id, boolean isPublic) throws SQLException {
        PreparedStatement st1 = connection.prepareStatement("UPDATE EndUser SET isPublic = ? WHERE profile = ?");
        st1.setInt(1, isPublic ? 1 : 0);
        st1.setInt(2, id);
        st1.executeUpdate();
        st1.close();
    }

    public void changeBio(int id, String bio) throws SQLException {
        PreparedStatement st1 = connection.prepareStatement("UPDATE Artist SET bio = ? WHERE profile = ?");
        st1.setString(1, bio);
        st1.setInt(2, id);
        st1.executeUpdate();
        st1.close();
    }

    public boolean checkFollowing(int user, int followed) throws SQLException {
        boolean outcome = false;
        PreparedStatement statement = connection.prepareStatement("" +
                "SELECT a.enduser FROM FollowArtist a WHERE a.enduser = ? AND a.artist = ? " +
                "UNION " +
                "SELECT u.follower FROM FollowUser u WHERE u.follower = ? AND u.followed = ?");
        statement.setInt(1, user);
        statement.setInt(2, followed);
        statement.setInt(3, user);
        statement.setInt(4, followed);
        ResultSet rs = statement.executeQuery();
        outcome = rs.next();
        rs.close(); statement.close();
        return outcome;
    }

    public void removeUser(int id) throws SQLException {
        PreparedStatement stLikes = connection.prepareStatement("DELETE FROM Likes WHERE enduser = ?");
        stLikes.setInt(1, id);
        stLikes.executeUpdate();
        stLikes.close();

        PreparedStatement stAdded = connection.prepareStatement("DELETE FROM Added WHERE enduser = ?");
        stAdded.setInt(1, id);
        stAdded.executeUpdate();
        stAdded.close();

        PreparedStatement stFollowArtist = connection.prepareStatement("DELETE FROM FollowArtist WHERE enduser = ?");
        stFollowArtist.setInt(1, id);
        stFollowArtist.executeUpdate();
        stFollowArtist.close();

        PreparedStatement stFollowUser = connection.prepareStatement("DELETE FROM FollowUser WHERE follower = ? OR followed = ?");
        stFollowUser.setInt(1, id);
        stFollowUser.setInt(2, id);
        stFollowUser.executeUpdate();
        stFollowUser.close();

        PreparedStatement stHost = connection.prepareStatement("DELETE FROM Guests WHERE host = ?");
        stHost.setInt(1, id);
        stHost.executeUpdate();
        stHost.close();

        PreparedStatement stGuests = connection.prepareStatement("DELETE FROM Guests WHERE guest = ?");
        stGuests.setInt(1, id);
        stGuests.executeUpdate();
        stGuests.close();

        PreparedStatement stPlays = connection.prepareStatement("DELETE FROM Plays WHERE enduser = ?");
        stPlays.setInt(1, id);
        stPlays.executeUpdate();
        stPlays.close();

        PreparedStatement stPlaylistCollab = connection.prepareStatement("DELETE FROM playlistcollaborative WHERE playlist IN (SELECT p.id FROM playlist p WHERE p.enduser = ?)");
        stPlaylistCollab.setInt(1, id);
        stPlaylistCollab.executeUpdate();
        stPlaylistCollab.close();

        PreparedStatement stPlaylistPublic = connection.prepareStatement("DELETE FROM playlistpublic WHERE playlist IN (SELECT p.id FROM playlist p WHERE p.enduser = ?)");
        stPlaylistPublic.setInt(1, id);
        stPlaylistPublic.executeUpdate();
        stPlaylistPublic.close();

        PreparedStatement stPlaylist = connection.prepareStatement("DELETE FROM playlist WHERE enduser = ?");
        stPlaylist.setInt(1, id);
        stPlaylist.executeUpdate();
        stPlaylist.close();

        PreparedStatement stUser = connection.prepareStatement("DELETE FROM EndUser WHERE profile = ?");
        stUser.setInt(1, id);
        stUser.executeUpdate();
        stUser.close();

        PreparedStatement stProfile = connection.prepareStatement("DELETE FROM Profile WHERE id = ?");
        stProfile.setInt(1, id);
        stProfile.executeUpdate();
        stProfile.close();
    }

    public void removeArtist(int id) throws SQLException {

        PreparedStatement stAdded = connection.prepareStatement("DELETE FROM Added WHERE track IN (SELECT t.id AS id FROM Track t, Album a WHERE t.album = a.id AND a.artist = ? UNION SELECT t.id AS id FROM Track t, Featuring f WHERE t.id = f.track AND f.artist = ?)");
        stAdded.setInt(1, id);
        stAdded.setInt(2, id);
        stAdded.executeUpdate();
        stAdded.close();

        PreparedStatement stFollowArtist = connection.prepareStatement("DELETE FROM FollowArtist WHERE artist = ?");
        stFollowArtist.setInt(1, id);
        stFollowArtist.executeUpdate();
        stFollowArtist.close();

        PreparedStatement stPlays =  connection.prepareStatement("DELETE FROM Plays WHERE track IN (SELECT t.id AS id FROM Track t, Album a WHERE t.album = a.id AND a.artist = ? UNION SELECT t.id AS id FROM Track t, Featuring f WHERE t.id = f.track AND f.artist = ?)");
        stPlays.setInt(1, id);
        stPlays.setInt(2, id);
        stPlays.executeUpdate();
        stPlays.close();

        PreparedStatement stFeaturing = connection.prepareStatement("DELETE FROM Featuring WHERE artist = ?");
        stFeaturing.setInt(1, id);
        stFeaturing.executeUpdate();
        stFeaturing.close();

        PreparedStatement stTracks = connection.prepareStatement("DELETE FROM Track WHERE album IN (SELECT a.id AS id FROM Album a WHERE a.artist = ?)");
        stTracks.setInt(1, id);
        stTracks.executeUpdate();
        stTracks.close();

        PreparedStatement stAlbums = connection.prepareStatement("DELETE FROM Album WHERE artist = ?");
        stAlbums.setInt(1, id);
        stAlbums.executeUpdate();
        stAlbums.close();

        PreparedStatement stArtist = connection.prepareStatement("DELETE FROM Artist WHERE profile = ?");
        stArtist.setInt(1, id);
        stArtist.executeUpdate();
        stArtist.close();

        PreparedStatement stProfile = connection.prepareStatement("DELETE FROM Profile WHERE id = ?");
        stProfile.setInt(1, id);
        stProfile.executeUpdate();
        stProfile.close();
    }

}

