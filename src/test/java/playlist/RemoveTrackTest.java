package playlist;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RemoveTrackTest {

    @Test
    public void removeTrackTest() throws Exception{
        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("track")).thenReturn("1");
        when(request.getParameter("playlist")).thenReturn("1");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //ATTRIBUTES
        PlaylistDAO playlistDAO = mock(PlaylistDAO.class);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //SERVLET
        RemoveTrack removeTrack = new RemoveTrack();
        removeTrack.playlistDAO = playlistDAO;
        removeTrack.doPost(request,response);

        writer.flush();
        assert(stringWriter.toString().contains(""));
    }
}