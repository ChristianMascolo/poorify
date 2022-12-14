package album;

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
                    "WHERE al.artist = ar.id " +
                    "AND ar.id = ?");
        stmt.setInt(1,id);

        ResultSet rs = stmt.executeQuery();
        while(rs.next())
            albums.add(resultToBean(rs));

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



}
