package profile;

import org.junit.jupiter.api.Test;
import playlist.PlaylistDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SignupTest {

    @Test
    public void signupTest() throws Exception{
        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("email")).thenReturn("email");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("alias")).thenReturn("alias");
        when(request.getParameter("type")).thenReturn("user");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //UTILS
        ProfileDAO profileDAO = mock(ProfileDAO.class);
        PlaylistDAO playlistDAO = mock(PlaylistDAO.class);

        //SERVLET
        Signup signup = new Signup();
        signup.playlistDAO = playlistDAO;
        signup.profileDAO = profileDAO;
        signup.doPost(request,response);

    }
}