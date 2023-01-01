package profile;

import album.AlbumDAO;
import navigation.Navigator;
import navigation.Page;
import playlist.PlaylistBean;
import playlist.PlaylistDAO;
import track.ListeningQueue;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {

    private ProfileDAO profileDAO;
    private PlaylistDAO playlistDAO;
    private AlbumDAO albumDAO;

    public void init() throws ServletException {
        super.init();
        this.profileDAO = (ProfileDAO) super.getServletContext().getAttribute("ProfileDAO");
        this.playlistDAO = (PlaylistDAO) super.getServletContext().getAttribute("PlaylistDAO");
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

            //LISTENING QUEUE
            session.setAttribute("ListeningQueue", new ListeningQueue());

            request.getRequestDispatcher("home.jsp").forward(request, response);
        }

    }
}
