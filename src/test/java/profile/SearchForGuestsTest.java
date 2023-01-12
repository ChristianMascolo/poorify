package profile;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchForGuestsTest {

    @Test
    public void searchForGuestsTest() throws Exception {

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("search")).thenReturn("c");

        //USERBEAN
        UserBean userBean = new UserBean();
        userBean.setId(1);
        userBean.setAlias("Clare Ingram");
        userBean.setEmail("chiara@gmail.com");

        //DAO
        ProfileDAO profileDAO = mock(ProfileDAO.class);
        when(profileDAO.searchUsersByAlias("c")).thenReturn(Arrays.asList(userBean));

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        SearchForGuests searchForGuests = new SearchForGuests();
        searchForGuests.profileDAO = profileDAO;
        searchForGuests.doPost(request, response);

        writer.flush();

        System.out.println(stringWriter.toString());
        assert(stringWriter.toString().contains("Clare Ingram"));

    }

}