package profile;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CheckNationsTest {

    @Test
    public void checkNations() throws Exception {

        //NATIONS
        NationBean nationBean = new NationBean("ITA", "Italy");

        //PROFILEDAO
        ProfileDAO profileDAO = mock(ProfileDAO.class);
        when(profileDAO.getAllNations()).thenReturn(Arrays.asList(nationBean));

        //REQUEST & RESPONSE
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        //WRITER
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        CheckNations checkNations = new CheckNations();
        checkNations.profileDAO = profileDAO;
        checkNations.doPost(request, response);

        writer.flush();
        assert(stringWriter.toString().contains("ITA"));

    }

}