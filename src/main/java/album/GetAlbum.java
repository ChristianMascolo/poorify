package album;

import org.json.JSONObject;
import profile.ArtistBean;
import profile.ProfileDAO;
import track.TrackBean;
import track.TrackDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet(name = "GetAlbum", value = "/GetAlbum")
public class GetAlbum extends HttpServlet {

    private AlbumDAO albumDAO;
    private ProfileDAO profileDAO;
    private TrackDAO trackDAO;

    public void init() throws ServletException {
        super.init();
        this.albumDAO = (AlbumDAO) super.getServletContext().getAttribute("AlbumDAO");
        this.profileDAO = (ProfileDAO) super.getServletContext().getAttribute("ProfileDAO");
        this.trackDAO = (TrackDAO) super.getServletContext().getAttribute("TrackDAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        int id = Integer.parseInt(request.getParameter("id"));

        AlbumBean album = null;
        try{
            album = albumDAO.get(id);
            album.setArtist(profileDAO.getFromAlbum(id));

            Collection<TrackBean> tracks = trackDAO.getFromAlbum(id);
            for(TrackBean t: tracks)
                t.setAlbum(album);
            album.setTracklist(tracks);

        }catch(SQLException e){
            e.printStackTrace();
        }

        request.getSession().setAttribute("Album", album);
        JSONObject jsonObject = new JSONObject();
        response.getWriter().print(jsonObject);

    }
}
