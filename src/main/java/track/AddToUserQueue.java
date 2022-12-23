package track;

import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddToUserQueue", value = "/AddToUserQueue")
public class AddToUserQueue extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        ListeningQueue queue = (ListeningQueue) request.getSession().getAttribute("ListeningQueue");
        queue.addToUserQueue((int)request.getAttribute("id"));

        JSONObject jsonObj = new JSONObject();
        response.getWriter().print(jsonObj);
    }
}