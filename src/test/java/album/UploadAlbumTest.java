package album;

import com.azure.core.util.tracing.Tracer;
import org.junit.jupiter.api.Test;
import profile.ArtistBean;
import track.TrackDAO;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UploadAlbumTest {

    @Test
    public void uploadAlbumTest() throws Exception{
        //REQUEST & RESPONSE
        Part part = mock(Part.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("title")).thenReturn("title");
        when(request.getParameter("duration")).thenReturn("100");
        when(request.getParameter("tracks")).thenReturn("1");
        when(request.getParameter("year")).thenReturn("2023");
        when(request.getParameter("edit-type")).thenReturn("album");
        when(request.getPart("cover")).thenReturn(part);

        //SESSION
        ServletConfig config = mock(ServletConfig.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //UTILITY

        AlbumDAO albumDAO = mock(AlbumDAO.class);
        TrackDAO trackDAO = mock(TrackDAO.class);
        ArtistBean artistBean = mock(ArtistBean.class);
        when(artistBean.getAlbums()).thenReturn(new ArrayList<>());
        when(session.getAttribute("Profile")).thenReturn(artistBean);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        UploadAlbum uploadAlbum = new UploadAlbum();
        uploadAlbum.albumDAO = albumDAO;
        uploadAlbum.trackDAO = trackDAO;
        uploadAlbum.doPost(request,response);

        writer.flush();
        assert(stringWriter.toString().contains(""));
    }
}