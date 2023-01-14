package playlist;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MakePrivateIntegrationTest {

    private Connection open() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poorify","poorify","poorify");
        return connection;
    }

    @Test
    public void makePrivateTest() throws Exception {

        Connection connection = open();
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //
        connection.setAutoCommit(false);

        MakePrivate servlet = new MakePrivate();
        servlet.playlistDAO = playlistDAO;
        servlet.doPost(request, response);

        //
        connection.rollback();

        writer.flush();
        System.out.println(stringWriter.toString());
        assert(stringWriter.toString().contains("outcome"));
    }

}