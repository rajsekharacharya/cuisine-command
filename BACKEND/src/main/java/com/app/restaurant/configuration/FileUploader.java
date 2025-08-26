package com.app.restaurant.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.app.restaurant.exception.ApplicationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileUploader {

    private static final Logger logger = Logger.getLogger(FileUploader.class.getName());

    @Value("${file.upload-dir.windows}")
    private String windowsUploadDir;

    @Value("${file.upload-dir.linux}")
    private String linuxUploadDir;

    /**
     * Uploads a file to the designated directory based on OS and type.
     *
     * @param file MultipartFile to upload
     * @param type Subdirectory under upload dir
     * @return relative path (for URLs)
     */
    public String uploadFile(MultipartFile file, String type) {
        try {
            if (file == null || file.isEmpty()) {
                throw new ApplicationException("File is empty or missing.");
            }

            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            if (originalFilename.contains("..")) {
                throw new ApplicationException("Invalid file path detected: " + originalFilename);
            }

            String extension = getFileExtension(originalFilename);
            String uniqueFilename = UUID.randomUUID() + (extension.isEmpty() ? "" : "." + extension);

            Path uploadDirPath = Paths.get(getBaseDir(), type);
            Files.createDirectories(uploadDirPath); // creates parent directories too

            Path uploadFilePath = uploadDirPath.resolve(uniqueFilename);
            Path relativePath = Paths.get("uploads", type, uniqueFilename);

            Files.write(uploadFilePath, file.getBytes());

            logger.log(Level.INFO, "File uploaded successfully: {0}", uploadFilePath);

            // Normalize for URL paths
            return relativePath.toString().replace("\\", "/");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "File upload failed: {0}", e.getMessage());
            throw new ApplicationException("File upload failed. Please try again later.");
        }
    }

    /**
     * Determines the base upload directory based on the OS.
     */
    private String getBaseDir() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win") ? windowsUploadDir : linuxUploadDir;
    }

    /**
     * Extracts the file extension from a filename.
     *
     * @param filename the original filename
     * @return file extension or empty string
     */
    private static String getFileExtension(String filename) {
        if (filename == null) {
            return "";
        }
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == filename.length() - 1) {
            return "";
        }
        return filename.substring(dotIndex + 1);
    }
}
