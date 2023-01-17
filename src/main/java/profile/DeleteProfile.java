package profile;

import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteProfile", value = "/DeleteProfile")
public class DeleteProfile extends HttpServlet {

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

        ProfileBean profile = (ProfileBean) request.getSession().getAttribute("Profile");

        try {

            if(profile.getRole() == ProfileBean.Role.USER)
                profileDAO.removeUser(profile.getId());
            else if(profile.getRole() == ProfileBean.Role.ARTIST)
                profileDAO.removeArtist(profile.getId());
            else {
                int id = Integer.parseInt(request.getParameter("id"));
                ProfileBean temp = profileDAO.get(id);
                if(temp.getRole() == ProfileBean.Role.USER)
                    profileDAO.removeUser(id);
                else
                    profileDAO.removeArtist(id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(profile.getRole() != ProfileBean.Role.OVERSEER)
            request.getRequestDispatcher("index.jsp").forward(request, response);
        else {
            response.setContentType("application/json");
            JSONObject jsonObject = new JSONObject();
            response.getWriter().print(jsonObject);
        }

    }
}
