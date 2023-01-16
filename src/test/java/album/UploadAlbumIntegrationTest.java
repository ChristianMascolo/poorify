package album;

import org.junit.jupiter.api.Test;
import profile.ArtistBean;
import track.TrackDAO;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UploadAlbumIntegrationTest {

    private Connection open() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poorify","poorify","poorify");
        return connection;
    }

    @Test
    public void uploadAlbum() throws Exception {

        Connection connection = open();
        AlbumDAO albumDAO = new AlbumDAO(connection);
        TrackDAO trackDA0 = new TrackDAO(connection);

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("title")).thenReturn("Title");
        when(request.getParameter("duration")).thenReturn("1");
        when(request.getParameter("tracks")).thenReturn("0");
        when(request.getParameter("year")).thenReturn("2000");
        when(request.getParameter("edit-type")).thenReturn("Album");
        
        //PART
        Part part = mock(Part.class);
        when(part.getSize()).thenReturn(0L);
        when(request.getPart("cover")).thenReturn(part);

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //PROFILEBEAN
        ArtistBean artistBean = new ArtistBean();
        artistBean.setId(3);
        artistBean.setAlbums(new ArrayList<>());
        when(session.getAttribute("Profile")).thenReturn(artistBean);

        //CONTEXT
        ServletContext context = mock(ServletContext.class);
        when(request.getServletContext()).thenReturn(context);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //
        connection.setAutoCommit(false);

        //SERVLET
        UploadAlbum uploadAlbum = new UploadAlbum();
        uploadAlbum.albumDAO = albumDAO;
        uploadAlbum.trackDAO = trackDA0;
        uploadAlbum.doPost(request, response);

        //
        connection.rollback();

        writer.flush();
        System.out.println(stringWriter.toString());

    }

}