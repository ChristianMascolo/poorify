package profile;

import album.AlbumDAO;
import org.junit.jupiter.api.Test;
import playlist.GetPlaylist;
import playlist.PlaylistDAO;
import track.TrackDAO;

import javax.servlet.RequestDispatcher;
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

class LoginIntegrationTest {

    private Connection open() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poorify","poorify","poorify");
        return connection;
    }

    @Test
    public void loginUserTest() throws Exception {

        Connection connection = open();
        AlbumDAO albumDAO = new AlbumDAO(connection);
        ProfileDAO profileDAO = new ProfileDAO(connection);
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);
        TrackDAO trackDAO = new TrackDAO(connection);

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("email")).thenReturn("alessandro@gmail.com");
        when(request.getParameter("password")).thenReturn("alessandro");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(true)).thenReturn(session);

        //DISPATCHER
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("home.jsp")).thenReturn(dispatcher);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //SERVLET
        Login login = new Login();
        login.albumDAO = albumDAO;
        login.playlistDAO = playlistDAO;
        login.profileDAO = profileDAO;
        login.trackDAO = trackDAO;
        login.doPost(request, response);

        writer.flush();
        System.out.println(stringWriter.toString());
        assert(stringWriter.toString().contains(""));

    }

    @Test
    public void loginArtistTest() throws Exception {

        Connection connection = open();
        AlbumDAO albumDAO = new AlbumDAO(connection);
        ProfileDAO profileDAO = new ProfileDAO(connection);
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);
        TrackDAO trackDAO = new TrackDAO(connection);

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("email")).thenReturn("coldplay@gmail.com");
        when(request.getParameter("password")).thenReturn("coldplay");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(true)).thenReturn(session);

        //DISPATCHER
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("home.jsp")).thenReturn(dispatcher);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //SERVLET
        Login login = new Login();
        login.albumDAO = albumDAO;
        login.playlistDAO = playlistDAO;
        login.profileDAO = profileDAO;
        login.trackDAO = trackDAO;
        login.doPost(request, response);

        writer.flush();
        System.out.println(stringWriter.toString());
        assert(stringWriter.toString().contains(""));

    }

    @Test
    public void loginOverseerTest() throws Exception {

        Connection connection = open();
        AlbumDAO albumDAO = new AlbumDAO(connection);
        ProfileDAO profileDAO = new ProfileDAO(connection);
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);
        TrackDAO trackDAO = new TrackDAO(connection);

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("email")).thenReturn("kevin@gmail.com");
        when(request.getParameter("password")).thenReturn("kevin");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(true)).thenReturn(session);

        //DISPATCHER
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("home.jsp")).thenReturn(dispatcher);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //SERVLET
        Login login = new Login();
        login.albumDAO = albumDAO;
        login.playlistDAO = playlistDAO;
        login.profileDAO = profileDAO;
        login.trackDAO = trackDAO;
        login.doPost(request, response);

        writer.flush();
        System.out.println(stringWriter.toString());
        assert(stringWriter.toString().contains(""));

    }

}