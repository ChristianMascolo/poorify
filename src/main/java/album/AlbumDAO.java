package album;

import track.TrackBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.TreeSet;

public class AlbumDAO {

    private Connection connection;

    public AlbumDAO(Connection connection) {
        this.connection = connection;
    }

    public AlbumBean get(int id) throws SQLException {
        AlbumBean album = null;

        PreparedStatement stmt = connection.prepareStatement(" SELECT * FROM Album a WHERE a.id = ?");
        stmt.setInt(  1, id);

        ResultSet rs = stmt.executeQuery();
        if(rs.next())
            album = resultToBean(rs);
        rs.close(); stmt.close();

        return album;
    }

    public Collection<AlbumBean> getFromArtist(int id)  throws SQLException{
        Collection<AlbumBean> albums = new TreeSet<AlbumBean>((AlbumBean a, AlbumBean b) -> a.getYear() - b.getYear());

        PreparedStatement stmt = connection.prepareStatement("SELECT al.id AS id, al.title AS title, al.tracks AS tracks, al.duration AS duration, al.year AS year, al.type AS type " +
                    "FROM Album al, Artist ar " +
                    "WHERE al.artist = ar.profile " +
                    "AND ar.profile = ?");
        stmt.setInt(1,id);

        ResultSet rs = stmt.executeQuery();
        while(rs.next())
            albums.add(resultToBean(rs));
        rs.close(); stmt.close();

        return  albums;
    }

    public AlbumBean getFromTrack(int id) throws SQLException{
        AlbumBean album = null;

        PreparedStatement stmt = connection.prepareStatement("SELECT a.id AS id, a.title AS title, a.tracks AS tracks, a.duration AS duration, a.year AS year, a.type AS type " +
                "FROM Track t, Album a " +
                "WHERE t.album = a.id " +
                "AND t.id = ?");
        stmt.setInt(1,id);

        ResultSet rs = stmt.executeQuery();
        if(rs.next())
            album = resultToBean(rs);
        rs.close(); stmt.close();

        return  album;
    }

    private AlbumBean resultToBean(ResultSet rs) throws SQLException {

        int id = rs.getInt("id");
        String title = rs.getString("title");
        int tracks = rs.getInt("tracks");
        int duration = rs.getInt("duration");
        int year = rs.getInt("year");
        String type = rs.getString("type");

        return new AlbumBean(id, title, tracks, duration, year, type);
    }

    public Collection<AlbumBean> searchAlbumsByTitle(String search) throws SQLException {
        Collection<AlbumBean> albums = new TreeSet<>((AlbumBean a, AlbumBean b) -> a.getTitle().compareTo(b.getTitle()));
        PreparedStatement stmt = connection.prepareStatement("" +
                "SELECT a.id AS id, a.title AS title, a.tracks AS tracks, a.duration AS duration, a.year AS year, a.type AS type " +
                "FROM Album a " +
                "WHERE a.title LIKE ? " +
                "LIMIT 10");
        search = '%' + search +  '%';
        stmt.setString(1, search);

        ResultSet rs = stmt.executeQuery();
        while(rs.next())
            albums.add((AlbumBean) resultToBean(rs));

        rs.close(); stmt.close();
        return albums;
    }

    public boolean add(int artist, String title, int tracks, int duration, int year, String type) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement(" " +
                "INSERT INTO Album (artist, title, tracks, duration, year, type) VALUES (?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, artist);
        stmt.setString(2, title);
        stmt.setInt(3, tracks);
        stmt.setInt(4, duration);
        stmt.setInt(5, year);
        stmt.setString(6, type);

        boolean outcome = stmt.executeUpdate() > 0;
        stmt.close();

        return outcome;
    }

    public int get(int artist, String title) throws SQLException {
        int outcome = 0;

        PreparedStatement stmt = connection.prepareStatement("SELECT a.id AS id FROM Album a WHERE a.artist = ? AND a.title = ?");
        stmt.setInt(1, artist);
        stmt.setString(2, title);

        ResultSet rs = stmt.executeQuery();
        if(rs.next())
            outcome = rs.getInt("id");

        rs.close(); stmt.close();
        return outcome;
    }

    public void remove(int id) throws SQLException {

        PreparedStatement stPlays = connection.prepareStatement("DELETE FROM Plays WHERE track IN (SELECT t.id AS id FROM Track t WHERE t.album = ?)");
        stPlays.setInt(1, id);
        stPlays.executeUpdate();
        stPlays.close();

        PreparedStatement stAdded = connection.prepareStatement("DELETE FROM Added WHERE track IN (SELECT t.id AS id FROM Track t WHERE t.album = ?)");
        stAdded.setInt(1, id);
        stAdded.executeUpdate();
        stAdded.close();

        PreparedStatement stFeaturing = connection.prepareStatement("DELETE FROM Featuring WHERE track IN (SELECT t.id AS id FROM Track t WHERE t.album = ?)");
        stFeaturing.setInt(1, id);
        stFeaturing.executeUpdate();
        stFeaturing.close();

        PreparedStatement stTrack = connection.prepareStatement("DELETE FROM Track WHERE album = ?");
        stTrack.setInt(1, id);
        stTrack.executeUpdate();
        stTrack.close();

        PreparedStatement stAlbum = connection.prepareStatement("DELETE FROM Album WHERE id = ?");
        stAlbum.setInt(1, id);
        stAlbum.executeUpdate();
        stAlbum.close();

    }
    
}
