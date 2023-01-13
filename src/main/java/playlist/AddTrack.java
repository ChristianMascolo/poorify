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

    public PlaylistDAO playlistDAO;

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

        boolean outcome = false;
        try {
            outcome = playlistDAO.addTrackToPlaylist(user, track, playlist);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //RISPOSTA JSON
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("outcome", outcome);
        response.getWriter().print(jsonObject);
    }
}
