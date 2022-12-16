package profile;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {

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

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        ProfileBean profile = null;
        try {
            profile = profileDAO.get(email, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(profile != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("Profile", profile);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        }

    }
}
