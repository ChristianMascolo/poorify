package profile;

import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "FollowUser", value = "/FollowUser")
public class FollowUser extends HttpServlet {

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

        UserBean profile = (UserBean) request.getSession().getAttribute("Profile");

        int follower = profile.getId();
        int followed = Integer.parseInt(request.getParameter("id"));

        boolean outcome = false;
        try{
            outcome = profileDAO.followUser(follower, followed);
            if(outcome)
                profile.getFollowing().add((UserBean) profileDAO.get(followed));

        }catch(Exception e){
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("outcome", outcome);
        response.getWriter().print(jsonObject);
    }
}
