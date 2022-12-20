package profile;

import album.AlbumBean;
import album.AlbumDAO;
import org.json.JSONObject;
import track.TrackDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "GetArtist", value = "/GetArtist")
public class GetArtist extends HttpServlet {

    private ProfileDAO profileDAO;
    private AlbumDAO albumDAO;
    private TrackDAO trackDAO;
    private AlbumBean albumBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        int id = Integer.parseInt(request.getParameter("id"));
        ArtistBean artist = null;

        try{
            artist = (ArtistBean) profileDAO.get(id);
            artist.setTracks(trackDAO.getTopFiveFromArtist(id));

            Collection<AlbumBean> album = albumDAO.getFromArtist(id);
            for(AlbumBean a :album){
                a.setArtist(artist);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        request.getSession().setAttribute("Artist", artist);
        JSONObject jsonObject = new JSONObject();
        response.getWriter().print(jsonObject);

    }
}
