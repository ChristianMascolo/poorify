package profile;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteProfile", value = "/DeleteProfile")
public class DeleteProfile extends HttpServlet {

    private ProfileDAO profileDAO;

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

        ProfileBean profile = (ProfileBean) request.getSession().getAttribute("Profile");

        try {

            if(profile.getRole() == ProfileBean.Role.USER)
                profileDAO.removeUser(profile.getId());
            else
                profileDAO.removeArtist(profile.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
