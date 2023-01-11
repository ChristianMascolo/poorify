package playlist;

import org.json.JSONObject;
import profile.UserBean;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RemoveTrack", value = "/RemoveTrack")
public class RemoveTrack extends HttpServlet {
    public PlaylistDAO playlistDAO;

    @Override
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

        int track = Integer.parseInt(request.getParameter("track"));
        int playlist = Integer.parseInt(request.getParameter("playlist"));

        try{
            playlistDAO.removeTrackFromPlaylist(track, playlist);
        }catch (Exception e){
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        response.getWriter().print(jsonObject);

    }
}
