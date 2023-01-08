package track;

import profile.ArtistBean;

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

    public Collection<TrackBean> searchTracksByTitle(String search) throws SQLException {
        Collection<TrackBean> tracks = new TreeSet<>((TrackBean a, TrackBean b) -> a.getTitle().compareTo(b.getTitle()));
        PreparedStatement stmt = connection.prepareStatement("" +
                "SELECT TOP 10 t.id AS id, t.title AS title, t.track AS track, t.duration AS duration, t.plays AS plays, g.label AS genre " +
                "FROM Track t, Genre g " +
                "WHERE t.genre = g.id " +
                "AND t.title LIKE ?");
        search = '%' + search +  '%';
        stmt.setString(1, search);

        ResultSet rs = stmt.executeQuery();
        while(rs.next())
            tracks.add((TrackBean) resultToBean(rs));

        rs.close(); stmt.close();
        return tracks;
    }

    public boolean add(int album, String title, int track, int duration, int genre) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Track (album, title, track, duration, genre) VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1, album);
        stmt.setString(2, title);
        stmt.setInt(3, track);
        stmt.setInt(4, duration);
        stmt.setInt(5, genre);

        boolean outcome = stmt.executeUpdate() > 0;
        stmt.close();

        return outcome;
    }

    public int get(int album, String title) throws SQLException {
        int outcome = 0;

        PreparedStatement stmt = connection.prepareStatement("SELECT t.id AS id FROM Track t WHERE t.album = ? AND t.title = ?");
        stmt.setInt(1, album);
        stmt.setString(2, title);

        ResultSet rs = stmt.executeQuery();
        if(rs.next())
            outcome = rs.getInt("id");

        rs.close(); stmt.close();
        return outcome;
    }

    public Collection<TrackBean> generateFeed(int id) throws SQLException {
        Collection<TrackBean> tracks = new ArrayList<>();

        PreparedStatement plays = connection.prepareStatement("SELECT TOP 1 * FROM Plays p WHERE p.enduser = ?");
        plays.setInt(1, id);
        ResultSet rsPlays = plays.executeQuery();

        if(rsPlays.next()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT TOP 25 t.id AS id, t.title AS title, t.track AS track, t.duration AS duration, t.plays AS plays, g.label AS genre " +
                    "FROM Track t, Genre g " +
                    "WHERE t.genre = g.id " +
                    "AND t.genre IN ( " +
                    "   SELECT TOP 10 g.id " +
                    "   FROM Plays p, Track t, Genre g " +
                    "   WHERE p.track = t.id AND t.genre = g.id AND p.enduser = ? " +
                    "   ORDER BY p.time DESC " +
                    "   ) " +
                    "ORDER BY NEWID()");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
                tracks.add(resultToBean(rs));
            rs.close();
            stmt.close();
        } else {
            PreparedStatement stmt = connection.prepareStatement("SELECT TOP 25 t.id AS id, t.title AS title, t.track AS track, t.duration AS duration, t.plays AS plays, g.label AS genre " +
                    "FROM Track t, Genre g " +
                    "WHERE t.genre = g.id " +
                    "AND t.genre IN ( " +
                    "   SELECT TOP 10 g.id FROM Genre g " +
                    "   ) " +
                    "ORDER BY NEWID()");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
                tracks.add(resultToBean(rs));
            rs.close();
            stmt.close();
        }

        rsPlays.close();
        plays.close();

        return tracks;
    }

    public int shuffle(int id) throws SQLException {
        int outcome = 0;
        PreparedStatement stmt = connection.prepareStatement("SELECT TOP 1 t.id AS id " +
                "FROM Track t " +
                "WHERE t.genre IN (" +
                "   SELECT TOP 1 t.genre " +
                "   FROM Plays p, Track t" +
                "   WHERE p.track = t.id AND p.enduser = ? " +
                "   ORDER BY p.time DESC " +
                ") ORDER BY NEWID()");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
            outcome = rs.getInt("id");
        rs.close(); stmt.close();
        return outcome;
    }

}
