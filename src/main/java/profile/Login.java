package profile;

import playlist.PlaylistDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {

    private ProfileDAO profileDAO;
    private PlaylistDAO playlistDAO;

    public void init() throws ServletException {
        super.init();
        this.profileDAO = (ProfileDAO) super.getServletContext().getAttribute("ProfileDAO");
        this.playlistDAO = (PlaylistDAO) super.getServletContext().getAttribute("PlaylistDAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        ProfileBean profile = null;
        try {
            profile = profileDAO.get(email, password);
            if(profile.getRole() == ProfileBean.Role.USER) {
                ((UserBean) profile).setPlaylists(playlistDAO.getFromUser(profile.getId()));
                ((UserBean) profile).setLikedPlaylists(playlistDAO.getFromLikes(profile.getId()));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        if(profile != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("Profile", profile);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        }

    }
}
