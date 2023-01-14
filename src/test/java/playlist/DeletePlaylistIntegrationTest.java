package playlist;

import org.junit.jupiter.api.Test;
import profile.UserBean;

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

class DeletePlaylistIntegrationTest {

    private Connection open() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poorify","poorify","poorify");
        return connection;
    }

    @Test
    public void createPlaylistTest() throws Exception {

        Connection connection = open();
        PlaylistDAO playlistDAO = new PlaylistDAO(connection);

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");

        //USERBEAN
        UserBean userBean = mock(UserBean.class);
        when(userBean.getId()).thenReturn(2);
        when(userBean.getPlaylists()).thenReturn(new ArrayList<>());

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Profile")).thenReturn(userBean);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //
        connection.setAutoCommit(false);

        DeletePlaylist deletePlaylist = new DeletePlaylist();
        deletePlaylist.playlistDAO = playlistDAO;
        deletePlaylist.doPost(request, response);

        //
        connection.rollback();

        writer.flush();
        System.out.println(stringWriter.toString());
        assert(stringWriter.toString().contains("outcome"));
    }

}