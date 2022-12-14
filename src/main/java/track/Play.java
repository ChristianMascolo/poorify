package track;

import album.AlbumBean;
import album.AlbumDAO;
import org.json.JSONObject;
import profile.ArtistBean;
import profile.ProfileBean;
import profile.ProfileDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "Play", value = "/Play")
public class Play extends HttpServlet {

    public TrackDAO trackDAO;
    public AlbumDAO albumDAO;
    public ProfileDAO profileDAO;

    public void init() throws ServletException {
        super.init();
        this.trackDAO = (TrackDAO) super.getServletContext().getAttribute("TrackDAO");
        this.albumDAO = (AlbumDAO) super.getServletContext().getAttribute("AlbumDAO");
        this.profileDAO = (ProfileDAO) super.getServletContext().getAttribute("ProfileDAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        int userId = ((ProfileBean) request.getSession().getAttribute("Profile")).getId();
        int trackId = Integer.parseInt(request.getParameter("trackId"));

        TrackBean track = null;
        try {
            track = trackDAO.get(trackId);
            AlbumBean album = albumDAO.getFromTrack(trackId);
            ArtistBean artist = profileDAO.getFromTrack(trackId);
            Collection<ArtistBean> featuring = profileDAO.getFeaturingFromTrack(trackId);
            album.setArtist(artist);
            track.setAlbum(album);
            track.setFeaturing(featuring);

            trackDAO.addPlay(userId, trackId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("CurrentTrack", track);

        JSONObject jsonObject = new JSONObject();

        jsonObject.append("title", track.getTitle());
        jsonObject.append("trackID", track.getId());
        jsonObject.append("albumID", track.getAlbum().getId());
        jsonObject.append("artist", track.getAlbum().getArtist().getAlias());
        jsonObject.append("artistID", track.getAlbum().getArtist().getId());
        if(track.getFeaturing() != null) {
            for(ArtistBean artist: track.getFeaturing()) {
                jsonObject.append("artist", artist.getAlias());
                jsonObject.append("artistID", artist.getId());
            }
        }

        response.getWriter().print(jsonObject);

    }
}
