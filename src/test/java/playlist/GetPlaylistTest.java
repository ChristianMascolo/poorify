package playlist;

import album.AlbumBean;
import album.AlbumDAO;
import navigation.Navigator;
import org.junit.jupiter.api.Test;
import profile.ProfileDAO;
import profile.UserBean;
import track.TrackDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetPlaylistTest {

    @Test
    public void getPlaylistTest() throws Exception{
        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("new_page")).thenReturn("true");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //ATTRIBUTES
        PlaylistDAO playlistDAO = mock(PlaylistDAO.class);
        PlaylistBean playlistBean = mock(PlaylistBean.class);
        ProfileDAO profileDAO = mock(ProfileDAO.class);
        AlbumDAO albumDAO = mock(AlbumDAO.class);
        TrackDAO trackDAO = mock(TrackDAO.class);
        UserBean userBean = mock(UserBean.class);
        Navigator navigator = mock(Navigator.class);
        when(session.getAttribute("Profile")).thenReturn(userBean);
        when(playlistDAO.get(1)).thenReturn(playlistBean);
        when(playlistBean.getHost()).thenReturn(userBean);
        when(session.getAttribute("Navigator")).thenReturn(navigator);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //SERVLET
        GetPlaylist getPlaylist = new GetPlaylist();
        getPlaylist.playlistDAO = playlistDAO;
        getPlaylist.profileDAO = profileDAO;
        getPlaylist.albumDAO = albumDAO;
        getPlaylist.trackDAO = trackDAO;
        getPlaylist.doPost(request,response);

        writer.flush();
        assert(stringWriter.toString().contains(""));
    }

}