package profile;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.rmi.server.ExportException;

import org.json.JSONObject;

@WebServlet(name = "CheckCredentials", value = "/CheckCredentials")
public class CheckCredentials extends HttpServlet {

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
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.append("outcome", profileDAO.check(request.getParameter("email"), request.getParameter("password")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.getWriter().print(jsonObject);
    }
}
