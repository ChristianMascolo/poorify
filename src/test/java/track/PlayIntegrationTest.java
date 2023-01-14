package track;

import album.AlbumDAO;
import navigation.Navigator;
import org.junit.jupiter.api.Test;
import playlist.GetPlaylist;
import playlist.PlaylistDAO;
import profile.ProfileDAO;
import profile.UserBean;

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

class PlayIntegrationTest {

    private Connection open() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poorify","poorify","poorify");
        return connection;
    }

    @Test
    public void getPlaylistTest() throws Exception {

        Connection connection = open();
        AlbumDAO albumDAO = new AlbumDAO(connection);
        ProfileDAO profileDAO = new ProfileDAO(connection);
        TrackDAO trackDAO = new TrackDAO(connection);

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("trackId")).thenReturn("1");

        //USERBEAN
        UserBean userBean = mock(UserBean.class);
        when(userBean.getId()).thenReturn(2);

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Profile")).thenReturn(userBean);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //SERVLET
        Play servlet = new Play();
        servlet.albumDAO = albumDAO;
        servlet.profileDAO = profileDAO;
        servlet.trackDAO = trackDAO;
        servlet.doPost(request, response);

        writer.flush();
        System.out.println(stringWriter.toString());
        assert(stringWriter.toString().contains("title"));

    }
}