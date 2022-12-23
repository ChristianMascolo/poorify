package profile;

import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "FollowUser", value = "/FollowUser")
public class FollowUser extends HttpServlet {

    private ProfileDAO profileDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        profileDAO = (ProfileDAO) super.getServletContext().getAttribute("ProfileDAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        int idFollower = (int) request.getSession().getAttribute("enduser");
        int idFollowed = Integer.parseInt(request.getParameter("followed"));

        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("outcome",profileDAO.followUser(idFollower,idFollowed));
            response.getWriter().print(jsonObject);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
