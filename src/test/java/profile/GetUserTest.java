package profile;

import album.AlbumDAO;
import navigation.Navigator;
import org.junit.jupiter.api.Test;
import playlist.PlaylistBean;
import playlist.PlaylistDAO;
import track.PlayPlaylist;
import track.TrackDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetUserTest {

    @Test
    public void getUserTest() throws Exception {
        //DAO
        ProfileDAO profileDAO = mock(ProfileDAO.class);
        AlbumDAO albumDAO = mock(AlbumDAO.class);
        PlaylistDAO playlistDAO = mock(PlaylistDAO.class);

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("new_page")).thenReturn("false");

        //BEANS
        UserBean userBean = new UserBean();
        userBean.setId(1);
        when(profileDAO.get(1)).thenReturn(userBean);

        when(playlistDAO.getPublicFromUser(1)).thenReturn(new ArrayList<>());
        when(playlistDAO.getFromLikes(1)).thenReturn(new ArrayList<>());
        when(profileDAO.getArtistsFromUser(1)).thenReturn(new ArrayList<>());
        when(profileDAO.getFollowersFromUser(1)).thenReturn(new ArrayList<>());
        when(profileDAO.getFollowingFromUser(1)).thenReturn(new ArrayList<>());

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //NAVIGATOR
        Navigator navigator = new Navigator();
        when(session.getAttribute("Navigator")).thenReturn(navigator);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        GetUser getUser = new GetUser();
        getUser.profileDAO = profileDAO;
        getUser.albumDAO = albumDAO;
        getUser.playlistDAO = playlistDAO;
        getUser.doPost(request, response);

        writer.flush();
        assert(navigator.getCurrent().getId() == 1);
    }

}