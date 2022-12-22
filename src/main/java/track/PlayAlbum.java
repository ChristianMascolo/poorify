package track;

import album.AlbumBean;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "PlayAlbum", value = "/PlayAlbum")
public class PlayAlbum extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        int index = Integer.parseInt(request.getParameter("index")) - 1;

        //GET ALBUM TRACKS
        AlbumBean album = (AlbumBean) request.getSession().getAttribute("Album");
        TrackBean[] tracks = album.getTracklist().toArray(new TrackBean[0]);

        //ADD ALBUM TRACKS TO QUEUE
        ListeningQueue queue = (ListeningQueue) request.getSession().getAttribute("ListeningQueue");
        queue.clearAutoQueue();

        for(int i = index; i < tracks.length; i++)
            queue.addToAutoQueue(tracks[i].getId());

        JSONObject jsonObject = new JSONObject();
        response.getWriter().print(jsonObject);
    }
}
