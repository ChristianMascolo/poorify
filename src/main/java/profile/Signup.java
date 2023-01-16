package profile;

import playlist.PlaylistDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Signup", value = "/Signup")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 3, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 100)
public class Signup extends HttpServlet {

    public ProfileDAO profileDAO;
    public PlaylistDAO playlistDAO;

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
        boolean outcome = false;

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String alias = request.getParameter("alias");

        //SAVE PROFILE TO DATABASE
        if(request.getParameter("type").equals("user")) {

            UserBean user = new UserBean();
            user.setEmail(email);
            user.setPassword(password);
            user.setAlias(alias);
            user.setBirthdate(request.getParameter("birthdate"));
            user.setPublic(false);

            NationBean nation = new NationBean();
            nation.setIso(request.getParameter("nation"));

            user.setNation(nation);

            try {
                outcome = profileDAO.add(user);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            ArtistBean artist = new ArtistBean();
            artist.setEmail(email);
            artist.setPassword(password);
            artist.setAlias(alias);
            artist.setBio(request.getParameter("bio"));

            try {
                outcome = profileDAO.add(artist);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ProfileBean profile = null;

        //GET NEW PROFILE & SAVE PROFILE PICTURE
        if(outcome) {
            try {

                profile = profileDAO.get(email, password);
                Part part = request.getPart("picture");
                String filename = "profile/" + profile.getId() + ".jpg";

                request.setAttribute("Upload", true);
                request.setAttribute("InputStream", part.getInputStream());
                request.setAttribute("Path", filename);
                request.getRequestDispatcher("files").include(request, response);

                //REDIRECT TO LOGIN
                request.setAttribute("email", profile.getEmail());
                request.setAttribute("password", profile.getPassword());
                request.getRequestDispatcher("Login").forward(request, response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
