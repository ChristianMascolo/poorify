package track;

import album.AlbumBean;
import album.AlbumDAO;
import org.junit.jupiter.api.Test;
import profile.ArtistBean;
import profile.ProfileDAO;
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

class PlayTest {

    @Test
    public void playTest() throws Exception {

        //BEAN
        UserBean userBean = mock(UserBean.class);
        TrackBean trackBean = mock(TrackBean.class);
        AlbumBean albumBean = mock(AlbumBean.class);
        ArtistBean artistBean = mock(ArtistBean.class);

        when(trackBean.getTitle()).thenReturn("Title");
        when(trackBean.getAlbum()).thenReturn(albumBean);
        when(albumBean.getArtist()).thenReturn(artistBean);

        //DAO
        TrackDAO trackDAO = mock(TrackDAO.class);
        AlbumDAO albumDAO = mock(AlbumDAO.class);
        ProfileDAO profileDAO = mock(ProfileDAO.class);

        when(trackDAO.get(1)).thenReturn(trackBean);
        when(albumDAO.getFromTrack(1)).thenReturn(albumBean);
        when(profileDAO.getFromTrack(1)).thenReturn(artistBean);
        when(profileDAO.getFeaturingFromTrack(1)).thenReturn(new ArrayList<>());

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("trackId")).thenReturn("1");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("Profile")).thenReturn(userBean);
        when(userBean.getId()).thenReturn(1);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        Play play = new Play();
        play.trackDAO = trackDAO;
        play.albumDAO = albumDAO;
        play.profileDAO = profileDAO;
        play.doPost(request, response);

        writer.flush();
        assert(stringWriter.toString().contains("Title"));
    }

}