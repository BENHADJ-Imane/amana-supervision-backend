package ma.baridalmaghrib.amana_supervision_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path uploadDir = Paths.get("uploads/pod");

    private static final List<String> ALLOWED_TYPES = List.of("image/jpeg", "image/png");

    public FileStorageService() {
        try {
            Files.createDirectories(uploadDir);
        } catch (IOException e) {
            throw new RuntimeException("Impossible de creer le dossier d'upload", e);
        }
    }

    // Sauvegarde le fichier sur le disque et renvoie l'URL relative pour y acceder.
    public String store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide.");
        }
        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException("Seules les images JPG et PNG sont acceptees.");
        }

        String extension = file.getOriginalFilename() != null && file.getOriginalFilename().contains(".")
                ? file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'))
                : "";
        String filename = UUID.randomUUID() + extension;

        try {
            Path target = uploadDir.resolve(filename);
            Files.copy(file.getInputStream(), target);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du fichier.", e);
        }

        return "/uploads/pod/" + filename;
    }
}