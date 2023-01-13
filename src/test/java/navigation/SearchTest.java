package navigation;

import album.AlbumDAO;
import org.junit.jupiter.api.Test;
import playlist.PlaylistDAO;
import profile.ProfileDAO;
import track.TrackBean;
import track.TrackDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchTest {

    @Test
    public void SearchTest() throws Exception{

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("search")).thenReturn("search");

        //UTILITY
        PlaylistDAO playlistDAO = mock(PlaylistDAO.class);
        ProfileDAO profileDAO = mock(ProfileDAO.class);
        AlbumDAO albumDAO = mock(AlbumDAO.class);
        TrackDAO trackDAO = mock(TrackDAO.class);

        when(profileDAO.searchUsersByAlias("search")).thenReturn(new ArrayList<>());
        when(profileDAO.searchArtistsByAlias("search")).thenReturn(new ArrayList<>());
        when(trackDAO.searchTracksByTitle("search")).thenReturn(new ArrayList<>());
        when(albumDAO.searchAlbumsByTitle("search")).thenReturn(new ArrayList<>());
        when(playlistDAO.searchPlaylistsByTitle("search")).thenReturn(new ArrayList<>());

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        Search search = new Search();
        search.profileDAO = profileDAO;
        search.albumDAO = albumDAO;
        search.playlistDAO = playlistDAO;
        search.trackDAO = trackDAO;
        search.doPost(request, response);

        writer.flush();
        assert(stringWriter.toString().contains("empty"));
    }

}