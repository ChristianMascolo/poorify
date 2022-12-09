package main;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Properties;

@WebServlet(name = "DownloadServlet", value = "/DownloadServlet")
public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("application/json");

            Properties properties = (Properties) request.getServletContext().getAttribute("Properties");

            //SAVE FILE TO BLOB
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(properties.getProperty("connectionString"))
                    .buildClient();
            BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient("track");
            BlobClient blobClient = blobContainerClient.getBlobClient("temp.mp3");
            String path = blobClient.getBlobUrl();

            JSONObject jsonObject = new JSONObject();
            jsonObject.append("path", path);

            response.getWriter().print(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
