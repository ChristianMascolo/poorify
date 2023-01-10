package playlist;

import org.json.JSONObject;
import profile.UserBean;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeletePlaylist", value = "/DeletePlaylist")
public class DeletePlaylist extends HttpServlet {
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

        int id = Integer.parseInt(request.getParameter("id"));

        boolean outcome = false;
        try {

            outcome = playlistDAO.remove(id);
            if(outcome)
                ((UserBean) request.getSession().getAttribute("Profile")).getPlaylists().removeIf(p -> (p.getId() == id));


        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("outcome", outcome);
        response.getWriter().print(jsonObject);
    }
}
