package main;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.net.URLDecoder;

@WebServlet(name = "FileSystemManager", value = "/FileSystemManager")
public class FileSystemManager extends HttpServlet {

    private static final int BUFFER = 4096;
    private String filePath;

    public void init() throws ServletException {
        this.filePath = "/files";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Boolean upload = request.getAttribute("Upload") != null;

        if(!upload) {

            String requestedFile = request.getPathInfo();
            File file = new File(filePath, URLDecoder.decode(requestedFile, "UTF-8"));
            String contentType = getServletContext().getMimeType(file.getName());

            response.reset();
            response.setBufferSize(BUFFER);
            response.setContentType(contentType);
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

            BufferedInputStream input = null;
            BufferedOutputStream output = null;

            input = new BufferedInputStream(new FileInputStream(file), BUFFER);
            output = new BufferedOutputStream(response.getOutputStream(), BUFFER);

            byte[] buffer = new byte[BUFFER];
            int length;
            while ((length = input.read(buffer)) > 0)
                output.write(buffer, 0, length);

            output.close();
            input.close();

        } else {

            InputStream in = (InputStream) request.getAttribute("InputStream");
            String path = (String) request.getAttribute("Path");

            File file = new File(filePath + "/" + path);
            OutputStream out = new FileOutputStream(file);
            byte[] buffer = new byte[BUFFER];
            while(in.read(buffer) >= 0)
                out.write(buffer);
            out.close();
            in.close();

        }


    }
}
