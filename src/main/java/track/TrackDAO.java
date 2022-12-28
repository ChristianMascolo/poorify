package track;

import javax.sound.midi.Track;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

public class TrackDAO {
    private Connection connection;

    public TrackDAO(Connection connection) {
        this.connection = connection;
    }

    public TrackBean get(int id) throws SQLException {
        TrackBean track = null;

        PreparedStatement statement = connection.prepareStatement("SELECT t.id AS id, t.title AS title, t.track AS track, t.duration AS duration, t.plays AS plays, g.label AS genre " +
                "FROM Track t, Genre g " +
                "WHERE t.genre = g.id " +
                "AND t.id = ?");
        statement.setInt(1, id);

        ResultSet rs = statement.executeQuery();
        if(rs.next())
            track = resultToBean(rs);

        rs.close(); statement.close();
        return track;
    }

    public Collection<TrackBean> getFromAlbum(int id) throws SQLException {
        Collection<TrackBean> tracks = new TreeSet<>((TrackBean a, TrackBean b) -> a.getIndex() - b.getIndex());

        PreparedStatement statement = connection.prepareStatement("SELECT t.id AS id, t.title AS title, t.track AS track, t.duration AS duration, t.plays AS plays, g.label AS genre " +
                "FROM Album a, Track t, Genre g " +
                "WHERE a.id = t.album AND t.genre = g.id " +
                "AND a.id = ?");
        statement.setInt(1, id);

        ResultSet rs = statement.executeQuery();
        while (rs.next())
            tracks.add(resultToBean(rs));

        rs.close(); statement.close();
        return tracks;
    }

    public Collection<TrackBean> getTopFiveFromArtist(int id) throws SQLException {
        Comparator<TrackBean> comparator = new Comparator<TrackBean>() {
            @Override
            public int compare(TrackBean a, TrackBean b) {
                if(a.getPlays() == b.getPlays())
                    return 1;
                return b.getPlays() - a.getPlays();
            }
        };
        Collection<TrackBean> tracks = new TreeSet<>(comparator);

        PreparedStatement statement = connection.prepareStatement(" " +
                " SELECT TOP 5 * FROM (" +
                    " SELECT t.id AS id, t.title AS title, t.track AS track, t.duration AS duration, t.plays AS plays, g.label AS genre " +
                    " FROM Artist ar, Album al, Track t, Genre g " +
                    " WHERE ar.profile = al.artist AND al.id = t.album AND t.genre = g.id " +
                    " AND ar.profile = ? " +
                    " UNION " +
                    " SELECT t.id AS id, t.title AS title, t.track AS track, t.duration AS duration, t.plays AS plays, g.label AS genre " +
                    " FROM Artist ar, Featuring f, Track t, Genre g " +
                    " WHERE ar.profile = f.artist AND f.track = t.id AND t.genre = g.id" +
                    " AND ar.profile = ?" +
                " ) AS temp" +
                " ORDER BY plays DESC");
        statement.setInt(1, id);
        statement.setInt(2, id);

        ResultSet rs = statement.executeQuery();
        while (rs.next())
            tracks.add(resultToBean(rs));

        rs.close(); statement.close();
        return tracks;
    }

    public void addPlay(int user, int track) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Plays (enduser, track) VALUES (?, ?)");
        stmt.setInt(1, user);
        stmt.setInt(2, track);
        stmt.executeUpdate();
        stmt.close();
    }

    public TrackBean resultToBean(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        int index = rs.getInt("track");
        int duration = rs.getInt("duration");
        int plays = rs.getInt("plays");
        String genre = rs.getString("genre");

        return new TrackBean(id, title, index, duration, plays, genre);
    }
}
