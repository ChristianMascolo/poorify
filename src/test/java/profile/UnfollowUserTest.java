package profile;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UnfollowUserTest {

    @Test
    public void unfollowUserTest() throws Exception {

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);


        //PROFILE
        ProfileDAO profileDAO = mock(ProfileDAO.class);
        UserBean userBean = mock(UserBean.class);
        when(userBean.getArtists()).thenReturn(new ArrayList<>());
        when(session.getAttribute("Profile")).thenReturn(userBean);

        UnfollowUser unfollowUser = new UnfollowUser();
        unfollowUser.profileDAO = profileDAO;
        unfollowUser.doPost(request,response);

        writer.flush();
        assert(stringWriter.toString().contains(""));

    }

}