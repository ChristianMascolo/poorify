package navigation;

import album.AlbumDAO;
import org.json.JSONObject;
import playlist.PlaylistDAO;
import profile.ProfileDAO;
import track.TrackDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Search", value = "/Search")
public class Search extends HttpServlet {

    private PlaylistDAO playlistDAO;
    private ProfileDAO profileDAO;
    private AlbumDAO albumDAO;
    private TrackDAO trackDAO;

    public void init() throws ServletException {
        super.init();
        this.playlistDAO = (PlaylistDAO) super.getServletContext().getAttribute("PlaylistDAO");
        this.profileDAO = (ProfileDAO) super.getServletContext().getAttribute("ProfileDAO");
        this.albumDAO = (AlbumDAO) super.getServletContext().getAttribute("AlbumDAO");
        this.trackDAO = (TrackDAO) super.getServletContext().getAttribute("TrackDAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String search = request.getParameter("search");

        ResultsContainer results = new ResultsContainer();
        try {

            results.setUsers(profileDAO.searchUsersByAlias(search));
            results.setArtists(profileDAO.searchArtistsByAlias(search));

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("Results", results);

        JSONObject jsonObject = new JSONObject();
        response.getWriter().print(jsonObject);
    }
}
