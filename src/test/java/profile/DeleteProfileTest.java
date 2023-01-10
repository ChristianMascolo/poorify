package profile;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteProfileTest {

    @Test
    public void deleteUserTest() throws Exception {

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //PROFILE
        ProfileBean profileBean = new UserBean();
        when(session.getAttribute("Profile")).thenReturn(profileBean);

        //PROFILEDAO
        ProfileDAO profileDAO = mock(ProfileDAO.class);

        //DISPATCHER
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("index.jsp")).thenReturn(dispatcher);

        DeleteProfile deleteProfile = new DeleteProfile();
        deleteProfile.profileDAO = profileDAO;
        deleteProfile.doPost(request, response);
    }

    @Test
    public void deleteArtistTest() throws Exception {

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //PROFILE
        ProfileBean profileBean = new ArtistBean();
        when(session.getAttribute("Profile")).thenReturn(profileBean);

        //PROFILEDAO
        ProfileDAO profileDAO = mock(ProfileDAO.class);

        //DISPATCHER
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("index.jsp")).thenReturn(dispatcher);

        DeleteProfile deleteProfile = new DeleteProfile();
        deleteProfile.profileDAO = profileDAO;
        deleteProfile.doPost(request, response);
    }

    @Test
    public void deleteArtistFromOverseerTest() throws Exception {

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn("1");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //PROFILE
        ProfileBean profileBean = new OverseerBean();
        when(session.getAttribute("Profile")).thenReturn(profileBean);

        ProfileBean toDelete = new ArtistBean();

        //PROFILEDAO
        ProfileDAO profileDAO = mock(ProfileDAO.class);
        when(profileDAO.get(1)).thenReturn(toDelete);

        //DISPATCHER
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("homepage.jsp")).thenReturn(dispatcher);

        DeleteProfile deleteProfile = new DeleteProfile();
        deleteProfile.profileDAO = profileDAO;
        deleteProfile.doPost(request, response);
    }

    @Test
    public void deleteUserFromOverseerTest() throws Exception {

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn("1");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //PROFILE
        ProfileBean profileBean = new OverseerBean();
        when(session.getAttribute("Profile")).thenReturn(profileBean);

        ProfileBean toDelete = new UserBean();

        //PROFILEDAO
        ProfileDAO profileDAO = mock(ProfileDAO.class);
        when(profileDAO.get(1)).thenReturn(toDelete);

        //DISPATCHER
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("homepage.jsp")).thenReturn(dispatcher);

        DeleteProfile deleteProfile = new DeleteProfile();
        deleteProfile.profileDAO = profileDAO;
        deleteProfile.doPost(request, response);
    }

}
