package main;

import com.azure.core.http.rest.Response;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.DeleteSnapshotsOptionType;
import com.azure.storage.blob.options.BlobUploadFromFileOptions;

import java.io.*;
import java.util.Properties;

public class Uploader {

    public enum Container {ALBUM, PLAYLIST, PROFILE, TRACK};

    public Uploader() {}

    /*
    public void upload(InputStream in, Container container, String localPath, String remoteFilename) throws IOException {

        //SAVE FILE ON LOCAL SERVER
        File file = new File(localPath);
        OutputStream out = new FileOutputStream(file);

        byte[] buffer = new byte[4096];
        while(in.read(buffer) >= 0)
            out.write(buffer);

        out.close();
        in.close();

        //SAVE TO REMOTE BLOB CONTAINER
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connection)
                .buildClient();
        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containers[container.ordinal()]);
        BlobClient blobClient = blobContainerClient.getBlobClient(remoteFilename);
        blobClient.uploadFromFile(localPath, true);

        //DELETE LOCAL TEMP FILE
        file.delete();
    }
     */

    public void upload(InputStream in, String path) throws IOException {

        File file = new File("C:/files/" + path);
        OutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[4096];
        while(in.read(buffer) >= 0)
            out.write(buffer);
        out.close();
        in.close();

    }

}
