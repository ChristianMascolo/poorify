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

class FollowUserTest {

    @Test
    public void followUserTest() throws Exception {

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
        when(userBean.getId()).thenReturn(2);
        when(userBean.getArtists()).thenReturn(new ArrayList<>());
        when(session.getAttribute("Profile")).thenReturn(userBean);

        UserBean followed = mock(UserBean.class);
        followed.setId(1);
        when(profileDAO.get(1)).thenReturn(followed);

        when(profileDAO.followUser(2, 1)).thenReturn(true);

        FollowUser followUser = new FollowUser();
        followUser.profileDAO = profileDAO;
        followUser.doPost(request,response);

        writer.flush();
        System.out.println(stringWriter.toString());
        assert(stringWriter.toString().contains("true"));

    }

}