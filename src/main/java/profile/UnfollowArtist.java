package profile;

import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UnfollowArtist", value = "/UnfollowArtist")
public class UnfollowArtist extends HttpServlet {

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

        UserBean profile = (UserBean) request.getSession().getAttribute("Profile");

        int user = profile.getId();
        int artist = Integer.parseInt(request.getParameter("id"));

        boolean outcome = false;
        try{
            outcome = profileDAO.unfollowArtist(user, artist);
            if(outcome)
                profile.getArtists().removeIf(a -> (a.getId() == artist));

        }catch(Exception e){
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("outcome", outcome);
        response.getWriter().print(jsonObject);
    }
}
