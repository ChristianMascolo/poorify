package profile;

import album.AlbumDAO;
import navigation.Navigator;
import org.junit.jupiter.api.Test;
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

class GetArtistTest {

    @Test
    public void getArtistTest() throws Exception {

        //DAO
        ProfileDAO profileDAO = mock(ProfileDAO.class);
        AlbumDAO albumDAO = mock(AlbumDAO.class);
        TrackDAO trackDAO = mock(TrackDAO.class);

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("new_page")).thenReturn("false");

        //BEANS
        ArtistBean artistBean = new ArtistBean();
        artistBean.setId(1);
        when(profileDAO.get(1)).thenReturn(artistBean);

        when(trackDAO.getTopFiveFromArtist(1)).thenReturn(new ArrayList<>());

        when(albumDAO.getFromArtist(1)).thenReturn(new ArrayList<>());

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

        GetArtist getArtist = new GetArtist();
        getArtist.profileDAO = profileDAO;
        getArtist.albumDAO = albumDAO;
        getArtist.trackDAO = trackDAO;
        getArtist.doPost(request, response);

        writer.flush();
        assert(navigator.getCurrent().getId() == 1);
    }

}