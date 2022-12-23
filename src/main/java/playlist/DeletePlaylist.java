package playlist;

import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeletePlaylist", value = "/DeletePlaylist")
public class DeletePlaylist extends HttpServlet {
    private PlaylistDAO playlistDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        playlistDAO = (PlaylistDAO) super.getServletContext().getAttribute("PlaylistDAO");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        int id = (int) request.getAttribute("id");

        try {
            playlistDAO.deletePlaylist(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonObject = new JSONObject();
        response.getWriter().print(jsonObject);
    }
}