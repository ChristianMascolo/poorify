package playlist;

import org.junit.jupiter.api.Test;
import profile.UserBean;

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

class EditPlaylistTest {

    @Test
    public void editPlaylistTitleTest() throws Exception {

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("title")).thenReturn("title");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //PART
        Part part = mock(Part.class);
        when(part.getSize()).thenReturn(0L);
        when(request.getPart("cover")).thenReturn(part);

        //USER
        UserBean userBean = mock(UserBean.class);
        when(userBean.getPlaylists()).thenReturn(new ArrayList<>());

        //BEAN
        PlaylistBean playlistBean = new PlaylistBean();
        playlistBean.setId(1);
        playlistBean.setTitle("title");
        when(session.getAttribute("Playlist")).thenReturn(playlistBean);
        when(session.getAttribute("Profile")).thenReturn(userBean);

        //DAO
        PlaylistDAO playlistDAO = mock(PlaylistDAO.class);
        when(playlistDAO.get(1)).thenReturn(playlistBean);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        EditPlaylist editPlaylist = new EditPlaylist();
        editPlaylist.playlistDAO = playlistDAO;
        editPlaylist.doPost(request, response);

        writer.flush();
        assert(playlistBean.getTitle().equals("title"));
    }

    @Test
    public void editPlaylistIsPublicTest() throws Exception {

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("isPublic")).thenReturn("");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //PART
        Part part = mock(Part.class);
        when(part.getSize()).thenReturn(0L);
        when(request.getPart("cover")).thenReturn(part);

        //USER
        UserBean userBean = mock(UserBean.class);
        when(userBean.getPlaylists()).thenReturn(new ArrayList<>());

        //BEAN
        PlaylistBean playlistBean = new PlaylistBean();
        playlistBean.setId(1);
        playlistBean.setPublic(true);
        when(session.getAttribute("Playlist")).thenReturn(playlistBean);
        when(session.getAttribute("Profile")).thenReturn(userBean);

        //DAO
        PlaylistDAO playlistDAO = mock(PlaylistDAO.class);
        when(playlistDAO.get(1)).thenReturn(playlistBean);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        EditPlaylist editPlaylist = new EditPlaylist();
        editPlaylist.playlistDAO = playlistDAO;
        editPlaylist.doPost(request, response);

        writer.flush();
        assert(playlistBean.isPublic() == true);
    }

    @Test
    public void editPlaylistIsCollaborativeTest() throws Exception {

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("isCollaborative")).thenReturn("");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //PART
        Part part = mock(Part.class);
        when(part.getSize()).thenReturn(0L);
        when(request.getPart("cover")).thenReturn(part);

        //USER
        UserBean userBean = mock(UserBean.class);
        when(userBean.getPlaylists()).thenReturn(new ArrayList<>());

        //BEAN
        PlaylistBean playlistBean = new PlaylistBean();
        playlistBean.setId(1);
        playlistBean.setCollaborative(true);
        when(session.getAttribute("Playlist")).thenReturn(playlistBean);
        when(session.getAttribute("Profile")).thenReturn(userBean);

        //DAO
        PlaylistDAO playlistDAO = mock(PlaylistDAO.class);
        when(playlistDAO.get(1)).thenReturn(playlistBean);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        EditPlaylist editPlaylist = new EditPlaylist();
        editPlaylist.playlistDAO = playlistDAO;
        editPlaylist.doPost(request, response);

        writer.flush();
        assert(playlistBean.isCollaborative() == true);
    }

}