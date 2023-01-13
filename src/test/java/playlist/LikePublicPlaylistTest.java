package playlist;

import org.junit.jupiter.api.Test;
import profile.ProfileDAO;
import profile.UserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LikePublicPlaylistTest {

    @Test
    public void likePublicPlaylistTest() throws Exception{
        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //ATTRIBUTES
        UserBean userBean = mock(UserBean.class);
        PlaylistDAO playlistDAO = mock(PlaylistDAO.class);
        PlaylistBean playlistBean = mock(PlaylistBean.class);
        ProfileDAO profileDAO = mock(ProfileDAO.class);
        userBean.setId(1);
        when(session.getAttribute("Profile")).thenReturn(userBean);
        when(playlistDAO.get(1)).thenReturn(playlistBean);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //SERVLET
        LikePublicPlaylist likePublicPlaylist = new LikePublicPlaylist();
        likePublicPlaylist.profileDAO = profileDAO;
        likePublicPlaylist.playlistDAO = playlistDAO;
        likePublicPlaylist.doPost(request,response);


        writer.flush();
        System.out.println(stringWriter.toString());
        assert(stringWriter.toString().contains("outcome"));
    }
}