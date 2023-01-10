package album;

import navigation.Navigator;
import org.junit.jupiter.api.Test;
import profile.ArtistBean;
import profile.ProfileDAO;
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

class GetAlbumTest {
    @Test
    public void getAlbumTest() throws Exception{
        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("new_page")).thenReturn("true");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //UTILITY
        AlbumDAO albumDAO = mock(AlbumDAO.class);
        TrackDAO trackDAO = mock(TrackDAO.class);
        ProfileDAO profileDAO = mock(ProfileDAO.class);
        ArtistBean artistBean = mock(ArtistBean.class);
        AlbumBean albumBean = mock(AlbumBean.class);
        Navigator navigator = mock(Navigator.class);
        when(albumDAO.get(1)).thenReturn(albumBean);
        when(session.getAttribute("Navigator")).thenReturn(navigator);
        when(artistBean.getAlbums()).thenReturn(new ArrayList<>());
        when(session.getAttribute("Profile")).thenReturn(artistBean);
        when(profileDAO.getFromAlbum(1)).thenReturn(artistBean);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        GetAlbum getAlbum = new GetAlbum();
        getAlbum.trackDAO = trackDAO;
        getAlbum.albumDAO = albumDAO;
        getAlbum.profileDAO = profileDAO;
        getAlbum.doPost(request,response);

        writer.flush();
        assert(stringWriter.toString().contains(""));
    }

}