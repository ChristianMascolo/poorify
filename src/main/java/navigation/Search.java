package navigation;

import album.AlbumBean;
import album.AlbumDAO;
import org.json.JSONObject;
import playlist.PlaylistBean;
import playlist.PlaylistDAO;
import profile.ProfileDAO;
import track.TrackBean;
import track.TrackDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "Search", value = "/Search")
public class Search extends HttpServlet {

    public PlaylistDAO playlistDAO;
    public ProfileDAO profileDAO;
    public AlbumDAO albumDAO;
    public TrackDAO trackDAO;

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

            Collection<TrackBean> tracks = trackDAO.searchTracksByTitle(search);
            for(TrackBean t: tracks) {
                AlbumBean album = albumDAO.getFromTrack(t.getId());
                album.setArtist(profileDAO.getFromAlbum(album.getId()));
                t.setAlbum(album);
                t.setFeaturing(profileDAO.getFeaturingFromTrack(t.getId()));
            }
            results.setTracks(tracks);

            Collection<AlbumBean> albums = albumDAO.searchAlbumsByTitle(search);
            for(AlbumBean a: albums)
                a.setArtist(profileDAO.getFromAlbum(a.getId()));
            results.setAlbums(albums);

            Collection<PlaylistBean> playlists = playlistDAO.searchPlaylistsByTitle(search);
            for(PlaylistBean p: playlists)
                p.setHost(profileDAO.getHostFromPlaylist(p.getId()));
            results.setPlaylists(playlists);

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("Results", results);
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("empty", results.isEmpty());
        response.getWriter().print(jsonObject);
    }
}
