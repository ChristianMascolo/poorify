package playlist;

import org.json.JSONObject;
import profile.UserBean;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddTrack", value = "/AddTrack")
public class AddTrack extends HttpServlet {

    private PlaylistDAO playlistDAO;

    public void init() throws ServletException {
        super.init();
        this.playlistDAO = (PlaylistDAO) super.getServletContext().getAttribute("PlaylistDAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        int user = ((UserBean) request.getSession().getAttribute("Profile")).getId();
        int track = Integer.parseInt(request.getParameter("track"));
        int playlist = Integer.parseInt(request.getParameter("playlist"));

        try {

            playlistDAO.addTrackToPlaylist(user, track, playlist);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //RISPOSTA JSON
        request.getSession().setAttribute("Playlist", playlist);
        JSONObject jsonObject = new JSONObject();
        response.getWriter().print(jsonObject);
    }
}
