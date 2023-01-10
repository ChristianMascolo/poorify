package album;

import org.json.JSONObject;
import profile.ArtistBean;
import profile.ProfileBean;
import track.TrackDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeleteAlbum", value = "/DeleteAlbum")
public class DeleteAlbum extends HttpServlet {

    public AlbumDAO albumDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.albumDAO = (AlbumDAO) super.getServletContext().getAttribute("AlbumDAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        ProfileBean profile = (ProfileBean) request.getSession().getAttribute("Profile");
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            albumDAO.remove(id);
            if(profile.getRole() == ProfileBean.Role.ARTIST) {
                ArtistBean artist = (ArtistBean) profile;
                artist.getAlbums().removeIf(a -> (a.getId() == id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        JSONObject jsonObject = new JSONObject();
        response.getWriter().print(jsonObject);
    }
}
