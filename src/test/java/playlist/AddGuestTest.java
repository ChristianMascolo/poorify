package playlist;

import org.junit.jupiter.api.Test;
import profile.ArtistBean;
import profile.UserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddGuestTest {

    @Test
    public void AddGuestTest() throws Exception{

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("guest")).thenReturn("2");
        when(request.getParameter("playlist")).thenReturn("3");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //UTILITY
        PlaylistDAO playlistDAO = mock(PlaylistDAO.class);
        UserBean userBean = mock(UserBean.class);
        when(userBean.getId()).thenReturn(1);
        when(session.getAttribute("Profile")).thenReturn(userBean);
        when(playlistDAO.addGuest(1, 2, 3)).thenReturn(true);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        AddGuest addGuest = new AddGuest();
        addGuest.playlistDAO = playlistDAO;
        addGuest.doPost(request, response);

        writer.flush();
        assert(stringWriter.toString().contains("true"));

    }

}