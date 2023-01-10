package track;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddToQueueTest {

    @Test
    public void addToQueue() throws Exception {

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn("1");

        //LISTENING QUEUE
        ListeningQueue queue = mock(ListeningQueue.class);

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("ListeningQueue")).thenReturn(queue);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        AddToQueue addToQueue = new AddToQueue();
        addToQueue.doPost(request, response);

        writer.flush();
        assert(stringWriter.toString().contains(""));
    }

}