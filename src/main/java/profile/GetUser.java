package profile;

import album.AlbumDAO;
import navigation.Navigator;
import navigation.Page;
import org.json.JSONObject;
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
        response.setContentType("application/json");

        int id = Integer.parseInt(request.getParameter("id"));
        boolean new_page = Boolean.parseBoolean(request.getParameter("new_page"));

        UserBean user = null;
        try{
            user = (UserBean) profileDAO.get(id);

            Collection<PlaylistBean> playlists = playlistDAO.getPublicFromUser(user.getId());
            for(PlaylistBean p: playlists)
                p.setHost(profileDAO.getHostFromPlaylist(p.getId()));
            user.setPlaylists(playlists);

            Collection<PlaylistBean> likedPlaylists = playlistDAO.getFromLikes(id);
            for(PlaylistBean p: likedPlaylists)
                p.setHost(profileDAO.getHostFromPlaylist(p.getId()));
            user.setLikedPlaylists(likedPlaylists);

            user.setArtists(profileDAO.getArtistsFromUser(id));
            user.setFollowing(profileDAO.getFollowingFromUser(id));
            user.setFollowers(profileDAO.getFollowersFromUser(id));

        }catch(Exception e){
            e.printStackTrace();
        }

        Navigator navigator = (Navigator) request.getSession().getAttribute("Navigator");
        if(new_page)
            navigator.save();
        navigator.setCurrent(new Page(id, Page.Type.USER));

        request.getSession().setAttribute("User", user);
        JSONObject jsonObject = new JSONObject();
        response.getWriter().print(jsonObject);

    }
}
