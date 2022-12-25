package playlist;

import album.AlbumBean;
import album.AlbumDAO;
import navigation.Navigator;
import navigation.Page;
import org.json.JSONObject;
import profile.ArtistBean;
import profile.ProfileDAO;
import profile.UserBean;
import track.TrackBean;
import track.TrackDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Collection;
import java.util.TreeSet;

@WebServlet(name = "GetPlaylist", value = "/GetPlaylist")
public class GetPlaylist extends HttpServlet {

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

        int id = Integer.parseInt(request.getParameter("id"));
        boolean new_page = Boolean.parseBoolean(request.getParameter("new_page"));
        UserBean profile = (UserBean) request.getSession().getAttribute("Profile");

        PlaylistBean playlist = null;
        try {

            playlist = playlistDAO.get(id);
            playlist.setHost(profileDAO.getHostFromPlaylist(id));
            if(playlist.isPublic()) playlist.setLikes(playlistDAO.getLikes(id));
            if(playlist.isCollaborative()) playlist.setGuests(profileDAO.getGuestsFromPlaylist(id));

            if(playlist.getHost().getId() == profile.getId())
                playlistDAO.updateLastAccess(playlist.getId());

            //RICERCA E COMPOSIZIONE DEI BRANI NELLA PLAYLIST
            Collection<AddedBean> addedBeans = new TreeSet<>((AddedBean a, AddedBean b) -> a.getDate().compareTo(b.getDate()));
            Collection<AddedBeanProxy> addedBeanProxies = playlistDAO.getAdded(id);
            for(AddedBeanProxy proxy: addedBeanProxies) {

                AddedBean addedBean = new AddedBean();
                addedBean.setDate(proxy.getDate());
                addedBean.setPlaylist(playlist);

                if(playlist.getHost().getId() == proxy.getUser())
                    addedBean.setUser(playlist.getHost());
                else {
                    for(UserBean guest: playlist.getGuests()) {
                        if(guest.getId() == proxy.getUser()) {
                            addedBean.setUser(guest);
                            break;
                        }
                    }
                }

                TrackBean track = trackDAO.get(proxy.getTrack());
                AlbumBean album = albumDAO.getFromTrack(track.getId());
                ArtistBean artist = profileDAO.getFromAlbum(album.getId());
                Collection<ArtistBean> featuring = profileDAO.getFeaturingFromTrack(track.getId());

                track.setFeaturing(featuring);
                album.setArtist(artist);
                track.setAlbum(album);

                addedBean.setTrack(track);
                addedBeans.add(addedBean);
            }

            playlist.setTracklist(addedBeans);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //NAVIGATION
        Navigator navigator = (Navigator) request.getSession().getAttribute("Navigator");
        if(new_page)
            navigator.save();
        navigator.setCurrent(new Page(id, Page.Type.PLAYLIST));

        //RISPOSTA JSON
        request.getSession().setAttribute("Playlist", playlist);
        JSONObject jsonObject = new JSONObject();
        response.getWriter().print(jsonObject);
    }
}
