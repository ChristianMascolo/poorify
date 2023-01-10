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

class MakeCollaborativeTest {

    @Test
    public void MakeCollaborativeTest() throws Exception{

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //PLAYLIST
        PlaylistDAO playlistDAO = mock(PlaylistDAO.class);
        when(playlistDAO.setCollaborative(1)).thenReturn(true);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        MakeCollaborative makeCollaborative = new MakeCollaborative();
        makeCollaborative.playlistDAO = playlistDAO;
        makeCollaborative.doPost(request, response);

        writer.flush();
        assert(stringWriter.toString().contains("true"));
    }
}