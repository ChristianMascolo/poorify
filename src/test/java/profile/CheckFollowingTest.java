package profile;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CheckFollowingTest {

    @Test
    public void checkFollowingTest() throws Exception{
        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("type")).thenReturn("type");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //UTILS
        UserBean userBean = mock(UserBean.class);
        when(userBean.getId()).thenReturn(2);
        ProfileDAO profileDAO = mock(ProfileDAO.class);
        when(request.getSession().getAttribute("Profile")).thenReturn(userBean);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //SERVLET
        CheckFollowing checkFollowing = new CheckFollowing();
        checkFollowing.profileDAO = profileDAO;
        checkFollowing.doPost(request,response);

        writer.flush();
        System.out.println(stringWriter.toString());
        assert(stringWriter.toString().contains("outcome"));
    }
}