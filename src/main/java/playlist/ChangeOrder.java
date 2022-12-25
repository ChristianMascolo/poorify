package playlist;

import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ChangeOrder", value = "/ChangeOrder")
public class ChangeOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String order = request.getParameter("order");
        switch (order) {
            case "date": request.getSession().setAttribute("Order", PlaylistBean.Order.DATE); break;
            case "title": request.getSession().setAttribute("Order", PlaylistBean.Order.TITLE); break;
            case "duration": request.getSession().setAttribute("Order", PlaylistBean.Order.DURATION); break;
            case "artist": request.getSession().setAttribute("Order", PlaylistBean.Order.ARTIST); break;
            case "album": request.getSession().setAttribute("Order", PlaylistBean.Order.ALBUM); break;
        }

        JSONObject jsonObject = new JSONObject();
        response.getWriter().print(jsonObject);

    }
}
