package track;

import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddToUserQueue", value = "/AddToUserQueue")
public class AddToQueue extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        int id = Integer.parseInt(request.getParameter("id"));

        ListeningQueue queue = (ListeningQueue) request.getSession().getAttribute("ListeningQueue");
        queue.addToUserQueue(id);

        JSONObject jsonObject = new JSONObject();
        response.getWriter().print(jsonObject);
    }
}
