package profile;

import main.Uploader;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Signup", value = "/Signup")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 3, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 100)
public class Signup extends HttpServlet {

    private ProfileDAO profileDAO;

    public void init() throws ServletException {
        super.init();
        this.profileDAO = (ProfileDAO) super.getServletContext().getAttribute("ProfileDAO");
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
                String filename = profile.getId() + ".jpg";
                String localpath = getServletContext().getRealPath(filename);

                Uploader uploader = (Uploader) request.getServletContext().getAttribute("Uploader");
                uploader.upload(part.getInputStream(), Uploader.Container.PROFILE, localpath, filename);

                HttpSession session = request.getSession(true);
                session.setAttribute("Profile", profile);
                request.getRequestDispatcher("home.jsp").forward(request, response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
