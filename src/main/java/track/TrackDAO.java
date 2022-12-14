package track;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrackDAO {
    private final Connection connection;

    public TrackDAO(Connection connection) {
        this.connection = connection;
    }

    public TrackBean resultToBean(ResultSet rs) {
        TrackBean track = new TrackBean();

        try {
            track.setId(rs.getInt("id"));
            track.setDuration(rs.getInt("duration"));
            track.setPlays(rs.getInt("plays"));
            track.setIndex(rs.getInt("index"));
            track.setIndex(rs.getInt("track"));
            track.setTitle(rs.getString("title"));
            track.setGenre(rs.getString("label"));
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return track;
    }

    public TrackBean getById(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * " +
                "FROM Track t,Genre g " +
                "WHERE t.genre = g.id " +
                "AND t.id = ?");

        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        TrackBean track = resultToBean(rs);
        statement.close();
        return track;
    }

    public ArrayList<TrackBean> getByAlbum(int idAlbum) throws SQLException {
        ArrayList<TrackBean> tracks = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT *" +
                "FROM Album b,Track t" +
                "WHERE t.album = b.id" +
                "AND b.id = ?");

        statement.setInt(1, idAlbum);

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            tracks.add(resultToBean(rs));
        }
        return tracks;
    }

    public ArrayList<TrackBean> getTopTracksArtist(int idArtist, int max) throws SQLException {
        ArrayList<TrackBean> tracks = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT     t.id,t.title," +
                "           t.duration,t.track" +
                "           t.plays,g.label" +
                "FROM       Genre g,Album al," +
                "           Artist ar,Profile p," +
                "           Track t" +
                "WHERE      t.genre = g.id " +
                "   AND     t.album = al.id" +
                "   AND     al.artist = p.id" +
                "   AND     ar.profile = ?" +
                "   ORDER BY   t.plays" +
                "   LIMIT " + max + "");
        statement.setInt(1, idArtist);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            tracks.add(resultToBean(rs));
        }

        return tracks;
    }

    public TrackBean getByTitle(String title) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * " +
                "FROM Track t" +
                "WHERE t.title = ?");
        statement.setString(1, title);
        ResultSet rs = statement.executeQuery();

        if(rs.next()){
            return resultToBean(rs);
        }

        return null;
    }

    public TrackBean getByAlbumTitle(String title) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT *" +
                "FROM Track t,Album a" +
                "WHERE t.album = a.id AND a.title = ?");
        statement.setString(1,title);

        ResultSet rs = statement.executeQuery();

        if(rs.next()){
            return resultToBean(rs);
        }

        return null;
    }

    public TrackBean getByArtist(String alias) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT *" +
                "FROM Track t,Album al,Artist ar" +
                "WHERE t.album = al.id AND al.artist = ar.profile AND ar.alias = ?");

        statement.setString(1,alias);

        ResultSet rs = statement.executeQuery();

        if(rs.next()){
            return resultToBean(rs);
        }

        return null;
    }
}
