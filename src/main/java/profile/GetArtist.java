package profile;

import album.AlbumBean;
import album.AlbumDAO;
import navigation.Navigator;
import navigation.Page;
import org.json.JSONObject;
import track.TrackBean;
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

    public void init() throws ServletException {
        super.init();
        this.albumDAO = (AlbumDAO) super.getServletContext().getAttribute("AlbumDAO");
        this.profileDAO = (ProfileDAO) super.getServletContext().getAttribute("ProfileDAO");
        this.trackDAO = (TrackDAO) super.getServletContext().getAttribute("TrackDAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        int id = Integer.parseInt(request.getParameter("id"));
        boolean new_page = Boolean.parseBoolean(request.getParameter("new_page"));

        ArtistBean artist = null;
        try{
            artist = (ArtistBean) profileDAO.get(id);

            //TOP TRACCE ARTIST
            Collection<TrackBean> topTracks = trackDAO.getTopFiveFromArtist(id);
            for(TrackBean track: topTracks) {
                AlbumBean track_album = albumDAO.getFromTrack(track.getId());
                ArtistBean track_artist = profileDAO.getFromTrack(track.getId());
                Collection<ArtistBean> track_featuring = profileDAO.getFeaturingFromTrack(track.getId());

                track_album.setArtist(track_artist);

                track.setAlbum(track_album);
                track.setFeaturing(track_featuring);
            }
            artist.setTopTracks(topTracks);

            //DISCOGRAFIA
            Collection<AlbumBean> albums = albumDAO.getFromArtist(id);
            for(AlbumBean album: albums)
                album.setArtist(artist);
            artist.setAlbums(albums);

        }catch (Exception e){
            e.printStackTrace();
        }

        //NAVIGATION
        Navigator navigator = (Navigator) request.getSession().getAttribute("Navigator");
        if(new_page)
            navigator.save();
        navigator.setCurrent(new Page(id, Page.Type.ARTIST));

        request.getSession().setAttribute("Artist", artist);
        JSONObject jsonObject = new JSONObject();
        response.getWriter().print(jsonObject);

    }
}
