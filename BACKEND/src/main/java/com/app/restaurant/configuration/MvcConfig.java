package com.app.restaurant.configuration;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir.windows}")
    private String windowsUploadDir;

    @Value("${file.upload-dir.linux}")
    private String linuxUploadDir;

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name").toLowerCase();
        String uploadDir = os.contains("win") ? windowsUploadDir : linuxUploadDir;

        // Ensure trailing slash consistency
        String normalizedPath = Paths.get(uploadDir).toAbsolutePath().toUri().toString();

        registry
            .addResourceHandler("/uploads/**")
            .addResourceLocations(normalizedPath);

        // Optionally, you can log the configured upload directory for debugging
        // System.out.println("Serving uploads from: " + normalizedPath);
    }
}
