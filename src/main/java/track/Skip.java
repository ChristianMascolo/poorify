package track;

import org.json.JSONObject;
import profile.ArtistBean;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sound.midi.Track;
import java.io.IOException;

@WebServlet(name = "Skip", value = "/Skip")
public class Skip extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        ListeningQueue queue = (ListeningQueue) request.getSession().getAttribute("ListeningQueue");
        int id = queue.poll();

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("id", id);
        response.getWriter().print(jsonObject);
    }
}
