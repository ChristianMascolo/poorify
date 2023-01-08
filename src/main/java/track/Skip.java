package track;

import album.AlbumDAO;
import org.json.JSONObject;
import profile.ArtistBean;
import profile.ProfileBean;
import profile.ProfileDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sound.midi.Track;
import java.io.IOException;

@WebServlet(name = "Skip", value = "/Skip")
public class Skip extends HttpServlet {

    private TrackDAO trackDAO;

    public void init() throws ServletException {
        super.init();
        this.trackDAO = (TrackDAO) super.getServletContext().getAttribute("TrackDAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        ListeningQueue queue = (ListeningQueue) request.getSession().getAttribute("ListeningQueue");
        int id = queue.poll();

        if(id == 0) {
            int user = ((ProfileBean) request.getSession().getAttribute("Profile")).getId();
            try {
                id = trackDAO.shuffle(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("id", id);
        response.getWriter().print(jsonObject);
    }
}
