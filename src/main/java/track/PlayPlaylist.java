package track;

import album.AlbumBean;
import org.json.JSONObject;
import playlist.AddedBean;
import playlist.PlaylistBean;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "PlayPlaylist", value = "/PlayPlaylist")
public class PlayPlaylist extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        int index = Integer.parseInt(request.getParameter("index"));

        //GET PLAYLIST TRACKS
        PlaylistBean playlistBean = (PlaylistBean) request.getSession().getAttribute("Playlist");
        AddedBean[] tracks = playlistBean.getTracklist().toArray(new AddedBean[0]);

        //ADD ALBUM TRACKS TO QUEUE
        ListeningQueue queue = (ListeningQueue) request.getSession().getAttribute("ListeningQueue");
        queue.clearAutoQueue();

        for(int i = index; i < tracks.length; i++)
            queue.addToAutoQueue(tracks[i].getTrack().getId());

        JSONObject jsonObject = new JSONObject();
        response.getWriter().print(jsonObject);

    }
}
