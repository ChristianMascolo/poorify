package profile;

import album.AlbumDAO;
import org.junit.jupiter.api.Test;
import playlist.PlaylistDAO;
import track.TrackDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginTest {

    @Test
    public void loginTest() throws Exception {

        //DAO
        PlaylistDAO playlistDAO = mock(PlaylistDAO.class);
        ProfileDAO profileDAO = mock(ProfileDAO.class);
        AlbumDAO albumDAO = mock(AlbumDAO.class);
        TrackDAO trackDAO = mock(TrackDAO.class);

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("email")).thenReturn("email");
        when(request.getParameter("password")).thenReturn("password");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(true)).thenReturn(session);

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("home.jsp")).thenReturn(dispatcher);

        //BEANS
        UserBean userBean = new UserBean();
        userBean.setId(1);
        when(profileDAO.get("email", "password")).thenReturn(userBean);
        when(playlistDAO.getFromUser(userBean.getId())).thenReturn(new ArrayList<>());
        when(playlistDAO.getFromLikes(userBean.getId())).thenReturn(new ArrayList<>());
        when(profileDAO.getArtistsFromUser(userBean.getId())).thenReturn(new ArrayList<>());
        when(profileDAO.getFollowingFromUser(userBean.getId())).thenReturn(new ArrayList<>());
        when(profileDAO.getFollowersFromUser(userBean.getId())).thenReturn(new ArrayList<>());

        UserBean poorify = new UserBean();
        when(profileDAO.get(1)).thenReturn(poorify);

        when(trackDAO.generateFeed(1)).thenReturn(new ArrayList<>());

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        Login login = new Login();
        login.playlistDAO = playlistDAO;
        login.profileDAO = profileDAO;
        login.trackDAO = trackDAO;
        login.albumDAO = albumDAO;
        login.doPost(request, response);

        writer.flush();

    }

}