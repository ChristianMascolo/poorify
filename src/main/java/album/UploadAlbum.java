package album;

import main.Uploader;
import org.json.JSONObject;
import playlist.PlaylistDAO;
import profile.ArtistBean;
import track.TrackDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UploadAlbum", value = "/UploadAlbum")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 3, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 100)
public class UploadAlbum extends HttpServlet {

    public  AlbumDAO albumDAO;
    public  TrackDAO trackDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.albumDAO = (AlbumDAO) super.getServletContext().getAttribute("AlbumDAO");
        this.trackDAO = (TrackDAO) super.getServletContext().getAttribute("TrackDAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        ArtistBean artist = (ArtistBean) request.getSession().getAttribute("Profile");

        String title = request.getParameter("title");
        int duration = Integer.parseInt(request.getParameter("duration"));
        int tracks = Integer.parseInt(request.getParameter("tracks"));
        int year = Integer.parseInt(request.getParameter("year"));
        String type = request.getParameter("edit-type");
        Part cover = request.getPart("cover");

        try {

            albumDAO.add(artist.getId(), title, tracks, duration, year, type);
            int album_id = albumDAO.get(artist.getId(), title);

            String filename = album_id + ".jpg";
            String localpath = getServletContext().getRealPath(filename);
            Uploader uploader = (Uploader) request.getServletContext().getAttribute("Uploader");
            uploader.upload(cover.getInputStream(), Uploader.Container.ALBUM, localpath, filename);

            for(int i = 0; i < tracks; i++) {
                String track_title = request.getParameter("title-" + (i + 1));
                int track_duration = Integer.parseInt(request.getParameter("duration-" + (i + 1)));
                int track_genre = Integer.parseInt(request.getParameter("genre-" + (i + 1)));

                trackDAO.add(album_id, track_title, i + 1, track_duration, track_genre);
                int track_id = trackDAO.get(album_id, track_title);

                Part audio = request.getPart("edit-audio-track-" + (i + 1));
                filename = track_id + ".mp3";
                localpath = getServletContext().getRealPath(filename);
                uploader.upload(audio.getInputStream(), Uploader.Container.TRACK, localpath, filename);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        response.getWriter().print(jsonObject);
    }
}
