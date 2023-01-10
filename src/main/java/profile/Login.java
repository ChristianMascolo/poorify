package profile;

import album.AlbumBean;
import album.AlbumDAO;
import com.fasterxml.jackson.databind.DatabindException;
import main.DateFormatter;
import navigation.Navigator;
import navigation.Page;
import playlist.AddedBean;
import playlist.PlaylistBean;
import playlist.PlaylistDAO;
import track.ListeningQueue;
import track.TrackBean;
import track.TrackDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.TreeSet;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {

    public ProfileDAO profileDAO;
    public PlaylistDAO playlistDAO;
    public TrackDAO trackDAO;
    public AlbumDAO albumDAO;

    public void init() throws ServletException {
        super.init();
        this.profileDAO = (ProfileDAO) super.getServletContext().getAttribute("ProfileDAO");
        this.playlistDAO = (PlaylistDAO) super.getServletContext().getAttribute("PlaylistDAO");
        this.trackDAO = (TrackDAO) super.getServletContext().getAttribute("TrackDAO");
        this.albumDAO = (AlbumDAO) super.getServletContext().getAttribute("AlbumDAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");


        if(email == null)
            email = (String) request.getAttribute("email");
        if(password == null)
            password = (String) request.getAttribute("password");

        ProfileBean profile = null;
        PlaylistBean feed = null;

        try {
            profile = profileDAO.get(email, password);
            if(profile.getRole() == ProfileBean.Role.USER) {

                //PLAYLIST CREATE O IN COLLABORAZIONE PER L'UTENTE
                Collection<PlaylistBean> playlists = playlistDAO.getFromUser(profile.getId());
                for(PlaylistBean p: playlists)
                    p.setHost(profileDAO.getHostFromPlaylist(p.getId()));
                ((UserBean) profile).setPlaylists(playlists);

                //PLAYLIST A CUI L'UTENTE HA MESSO LIKE
                Collection<PlaylistBean> likedPlaylists = playlistDAO.getFromLikes(profile.getId());
                for(PlaylistBean p: likedPlaylists)
                    p.setHost(profileDAO.getHostFromPlaylist(p.getId()));
                ((UserBean) profile).setLikedPlaylists(likedPlaylists);

                //ARTISTI SEGUITI
                ((UserBean) profile).setArtists(profileDAO.getArtistsFromUser(profile.getId()));

                //UTENTI SEGUITI
                ((UserBean) profile).setFollowing(profileDAO.getFollowingFromUser(profile.getId()));

                //UTENTI FOLLOWER
                ((UserBean) profile).setFollowers(profileDAO.getFollowersFromUser(profile.getId()));

                //FEED
                UserBean poorify = (UserBean) profileDAO.get(1);
                DateFormatter dateFormatter = new DateFormatter();

                feed = new PlaylistBean();
                feed.setId(-1);
                feed.setHost(poorify);
                feed.setTitle("Feed");

                Collection<AddedBean> addedBeans = new TreeSet<>((AddedBean a, AddedBean b) -> a.getDate().compareTo(b.getDate()) != 0 ? a.getDate().compareTo(b.getDate()) : -1);
                Collection<TrackBean> tracks = trackDAO.generateFeed(profile.getId());
                for(TrackBean t: tracks) {
                    AlbumBean album = albumDAO.getFromTrack(t.getId());
                    album.setArtist(profileDAO.getFromAlbum(album.getId()));
                    t.setAlbum(album);
                    t.setFeaturing(profileDAO.getFeaturingFromTrack(t.getId()));

                    AddedBean addedBean = new AddedBean();
                    addedBean.setUser(poorify);
                    addedBean.setPlaylist(feed);
                    addedBean.setTrack(t);
                    addedBean.setDate(dateFormatter.getCurrentDateAzureSQLFormat());

                    addedBeans.add(addedBean);

                    feed.setDuration(feed.getDuration() + t.getDuration());
                }
                feed.setTracks(tracks.size());
                feed.setTracklist(addedBeans);


            } else if(profile.getRole() == ProfileBean.Role.ARTIST) {
                ((ArtistBean) profile).setAlbums(albumDAO.getFromArtist(profile.getId()));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        if(profile != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("Profile", profile);

            //NAVIGATOR
            Navigator navigator = new Navigator();
            navigator.setCurrent(new Page(0, Page.Type.HOME));
            session.setAttribute("Navigator", navigator);

            if(profile.getRole() != ProfileBean.Role.ARTIST) {
                //LISTENING QUEUE
                session.setAttribute("ListeningQueue", new ListeningQueue());
            }

            if(profile.getRole() == ProfileBean.Role.USER) {
                session.setAttribute("Feed", feed);
            }

            request.getRequestDispatcher("home.jsp").forward(request, response);
        }

    }
}
