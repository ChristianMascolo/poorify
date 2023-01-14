package playlist;

import org.json.JSONObject;
import profile.ProfileBean;
import profile.ProfileDAO;
import profile.UserBean;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LikePublicPlaylist", value = "/LikePublicPlaylist")
public class LikePublicPlaylist extends HttpServlet {

    public PlaylistDAO playlistDAO;
    public ProfileDAO profileDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.playlistDAO = (PlaylistDAO) super.getServletContext().getAttribute("PlaylistDAO");
        this.profileDAO = (ProfileDAO) super.getServletContext().getAttribute("ProfileDAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        UserBean profile = (UserBean) request.getSession().getAttribute("Profile");

        int user = profile.getId();
        int playlist = Integer.parseInt(request.getParameter("id"));

        boolean outcome = false;
        try {
            outcome = playlistDAO.like(user, playlist);
            PlaylistBean p = playlistDAO.get(playlist);
            p.setHost(profileDAO.getHostFromPlaylist(playlist));
            profile.getLikedPlaylists().add(playlistDAO.get(playlist));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("outcome", outcome);
        response.getWriter().print(jsonObject);
    }
}
