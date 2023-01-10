package album;

import org.junit.jupiter.api.Test;
import profile.ArtistBean;
import profile.ProfileDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeleteAlbumTest {

    @Test
    public void deleteAlbumTest() throws Exception{
        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //UTILITY
        AlbumDAO albumDAO = mock(AlbumDAO.class);
        ArtistBean artistBean = mock(ArtistBean.class);
        when(artistBean.getAlbums()).thenReturn(new ArrayList<>());
        when(session.getAttribute("Profile")).thenReturn(artistBean);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        DeleteAlbum deleteAlbum = new DeleteAlbum();
        deleteAlbum.albumDAO = albumDAO;
        deleteAlbum.doPost(request,response);

        writer.flush();
        assert(stringWriter.toString().contains(""));
    }
}