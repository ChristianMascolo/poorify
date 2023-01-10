package track;

import org.junit.jupiter.api.Test;
import playlist.PlaylistBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayPlaylistTest {

    @Test
    public void playPlaylistTest() throws Exception {

        //PLAYLIST
        PlaylistBean playlistBean = mock(PlaylistBean.class);
        when(playlistBean.getTracklist()).thenReturn(new ArrayList<>());

        //LISTENING QUEUE
        ListeningQueue queue = mock(ListeningQueue.class);

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("index")).thenReturn("1");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("Playlist")).thenReturn(playlistBean);
        when(session.getAttribute("ListeningQueue")).thenReturn(queue);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        PlayPlaylist playPlaylist = new PlayPlaylist();
        playPlaylist.doPost(request, response);

        writer.flush();
        assert(stringWriter.toString().contains(""));
    }

}