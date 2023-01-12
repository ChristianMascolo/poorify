package profile;

import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "SearchForGuests", value = "/SearchForGuests")
public class SearchForGuests extends HttpServlet {

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

        String search = request.getParameter("search");

        Collection<UserBean> users = null;
        try {
            users = profileDAO.searchUsersByAlias(search);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        if(users != null) {
            for(UserBean u: users) {
                jsonObject.append("id", u.getId());
                jsonObject.append("alias", u.getAlias());
                jsonObject.append("email", u.getEmail());
            }
        }
        response.getWriter().print(jsonObject);

    }
}
