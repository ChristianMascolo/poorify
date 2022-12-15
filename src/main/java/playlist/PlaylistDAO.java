package playlist;

import profile.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class PlaylistDAO {
    private Connection connection;

    public PlaylistDAO(Connection connection) {
        this.connection = connection;
    }

    //Seleziona Playlist in base all'id
    public PlaylistBean get(int id) throws SQLException {
        PlaylistBean playlist = null;
        PreparedStatement stmt = connection.prepareStatement("SELECT p.title AS title, p.tracks AS tracks, p.duration AS duration, p.isPublic AS isPublic, p.isCollaborative AS isCollaborative FROM Playlist p WHERE p.id = ?");
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        if(rs.next())
            playlist = resultToBean(rs);

        rs.close();
        stmt.close();

        return playlist;
    }

    //Seleziona Playlist in base al titolo
    private PlaylistBean getTitolo(String title) throws SQLException{
        PlaylistBean playlist = null;
        PreparedStatement stat = connection.prepareStatement("SELECT p.title AS title, p.tracks AS tracks, p.duration AS duration, p.isPublic AS isPublic, p.isCollaborative AS isCollaborative FROM Playlist p WHERE p.title = ?");
        stat.setString(1, title);

        ResultSet rs = stat.executeQuery();
        if(rs.next())
            playlist = resultToBean(rs);
        rs.close();
        stat.close();

        return playlist;
    }

    //Seleziona le Playlist in base all'utente host
    private Collection<PlaylistBean> getHost(String host) throws SQLException{
        Collection<PlaylistBean> playlist = null;
        PreparedStatement stat = connection.prepareStatement("SELECT p.title AS title, p.tracks AS tracks, p.duration AS duration, p.isPublic AS isPublic, p.isCollaborative AS isCollaborative FROM Playlist p, Guests g WHERE p.id = pc.playlist AND g.playlist = p.id AND g.host=?");
        stat.setString(1, host);

        ResultSet rs = stat.executeQuery();
        if(rs.next())
            playlist.add(resultToBean(rs));
        rs.close();
        stat.close();

        return playlist;
    }

    //Seleziona tutte le Playlist a cui un utente partecipa come Guest
    private Collection<PlaylistBean> getGuests(int id) throws SQLException{
        Collection<PlaylistBean> playlist = null;
        PreparedStatement stat = connection.prepareStatement("SELECT p.title AS title, p.tracks AS tracks, p.duration AS duration, p.isPublic AS isPublic, p.isCollaborative AS isCollaborative FROM Playlist p, PlaylistCollaborative pc, Guests g WHERE g.playlist = p.id AND p.id = pc.playlist AND g.guest=?");
        stat.setInt(1, id);

        ResultSet rs = stat.executeQuery();
        if(rs.next())
            playlist.add(resultToBean(rs));
        rs.close();
        stat.close();

        return playlist;
    }

    //Seleziona tutte le playlist a cui un utente ha messo like
    private Collection<PlaylistBean> getPlaylistUserLikes(int id) throws SQLException{
        Collection<PlaylistBean> playlist = null;
        PreparedStatement stat = connection.prepareStatement("SELECT p.title AS title, p.tracks AS tracks, p.duration AS duration, p.isPublic AS isPublic, p.isCollaborative AS isCollaborative FROM Playlist p, PlaylistPublic pp, Profile pr, EndUser e, Likes l WHERE pp.playlist = p.id AND p.likes = l.playlist AND e.profile = l.enduser AND pr.id = e.profile AND pr.id=?");
        stat.setInt(1, id);

        ResultSet rs = stat.executeQuery();
        if(rs.next())
            playlist.add(resultToBean(rs));
        rs.close();
        stat.close();

        return playlist;
    }



    //Seleziona tutte le playlist pubbliche relative ad un utente
    private Collection<PlaylistBean> getPublic(int id) throws SQLException{
        Collection<PlaylistBean> playlist = null;

        PreparedStatement stat = connection.prepareStatement("SELECT p.title AS title, p.tracks AS tracks, p.duration AS duration, p.isPublic AS isPublic, p.isCollaborative AS isCollaborative FROM Playlist p, PlaylistPublic pp, EndUser e, Profile pr WHERE pp.playlist = p.id AND p.enduser = e.profile AND e.profile = pr.id AND pr.id =?");
        stat.setInt(1, id);

        ResultSet rs = stat.executeQuery();
        if(rs.next())
            playlist.add(resultToBean(rs));
        rs.close();
        stat.close();

        return playlist;
    }



    //Seleziona tutti i bean ADDED relativi a una Playlist
    private Collection<AddedBean> getAdded(int id) throws SQLException{
        Collection<AddedBean> beans = null;

        PreparedStatement stat = connection.prepareStatement("SELECT ad.enduser, ad.track, ad.playlist, ad.date FROM Playlist p, Added ad WHERE p.id = ad.playlist AND p.id=?");
        stat.setInt(1, id);

        ResultSet rs = stat.executeQuery();
        if(rs.next())
            beans.add(resultToBean1(rs));
        rs.close();
        stat.close();

        return beans;
    }


    private PlaylistBean resultToBean(ResultSet rs) throws SQLException {
        PlaylistBean playlist = null;

        int id = rs.getInt("id");
        String title = rs.getString("title");
        int tracks = rs.getInt("tracks");
        int duration = rs.getInt("duration");
        boolean isPublic = rs.getBoolean("isPublic");
        boolean isCollaborative = rs.getBoolean("isCollaborative");

        return playlist;
    }

    private AddedBean resultToBean1(ResultSet rs) throws SQLException {
        AddedBean bean = null;

        int enduser = rs.getInt("enduser");
        int track = rs.getInt("track");
        int playlist = rs.getInt("playlist");
        int date = rs.getInt("date");

        return bean;
    }


}
