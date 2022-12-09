package profile;

import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "CheckNations", value = "/CheckNations")
public class CheckNations extends HttpServlet {

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
        response.setContentType("application/json");
        JSONObject jsonObject = new JSONObject();
        try {
            Collection<NationBean> nations = profileDAO.getAllNations();
            for(NationBean n: nations) {
                jsonObject.append("iso", n.getIso());
                jsonObject.append("name", n.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.getWriter().print(jsonObject);
    }
}
