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

class UnlikePublicPlaylistTest {

    @Test
    public void unlikePublicPlaylistTest() throws Exception{
        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //ATTRIBUTES
        PlaylistDAO playlistDAO = mock(PlaylistDAO.class);
        UserBean userBean = mock(UserBean.class);
        when(session.getAttribute("Profile")).thenReturn(userBean);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //SERVLET
        UnlikePublicPlaylist unlikePublicPlaylist = new UnlikePublicPlaylist();
        unlikePublicPlaylist.playlistDAO = playlistDAO;
        unlikePublicPlaylist.doPost(request,response);

        writer.flush();
        assert(stringWriter.toString().contains("outcome"));
    }
}