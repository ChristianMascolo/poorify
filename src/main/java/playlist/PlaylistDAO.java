package playlist;

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
                "WHERE p.isPublic = 1 AND p.enduser = ?");
        stat.setInt(1, id);

        ResultSet rs = stat.executeQuery();
        while(rs.next())
            playlists.add(resultToBean(rs));

        rs.close(); stat.close();
        return playlists;
    }

    //Seleziona tutti i bean ADDED relativi a una Playlist
    public Collection<AddedBeanProxy> getAdded(int id) throws SQLException{
        Collection<AddedBeanProxy> beans = new TreeSet<>((AddedBeanProxy a, AddedBeanProxy b) -> a.getDate().compareTo(b.getDate()));

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

    public void deletePlaylist(int id) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Playlist WHERE Playlist.id = ?");
        statement.setInt(1,id);
        statement.executeUpdate();
    }

    public int addLike(int idUser,int idPlaylist) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Likes VALUES (?,?)");

        statement.setInt(1,idUser);
        statement.setInt(2,idPlaylist);

        return statement.executeUpdate();
    }

    public int removeLike(int idUser,int idPlaylist) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Likes AS l WHERE l.enduser = ? AND l.playlist = ?");

        statement.setInt(1,idUser);
        statement.setInt(2,idPlaylist);

        return statement.executeUpdate();
    }

    public int setPublic(int id) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("UPDATE Playlist SET Playlist.isPublic = 1 WHERE Playlist.id = ?");
        statement.setInt(1,id);
        return statement.executeUpdate();
    }

    public int setPrivate(int id) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("UPDATE Playlist SET Playlist.isPublic = 0 WHERE Playlist.id = ?");
        statement.setInt(1,id);
        return statement.executeUpdate();
    }

    public int setCollaborative(int id) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("UPDATE Playlist SET Playlist.isCollaborative = 1 WHERE Playlist.id = ?");
        statement.setInt(1,id);
        return statement.executeUpdate();
    }

    public int setSingle(int id) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("UPDATE Playlist SET Playlist.isCollaborative = 0 WHERE Playlist.id = ?");
        statement.setInt(1,id);
        return statement.executeUpdate();
    }
}
