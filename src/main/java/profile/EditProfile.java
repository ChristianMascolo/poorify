package profile;

import main.Uploader;
import org.json.JSONObject;
import playlist.PlaylistDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditProfile", value = "/EditProfile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 3, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 100)
public class EditProfile extends HttpServlet {

    public ProfileDAO profileDAO;

    @Override
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
        response.setContentType("application/json");

        ProfileBean profile = (ProfileBean) request.getSession().getAttribute("Profile");

        int id = profile.getId();
        String alias = request.getParameter("alias");
        String password = request.getParameter("password");
        Part part = request.getPart("cover");
        boolean isPublic = request.getParameter("isPublic") != null;
        String bio = request.getParameter("bio");

        try {

            if(part.getSize() > 0) {
                String filename = id + ".jpg";
                String localpath = getServletContext().getRealPath(filename);
                Uploader uploader = (Uploader) request.getServletContext().getAttribute("Uploader");
                uploader.upload(part.getInputStream(), Uploader.Container.PROFILE, localpath, filename);
            }

            if(profile.getRole() == ProfileBean.Role.USER) {

                UserBean user = (UserBean) profile;
                if(alias != null && !alias.equals("")) {
                    profileDAO.changeAlias(id, alias);
                    user.setAlias(alias);
                }
                if(password != null && !password.equals("")) {
                    profileDAO.changePassword(id, password);
                    user.setPassword(password);
                }
                if(isPublic) {
                    profileDAO.changePublic(id, !user.isPublic());
                    user.setPublic(!user.isPublic());
                }

            } else {
                ArtistBean artist = (ArtistBean) profile;
                if(alias != null && !alias.equals("")) {
                    profileDAO.changeAlias(id, alias);
                    artist.setAlias(alias);
                }
                if(password != null && !password.equals("")) {
                    profileDAO.changePassword(id, password);
                    artist.setPassword(password);
                }
                if(bio != null && !bio.equals("")) {
                    profileDAO.changeBio(id, bio);
                    artist.setBio(bio);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("Profile", profile);
        JSONObject jsonObject = new JSONObject();
        response.getWriter().print(jsonObject);
    }
}
