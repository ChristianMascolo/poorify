package profile;

import album.AlbumDAO;
import playlist.PlaylistBean;
import playlist.PlaylistDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet(name = "GetUser", value = "/GetUser")
public class GetUser extends HttpServlet {

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
        int id = Integer.parseInt(request.getParameter("id"));
        ProfileBean profile = null;

        try{
            profile = profileDAO.get(id);

            if(profile.getRole() == ProfileBean.Role.USER){
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
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
}
