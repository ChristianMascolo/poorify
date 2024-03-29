package playlist;

import track.TrackBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.TreeSet;

public class PlaylistDAO {
    private Connection connection;

    public PlaylistDAO(Connection connection) {
        this.connection = connection;
    }

    //Seleziona Playlist in base all'id
    public PlaylistBean get(int id) throws SQLException {
        PlaylistBean playlist = null;
        
        PreparedStatement stmt = connection.prepareStatement("" +
                "SELECT p.id AS id, p.title AS title, p.tracks AS tracks, p.duration AS duration, p.isPublic AS isPublic, p.isCollaborative AS isCollaborative, p.lastAccessTime AS lastAccessTime " +
                "FROM Playlist p " +
                "WHERE p.id = ?");
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        if(rs.next())
            playlist = resultToBean(rs);

        rs.close(); stmt.close();
        return playlist;
    }

    //create playlist
    public boolean add(int user, String title) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Playlist (enduser, title) VALUES (?, ?)");
        statement.setInt(1, user);
        statement.setString(2, title);
        return statement.executeUpdate() > 0;
    }

    public int getLastFromUser(int user) throws SQLException {
        int id = 0;
        PreparedStatement stmt = connection.prepareStatement("SELECT p.id AS id FROM Playlist p WHERE p.enduser = ? ORDER BY p.id DESC LIMIT 1");
        stmt.setInt(1, user);
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
            id = rs.getInt("id");
        return id;
    }

    //Seleziona le Playlist in base all'utente
    public Collection<PlaylistBean> getFromUser(int id) throws SQLException{
        Collection<PlaylistBean> playlists = new TreeSet<>((PlaylistBean a, PlaylistBean b) -> b.getLastAccessTime().compareTo(a.getLastAccessTime()));

        PreparedStatement stat = connection.prepareStatement("" +
                "SELECT p.id AS id, p.title AS title, p.tracks AS tracks, p.duration AS duration, p.isPublic AS isPublic, p.isCollaborative AS isCollaborative, p.lastAccessTime AS lastAccessTime " +
                "FROM Playlist p " +
                "WHERE p.enduser = ? " +
                "UNION " +
                "SELECT p.id AS id, p.title AS title, p.tracks AS tracks, p.duration AS duration, p.isPublic AS isPublic, p.isCollaborative AS isCollaborative, p.lastAccessTime AS lastAccessTime " +
                "FROM Playlist p, Guests g " +
                "WHERE p.id = g.playlist " +
                "AND g.guest = ?");
        stat.setInt(1, id);
        stat.setInt(2, id);

        ResultSet rs = stat.executeQuery();
        while(rs.next())
            playlists.add(resultToBean(rs));

        rs.close(); stat.close();
        return playlists;
    }

    //Seleziona tutte le playlist a cui un utente ha messo like
    public Collection<PlaylistBean> getFromLikes(int id) throws SQLException{
        Collection<PlaylistBean> playlists = new TreeSet<>((PlaylistBean a, PlaylistBean b) -> a.getTitle().compareTo(b.getTitle()));

        PreparedStatement stat = connection.prepareStatement("" +
                "SELECT p.id AS id, p.title AS title, p.tracks AS tracks, p.duration AS duration, p.isPublic AS isPublic, p.isCollaborative AS isCollaborative, p.lastAccessTime AS lastAccessTime " +
                "FROM Playlist p, Likes l " +
                "WHERE p.id = l.playlist " +
                "AND l.enduser = ? ");
        stat.setInt(1, id);

        ResultSet rs = stat.executeQuery();
        while(rs.next())
            playlists.add(resultToBean(rs));

        rs.close(); stat.close();
        return playlists;
    }

    //Seleziona tutte le playlist pubbliche relative ad un utente
    public Collection<PlaylistBean> getPublicFromUser(int id) throws SQLException{
        Collection<PlaylistBean> playlists = new TreeSet<>((PlaylistBean a, PlaylistBean b) -> a.getTitle().compareTo(b.getTitle()));

        PreparedStatement stat = connection.prepareStatement("" +
                "SELECT p.id AS id, p.title AS title, p.tracks AS tracks, p.duration AS duration, p.isPublic AS isPublic, p.isCollaborative AS isCollaborative, p.lastAccessTime AS lastAccessTime " +
                "FROM Playlist p " +
                "WHERE p.isPublic = 1 AND p.enduser = ? " +
                "UNION " +
                "SELECT p.id AS id, p.title AS title, p.tracks AS tracks, p.duration AS duration, p.isPublic AS isPublic, p.isCollaborative AS isCollaborative, p.lastAccessTime AS lastAccessTime " +
                "FROM Playlist p, Guests g " +
                "WHERE p.id = g.playlist AND p.isPublic = 1 AND g.guest = ?"
                );
        stat.setInt(1, id);
        stat.setInt(2, id);

        ResultSet rs = stat.executeQuery();
        while(rs.next())
            playlists.add(resultToBean(rs));

        rs.close(); stat.close();
        return playlists;
    }

    //Seleziona tutti i bean ADDED relativi a una Playlist
    public Collection<AddedBeanProxy> getAdded(int id) throws SQLException{
        Collection<AddedBeanProxy> beans = new TreeSet<>((AddedBeanProxy a, AddedBeanProxy b) -> a.getDate().compareTo(b.getDate()) != 0 ? a.getDate().compareTo(b.getDate()) : -1);

        PreparedStatement stat = connection.prepareStatement("SELECT * FROM Added a WHERE a.playlist = ?");
        stat.setInt(1, id);

        ResultSet rs = stat.executeQuery();
        while(rs.next())
            beans.add(resultToAddedBeanProxy(rs));

        rs.close(); stat.close();
        return beans;
    }

    public int getLikes(int id) throws SQLException {
        int likes = 0;

        PreparedStatement stat = connection.prepareStatement("SELECT * FROM PlaylistPublic p WHERE p.playlist = ?");
        stat.setInt(1, id);

        ResultSet rs = stat.executeQuery();
        if(rs.next())
            likes = rs.getInt("likes");

        rs.close(); stat.close();
        return likes;
    }

    public boolean addTrackToPlaylist(int user, int track, int playlist) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Added (enduser, track, playlist) VALUES (?, ?, ?)");
        stmt.setInt(1, user);
        stmt.setInt(2, track);
        stmt.setInt(3, playlist);
        boolean outcome = stmt.executeUpdate() > 0;
        stmt.close();

        PreparedStatement update = connection.prepareStatement("" +
                "UPDATE Playlist " +
                "SET tracks = (SELECT COUNT(*) FROM Added a WHERE a.playlist = ? GROUP BY a.playlist), " +
                "duration = (SELECT SUM(t.duration) FROM Added a, Track t WHERE a.track = t.id AND a.playlist = ? GROUP BY a.playlist) WHERE id = ?");
        update.setInt(1, playlist);
        update.setInt(2, playlist);
        update.setInt(3, playlist);
        update.executeUpdate();
        update.close();

        return outcome;
    }

    public void removeTrackFromPlaylist(int track, int playlist) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Added WHERE track = ? AND playlist = ?");
        statement.setInt(1,track);
        statement.setInt(2,playlist);
        statement.executeUpdate();
        statement.close();

        PreparedStatement update = connection.prepareStatement("" +
                "UPDATE Playlist " +
                "SET tracks = (SELECT COUNT(*) FROM Added a WHERE a.playlist = ? GROUP BY a.playlist), " +
                "duration = (SELECT SUM(t.duration) FROM Added a, Track t WHERE a.track = t.id AND a.playlist = ? GROUP BY a.playlist) WHERE id = ?");
        update.setInt(1, playlist);
        update.setInt(2, playlist);
        update.setInt(3, playlist);
        update.executeUpdate();
        update.close();
    }

    private PlaylistBean resultToBean(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        int tracks = rs.getInt("tracks");
        int duration = rs.getInt("duration");
        boolean isPublic = rs.getBoolean("isPublic");
        boolean isCollaborative = rs.getBoolean("isCollaborative");
        String lastAccessTime = rs.getString("lastAccessTime");

        return new PlaylistBean(id, title, tracks, duration, isPublic, isCollaborative, lastAccessTime);
    }

    private AddedBeanProxy resultToAddedBeanProxy(ResultSet rs) throws SQLException {
        int user = rs.getInt("enduser");
        int track = rs.getInt("track");
        int playlist = rs.getInt("playlist");
        String date = rs.getString("date");

        return new AddedBeanProxy(user, track, playlist, date);
    }

    public boolean remove(int id) throws SQLException{
        PreparedStatement stLikes = connection.prepareStatement("DELETE FROM Likes WHERE playlist = ?");
        stLikes.setInt(1, id);
        stLikes.executeUpdate();
        stLikes.close();

        PreparedStatement stGuests = connection.prepareStatement("DELETE FROM Guests WHERE playlist = ?");
        stGuests.setInt(1, id);
        stGuests.executeUpdate();
        stGuests.close();

        PreparedStatement stPublic = connection.prepareStatement("DELETE FROM PlaylistPublic WHERE playlist = ?");
        stPublic.setInt(1, id);
        stPublic.executeUpdate();
        stPublic.close();

        PreparedStatement stCollab = connection.prepareStatement("DELETE FROM PlaylistCollaborative WHERE playlist = ?");
        stCollab.setInt(1, id);
        stCollab.executeUpdate();
        stCollab.close();

        PreparedStatement stAdded = connection.prepareStatement("DELETE FROM Added WHERE playlist = ?");
        stAdded.setInt(1, id);
        stAdded.executeUpdate();
        stAdded.close();

        PreparedStatement statement = connection.prepareStatement("DELETE FROM Playlist WHERE id = ?");
        statement.setInt(1,id);
        boolean outcome = statement.executeUpdate() > 0;
        statement.close();

        return outcome;
    }

    public boolean like(int user, int playlist) throws SQLException {
        boolean outcome = false;
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Likes VALUES (?, ?)");
        statement.setInt(1, user);
        statement.setInt(2, playlist);
        outcome = statement.executeUpdate() > 0;
        statement.close();

        if(outcome) {
            PreparedStatement increment = connection.prepareStatement("UPDATE PlaylistPublic SET likes = likes + 1 WHERE playlist = ?");
            increment.setInt(1, playlist);
            increment.executeUpdate();
            increment.close();
        }

        return outcome;
    }

    public boolean unlike(int user, int playlist) throws SQLException {
        boolean outcome = false;
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Likes WHERE enduser = ? AND playlist = ?");
        statement.setInt(1, user);
        statement.setInt(2, playlist);
        outcome = statement.executeUpdate() > 0;
        statement.close();

        if(outcome) {
            PreparedStatement decrement = connection.prepareStatement("UPDATE PlaylistPublic SET likes = likes - 1 WHERE playlist = ?");
            decrement.setInt(1, playlist);
            decrement.executeUpdate();
            decrement.close();
        }

        return outcome;
    }

    public boolean checkLike(int user, int playlist) throws SQLException {
        boolean outcome = false;
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Likes WHERE enduser = ? AND playlist = ?");
        statement.setInt(1, user);
        statement.setInt(2, playlist);
        ResultSet rs = statement.executeQuery();
        outcome = rs.next();
        rs.close(); statement.close();
        return outcome;
    }

    public boolean setPublic(int id) throws SQLException{
        boolean outcome = false;

        PreparedStatement statement = connection.prepareStatement("UPDATE Playlist SET isPublic = 1 WHERE id = ?");
        statement.setInt(1,id);

        if(statement.executeUpdate() > 0) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO PlaylistPublic (playlist) VALUES (?)");
            stmt.setInt(1, id);
            outcome = stmt.executeUpdate() > 0;
            stmt.close();
        }

        statement.close();
        return outcome;
    }

    public boolean setPrivate(int id) throws SQLException{
        boolean outcome = false;

        PreparedStatement stmtLikes = connection.prepareStatement("DELETE FROM Likes WHERE playlist = ?");
        stmtLikes.setInt(1, id);
        stmtLikes.executeUpdate();
        stmtLikes.close();

        PreparedStatement statement = connection.prepareStatement("DELETE FROM PlaylistPublic WHERE playlist = ?");
        statement.setInt(1, id);
        if(statement.executeUpdate() > 0) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE Playlist SET isPublic = 0 WHERE id = ?");
            stmt.setInt(1, id);
            outcome = stmt.executeUpdate() > 0;
            stmt.close();
        }

        statement.close();
        return outcome;
    }

    public boolean setCollaborative(int id) throws SQLException{
        boolean outcome = false;

        PreparedStatement statement = connection.prepareStatement("UPDATE Playlist SET isCollaborative = 1 WHERE id = ?");
        statement.setInt(1,id);
        if(statement.executeUpdate() > 0) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO PlaylistCollaborative (playlist) VALUES (?)");
            stmt.setInt(1, id);
            outcome = stmt.executeUpdate() > 0;
            stmt.close();
        }

        statement.close();
        return outcome;
    }

    public boolean setSingle(int id) throws SQLException{
        boolean outcome = false;

        PreparedStatement stmtAdded = connection.prepareStatement("DELETE FROM Added WHERE playlist = ? AND e" +
                "nduser IN (SELECT g.guest FROM Guests g WHERE g.playlist = ?) ");
        stmtAdded.setInt(1, id);
        stmtAdded.setInt(2, id);
        stmtAdded.executeUpdate();
        stmtAdded.close();

        PreparedStatement stmtGuests = connection.prepareStatement("DELETE FROM Guests WHERE playlist = ?");
        stmtGuests.setInt(1, id);
        stmtGuests.executeUpdate();
        stmtGuests.close();

        PreparedStatement statement = connection.prepareStatement("DELETE FROM PlaylistCollaborative WHERE playlist = ?");
        statement.setInt(1, id);
        if(statement.executeUpdate() > 0) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE Playlist SET isCollaborative = 0 WHERE id = ?");
            stmt.setInt(1, id);
            outcome = stmt.executeUpdate() > 0;
            stmt.close();
        }

        statement.close();
        return outcome;
    }

    public int getCountFromUser(int id) throws SQLException {
        int count = 0;

        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) AS count FROM Playlist p WHERE p.enduser = ?");
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        if(rs.next())
            count = rs.getInt("count");

        rs.close(); stmt.close();
        return count;
    }

    public boolean changeTitle(int id, String title) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE Playlist SET title = ? WHERE id = ?");
        stmt.setString(1, title);
        stmt.setInt(2, id);
        return stmt.executeUpdate() > 0;
    }

    public boolean addGuest(int host, int guest, int playlist) throws SQLException {
        boolean outcome = false;
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Guests (host, guest, playlist) VALUES (?, ?, ?)");
        stmt.setInt(1, host);
        stmt.setInt(2, guest);
        stmt.setInt(3, playlist);
        outcome = stmt.executeUpdate() > 0;
        stmt.close();
        return outcome;
    }

    public void updateLastAccess(int playlist) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("UPDATE Playlist SET lastAccessTime = CURRENT_TIMESTAMP WHERE id = ?");
        stmt.setInt(1, playlist);
        stmt.executeUpdate();
        stmt.close();
    }

    public Collection<PlaylistBean> searchPlaylistsByTitle(String search) throws SQLException {
        Collection<PlaylistBean> playlists = new TreeSet<>((PlaylistBean a, PlaylistBean b) -> a.getTitle().compareTo(b.getTitle()));
        PreparedStatement stmt = connection.prepareStatement("" +
                "SELECT p.id AS id, p.title AS title, p.tracks AS tracks, p.duration AS duration, p.isPublic AS isPublic, p.isCollaborative AS isCollaborative, p.lastAccessTime AS lastAccessTime " +
                "FROM Playlist p " +
                "WHERE p.isPublic = 1 " +
                "AND p.title LIKE ? " +
                "LIMIT 10 ");
        search = '%' + search +  '%';
        stmt.setString(1, search);

        ResultSet rs = stmt.executeQuery();
        while(rs.next())
            playlists.add(resultToBean(rs));

        rs.close(); stmt.close();
        return playlists;
    }

}
