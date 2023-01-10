package profile;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EditProfileTest {

    @Test
    public void editUserAliasTest() throws Exception {

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("alias")).thenReturn("alias");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //PART
        Part part = mock(Part.class);
        when(part.getSize()).thenReturn(0L);
        when(request.getPart("cover")).thenReturn(part);

        //PROFILE BEAN
        UserBean userBean = new UserBean();
        userBean.setId(1);
        when(session.getAttribute("Profile")).thenReturn(userBean);

        //PROFILE DAO
        ProfileDAO profileDAO = mock(ProfileDAO.class);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        EditProfile editProfile = new EditProfile();
        editProfile.profileDAO = profileDAO;
        editProfile.doPost(request, response);

        writer.flush();
        assert(userBean.getAlias().equals("alias"));
    }

    @Test
    public void editUserPasswordTest() throws Exception {

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("password")).thenReturn("password");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //PART
        Part part = mock(Part.class);
        when(part.getSize()).thenReturn(0L);
        when(request.getPart("cover")).thenReturn(part);

        //PROFILE BEAN
        UserBean userBean = new UserBean();
        userBean.setId(1);
        when(session.getAttribute("Profile")).thenReturn(userBean);

        //PROFILE DAO
        ProfileDAO profileDAO = mock(ProfileDAO.class);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        EditProfile editProfile = new EditProfile();
        editProfile.profileDAO = profileDAO;
        editProfile.doPost(request, response);

        writer.flush();
        assert(userBean.getPassword().equals("password"));
    }

    @Test
    public void editUserPublicTest() throws Exception {

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("isPublic")).thenReturn("true");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //PART
        Part part = mock(Part.class);
        when(part.getSize()).thenReturn(0L);
        when(request.getPart("cover")).thenReturn(part);

        //PROFILE BEAN
        UserBean userBean = new UserBean();
        userBean.setId(1);
        when(session.getAttribute("Profile")).thenReturn(userBean);

        //PROFILE DAO
        ProfileDAO profileDAO = mock(ProfileDAO.class);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        EditProfile editProfile = new EditProfile();
        editProfile.profileDAO = profileDAO;
        editProfile.doPost(request, response);

        writer.flush();
        assert(userBean.isPublic() == true);
    }

    @Test
    public void editArtistAliasTest() throws Exception {

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("alias")).thenReturn("alias");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //PART
        Part part = mock(Part.class);
        when(part.getSize()).thenReturn(0L);
        when(request.getPart("cover")).thenReturn(part);

        //PROFILE BEAN
        ArtistBean artistBean = new ArtistBean();
        artistBean.setId(1);
        when(session.getAttribute("Profile")).thenReturn(artistBean);

        //PROFILE DAO
        ProfileDAO profileDAO = mock(ProfileDAO.class);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        EditProfile editProfile = new EditProfile();
        editProfile.profileDAO = profileDAO;
        editProfile.doPost(request, response);

        writer.flush();
        assert(artistBean.getAlias().equals("alias"));
    }

    @Test
    public void editArtistPasswordTest() throws Exception {

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("password")).thenReturn("password");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //PART
        Part part = mock(Part.class);
        when(part.getSize()).thenReturn(0L);
        when(request.getPart("cover")).thenReturn(part);

        //PROFILE BEAN
        ArtistBean artistBean = new ArtistBean();
        artistBean.setId(1);
        when(session.getAttribute("Profile")).thenReturn(artistBean);

        //PROFILE DAO
        ProfileDAO profileDAO = mock(ProfileDAO.class);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        EditProfile editProfile = new EditProfile();
        editProfile.profileDAO = profileDAO;
        editProfile.doPost(request, response);

        writer.flush();
        assert(artistBean.getPassword().equals("password"));
    }

    @Test
    public void editArtistBioTest() throws Exception {

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("bio")).thenReturn("bio");

        //SESSION
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        //PART
        Part part = mock(Part.class);
        when(part.getSize()).thenReturn(0L);
        when(request.getPart("cover")).thenReturn(part);

        //PROFILE BEAN
        ArtistBean artistBean = new ArtistBean();
        artistBean.setId(1);
        when(session.getAttribute("Profile")).thenReturn(artistBean);

        //PROFILE DAO
        ProfileDAO profileDAO = mock(ProfileDAO.class);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        EditProfile editProfile = new EditProfile();
        editProfile.profileDAO = profileDAO;
        editProfile.doPost(request, response);

        writer.flush();
        assert(artistBean.getBio().equals("bio"));
    }

}