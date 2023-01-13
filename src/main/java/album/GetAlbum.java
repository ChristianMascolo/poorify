package album;

import navigation.Navigator;
import navigation.Page;
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

    public AlbumDAO albumDAO;
    public ProfileDAO profileDAO;
    public TrackDAO trackDAO;

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
        boolean new_page = Boolean.parseBoolean(request.getParameter("new_page"));

        AlbumBean album = null;
        try{
            album = albumDAO.get(id);

            ArtistBean artist = profileDAO.getFromAlbum(id);
            album.setArtist(artist);

            Collection<TrackBean> tracks = trackDAO.getFromAlbum(id);
            for(TrackBean t: tracks) {
                t.setAlbum(album);
                t.setFeaturing(profileDAO.getFeaturingFromTrack(t.getId()));
            }
            album.setTracklist(tracks);

        }catch(SQLException e){
            e.printStackTrace();
        }

        //NAVIGATION
        Navigator navigator = (Navigator) request.getSession().getAttribute("Navigator");
        if(new_page)
            navigator.save();
        navigator.setCurrent(new Page(id, Page.Type.ALBUM));

        //RISPOSTA JSON
        request.getSession().setAttribute("Album", album);
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("title", album.getTitle());
        response.getWriter().print(jsonObject);

    }
}
