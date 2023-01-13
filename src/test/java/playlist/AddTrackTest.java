package playlist;

import org.junit.jupiter.api.Test;
import profile.UserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddTrackTest {

    @Test
    public void addTrackTest() throws Exception{

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("track")).thenReturn("2");
        when(request.getParameter("playlist")).thenReturn("3");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //UTILITY
        PlaylistDAO playlistDAO = mock(PlaylistDAO.class);
        UserBean userBean = mock(UserBean.class);
        when(userBean.getId()).thenReturn(1);
        when(session.getAttribute("Profile")).thenReturn(userBean);
        when(playlistDAO.addTrackToPlaylist(1, 2, 3)).thenReturn(true);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        AddTrack addTrack = new AddTrack();
        addTrack.playlistDAO = playlistDAO;
        addTrack.doPost(request, response);

        writer.flush();
        assert(stringWriter.toString().contains("true"));
    }

}