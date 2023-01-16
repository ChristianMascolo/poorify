package album;

import org.json.JSONObject;
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

            if(cover.getSize() > 0) {
                String filename = "album/" + album_id + ".jpg";
                request.setAttribute("Upload", true);
                request.setAttribute("InputStream", cover.getInputStream());
                request.setAttribute("Path", filename);
                request.getRequestDispatcher("files").include(request, response);
            }

            for(int i = 0; i < tracks; i++) {
                String track_title = request.getParameter("title-" + (i + 1));
                int track_duration = Integer.parseInt(request.getParameter("duration-" + (i + 1)));
                int track_genre = Integer.parseInt(request.getParameter("genre-" + (i + 1)));

                trackDAO.add(album_id, track_title, i + 1, track_duration, track_genre);
                int track_id = trackDAO.get(album_id, track_title);

                Part audio = request.getPart("edit-audio-track-" + (i + 1));
                if(audio.getSize() > 0) {
                    String filename = "track/" + track_id + ".mp3";
                    request.setAttribute("Upload", true);
                    request.setAttribute("InputStream", audio.getInputStream());
                    request.setAttribute("Path", filename);
                    request.getRequestDispatcher("files").include(request, response);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        response.getWriter().print(jsonObject);
    }
}
