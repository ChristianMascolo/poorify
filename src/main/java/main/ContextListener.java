package main;

import album.AlbumDAO;
import playlist.PlaylistDAO;
import profile.ProfileDAO;
import track.TrackDAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    private Connection connection;

    public ContextListener() {

    }

    /*
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            //GET APPLICATION PROPERTIES
            Properties properties = new Properties();
            properties.load(ContextListener.class.getClassLoader().getResourceAsStream("application.properties"));
            sce.getServletContext().setAttribute("Properties", properties);

            //CREATE CONNECTION TO AZURE
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("password"));
            sce.getServletContext().setAttribute("Connection", connection);

            //CREATE UTIL CLASSES
            Uploader uploader = new Uploader(properties);
            sce.getServletContext().setAttribute("Uploader", uploader);

            //CREATE DAOs
            ProfileDAO profileDAO = new ProfileDAO(connection);
            sce.getServletContext().setAttribute("ProfileDAO", profileDAO);

            AlbumDAO albumDAO = new AlbumDAO(connection);
            sce.getServletContext().setAttribute("AlbumDAO", albumDAO);

            TrackDAO trackDAO = new TrackDAO(connection);
            sce.getServletContext().setAttribute("TrackDAO", trackDAO);

            PlaylistDAO playlistDAO = new PlaylistDAO(connection);
            sce.getServletContext().setAttribute("PlaylistDAO", playlistDAO);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     */

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            //PROPERTIES
            Properties properties = new Properties();
            properties.load(ContextListener.class.getClassLoader().getResourceAsStream("application.properties"));
            sce.getServletContext().setAttribute("Properties", properties);
            
            //CREATE UTIL CLASSES
            Uploader uploader = new Uploader();
            sce.getServletContext().setAttribute("Uploader", uploader);

            //DATASOURCE
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource) envCtx.lookup("jdbc/PoorifyDB");
            connection = ds.getConnection();

            //CREATE DAOs
            ProfileDAO profileDAO = new ProfileDAO(connection);
            sce.getServletContext().setAttribute("ProfileDAO", profileDAO);

            AlbumDAO albumDAO = new AlbumDAO(connection);
            sce.getServletContext().setAttribute("AlbumDAO", albumDAO);

            TrackDAO trackDAO = new TrackDAO(connection);
            sce.getServletContext().setAttribute("TrackDAO", trackDAO);

            PlaylistDAO playlistDAO = new PlaylistDAO(connection);
            sce.getServletContext().setAttribute("PlaylistDAO", playlistDAO);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if(connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
