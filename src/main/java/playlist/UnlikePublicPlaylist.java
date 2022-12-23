package playlist;

import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UnlikePublicPlaylist", value = "/UnlikePublicPlaylist")
public class UnlikePublicPlaylist extends HttpServlet {
    private PlaylistDAO playlistDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        playlistDAO = (PlaylistDAO) super.getServletContext().getAttribute("PlaylistDAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        int idUser = (int) request.getAttribute("enduser");
        int idPlaylist = (int) request.getAttribute("idPlaylist");

        try {
            playlistDAO.removeLike(idUser,idPlaylist);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonObject = new JSONObject();
        response.getWriter().print(jsonObject);
    }
}
