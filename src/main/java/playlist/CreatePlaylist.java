package playlist;

import netscape.javascript.JSObject;
import org.json.JSONObject;
import profile.UserBean;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CreatePlaylist", value = "/CreatePlaylist")
public class CreatePlaylist extends HttpServlet {

    private PlaylistDAO playlistDAO;

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

        UserBean userBean = (UserBean) request.getSession().getAttribute("Profile");

        int user = userBean.getId();

        boolean outcome = false;
        int id = 0;

        try{

            int playlists = playlistDAO.getCountFromUser(user);
            String title = "My Playlist #" + (playlists + 1);
            outcome = playlistDAO.add(user, title);
            if(outcome) {
                id = playlistDAO.getLastFromUser(user);
                PlaylistBean playlist = playlistDAO.get(id);
                playlist.setHost(userBean);
                userBean.getPlaylists().add(playlist);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("outcome", outcome);
        jsonObject.append("id", id);
        response.getWriter().print(jsonObject);
    }
}
