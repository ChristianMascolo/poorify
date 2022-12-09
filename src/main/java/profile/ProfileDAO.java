package profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;
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

}
