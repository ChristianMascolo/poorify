package playlist;

import netscape.javascript.JSObject;
import org.json.JSONObject;

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
        playlistDAO = (PlaylistDAO) super.getServletContext().getAttribute("PlaylistDAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        int enduser = (int) request.getSession().getAttribute("enduser");
        String title = request.getParameter("title");

        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("outcome",playlistDAO.createPlaylist(enduser,title));
            response.getWriter().print(jsonObject);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
