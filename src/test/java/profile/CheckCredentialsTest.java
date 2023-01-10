package profile;

import org.junit.jupiter.api.Test;
import track.Skip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckCredentialsTest {

    @Test
    public void checkCredentials() throws Exception {

        //PROFILEDAO
        ProfileDAO profileDAO = mock(ProfileDAO.class);
        when(profileDAO.check("email", "password")).thenReturn(true);

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("email")).thenReturn("email");
        when(request.getParameter("password")).thenReturn("password");

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        CheckCredentials checkCredentials = new CheckCredentials();
        checkCredentials.profileDAO = profileDAO;
        checkCredentials.doPost(request, response);

        writer.flush();
        assert(stringWriter.toString().contains("true"));

    }

}