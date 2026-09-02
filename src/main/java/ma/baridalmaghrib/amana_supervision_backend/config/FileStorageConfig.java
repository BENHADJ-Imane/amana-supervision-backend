package ma.baridalmaghrib.amana_supervision_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

// Permet d'acceder aux images POD uploadees via une URL du type
// http://localhost:8080/uploads/pod/nom-fichier.jpg
@Configuration
public class FileStorageConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadPath = Paths.get("uploads/pod").toAbsolutePath().toUri().toString();
        registry.addResourceHandler("/uploads/pod/**")
                .addResourceLocations(uploadPath);
    }
}