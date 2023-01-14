package album;

import org.junit.jupiter.api.Test;
import profile.ArtistBean;
import profile.ProfileBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeleteAlbumIntegrationTest {

    private Connection open() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poorify","poorify","poorify");
        return connection;
    }

    @Test
    public void deleteAlbum() throws Exception {

        Connection connection = open();
        AlbumDAO albumDAO = new AlbumDAO(connection);

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //PROFILEBEAN
        ArtistBean artistBean = new ArtistBean();
        artistBean.setAlbums(new ArrayList<>());
        when(session.getAttribute("Profile")).thenReturn(artistBean);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //
        connection.setAutoCommit(false);

        //SERVLET
        DeleteAlbum deleteAlbum = new DeleteAlbum();
        deleteAlbum.albumDAO = albumDAO;
        deleteAlbum.doPost(request, response);

        //
        connection.rollback();

        writer.flush();
        System.out.println(stringWriter.toString());

    }

}