package playlist;

import org.json.JSONObject;
import profile.ProfileBean;
import profile.UserBean;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditPlaylist", value = "/EditPlaylist")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 3, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 100)
public class EditPlaylist extends HttpServlet {

    public PlaylistDAO playlistDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.playlistDAO = (PlaylistDAO) super.getServletContext().getAttribute("PlaylistDAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        boolean isPublic = request.getParameter("isPublic") != null;
        boolean isCollaborative = request.getParameter("isCollaborative") != null;

        Part part = request.getPart("cover");

        try {

            if(part.getSize() > 0) {
                String filename = "playlist/" + id + ".jpg";
                request.setAttribute("Upload", true);
                request.setAttribute("InputStream", part.getInputStream());
                request.setAttribute("Path", filename);
                request.getRequestDispatcher("files").include(request, response);
            }

            PlaylistBean playlist = playlistDAO.get(id);

            if(title != null && !title.equals(""))
                playlistDAO.changeTitle(id, title);

            if(isPublic) {
                if(playlist.isPublic())
                    playlistDAO.setPrivate(id);
                else
                    playlistDAO.setPublic(id);
            }

            if(isCollaborative) {
                if(playlist.isCollaborative())
                    playlistDAO.setSingle(id);
                else
                    playlistDAO.setCollaborative(id);
            }

            UserBean user = (UserBean) request.getSession().getAttribute("Profile");
            user.getPlaylists().removeIf(p -> (p.getId() == id));

            playlist = playlistDAO.get(id);
            playlist.setHost(user);
            user.getPlaylists().add(playlist);


        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        response.getWriter().print(jsonObject);
    }
}
