package playlist;

import album.AlbumDAO;
import navigation.Navigator;
import navigation.Search;
import org.junit.jupiter.api.Test;
import profile.ProfileDAO;
import profile.UserBean;
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

class GetPlaylistIntegrationTest {

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
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);
        TrackDAO trackDAO = new TrackDAO(connection);

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("new_page")).thenReturn("false");

        //USERBEAN
        UserBean userBean = mock(UserBean.class);
        when(userBean.getId()).thenReturn(1);

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Profile")).thenReturn(userBean);

        //NAVIGATOR
        Navigator navigator = mock(Navigator.class);
        when(session.getAttribute("Navigator")).thenReturn(navigator);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //SERVLET
        GetPlaylist getPlaylist = new GetPlaylist();
        getPlaylist.albumDAO = albumDAO;
        getPlaylist.playlistDAO = playlistDAO;
        getPlaylist.profileDAO = profileDAO;
        getPlaylist.trackDAO = trackDAO;
        getPlaylist.doPost(request, response);

        writer.flush();
        System.out.println(stringWriter.toString());
        assert(stringWriter.toString().contains("title"));

    }

}