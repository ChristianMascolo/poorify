package track;

import org.junit.jupiter.api.Test;

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

}