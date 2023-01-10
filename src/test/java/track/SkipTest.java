package track;

import org.junit.jupiter.api.Test;
import profile.ProfileBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SkipTest {

    @Test
    public void skipTest() throws Exception {

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //LISTENING QUEUE
        ListeningQueue queue = mock(ListeningQueue.class);
        when(session.getAttribute("ListeningQueue")).thenReturn(queue);
        when(queue.poll()).thenReturn(1);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        Skip skip = new Skip();
        skip.doPost(request, response);

        writer.flush();
        assert(stringWriter.toString().contains("1"));
    }

    @Test
    public void skipWhenEmptyTest() throws Exception {
        //TRACKDAO
        TrackDAO trackDAO = mock(TrackDAO.class);
        when(trackDAO.shuffle(1)).thenReturn(27);

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //LISTENING QUEUE
        ListeningQueue queue = mock(ListeningQueue.class);
        when(session.getAttribute("ListeningQueue")).thenReturn(queue);
        when(queue.poll()).thenReturn(0);

        //PROFILE
        ProfileBean profile = mock(ProfileBean.class);
        when(session.getAttribute("Profile")).thenReturn(profile);
        when(profile.getId()).thenReturn(1);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        Skip skip = new Skip();
        skip.trackDAO = trackDAO;
        skip.doPost(request, response);

        writer.flush();
        assert(!stringWriter.toString().equals(""));
    }

}