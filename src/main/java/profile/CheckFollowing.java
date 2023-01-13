package profile;

import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CheckFollowing", value = "/CheckFollowing")
public class CheckFollowing extends HttpServlet {

    public ProfileDAO profileDAO;

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

        int user = ((ProfileBean) request.getSession().getAttribute("Profile")).getId();
        int followed = Integer.parseInt(request.getParameter("id"));
        String type = request.getParameter("type");

        boolean outcome = false;
        try {
            outcome = profileDAO.checkFollowing(user, followed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("outcome", outcome);
        jsonObject.append("type", type);
        response.getWriter().print(jsonObject);
    }
}
