package profile;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CheckCredentialsTest {

    @Test
    public void checkCredentialsTest() throws Exception{
        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("email")).thenReturn("email");
        when(request.getParameter("password")).thenReturn("password");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(true)).thenReturn(session);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //UTILS
        ProfileDAO profileDAO = mock(ProfileDAO.class);

        //SERVLET
        CheckCredentials checkCredentials = new CheckCredentials();
        checkCredentials.profileDAO = profileDAO;
        checkCredentials.doPost(request,response);

        writer.flush();
        System.out.println(stringWriter.toString());
        assert(stringWriter.toString().contains("outcome"));
    }
}