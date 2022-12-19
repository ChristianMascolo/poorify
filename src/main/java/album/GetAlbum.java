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

    private AlbumDAO albumDAO = new AlbumDAO();
    private ProfileDAO profileDAO;
    private TrackDAO trackDAO;

    public void init() throws ServletException {
        super.init();
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
        JSONObject jsonObject = new JSONObject();

        int id = Integer.parseInt(request.getParameter("id"));
        AlbumBean album = null;
        ArtistBean artist= null;

        try{
            //get element for album
            album = albumDAO.get(id);
            artist = profileDAO.getFromAlbum(id);

            //set element for album
            album.setArtist(artist);
            album.setTracklist(trackDAO.getFromAlbum(id));

            //set relationship between track and album
            for(TrackBean t:trackDAO.getFromAlbum(id)){
                t.setAlbum(album);
            }


            super.getServletContext().setAttribute("albumDAO",albumDAO);
            jsonObject.append("album",albumDAO.toString());
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
