package navigation;

import album.AlbumDAO;
import org.junit.jupiter.api.Test;
import playlist.PlaylistDAO;
import profile.ProfileDAO;
import track.TrackDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchIntegrationTest {

    private Connection open() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poorify","poorify","poorify");
        return connection;
    }

    @Test
    public void searchTest() throws Exception {

        Connection connection = open();
        AlbumDAO albumDAO = new AlbumDAO(connection);
        ProfileDAO profileDAO = new ProfileDAO(connection);
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);
        TrackDAO trackDAO = new TrackDAO(connection);

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("search")).thenReturn("a");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //SERVLET
        Search search = new Search();
        search.albumDAO = albumDAO;
        search.playlistDAO = playlistDAO;
        search.profileDAO = profileDAO;
        search.trackDAO = trackDAO;
        search.doPost(request, response);

        writer.flush();
        System.out.println(stringWriter.toString());
        assert(stringWriter.toString().contains("empty"));

    }

}