package es.library.springboot.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import es.library.springboot.exceptions.ValidateException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileStorageService 
{
	private final StorageProperties storageProperties;

    private static final Set<String> ALLOWED_EXTENSIONS =
            Set.of("jpg", "jpeg", "png", "webp", "avif", "svg");

    public String saveFile(MultipartFile file, String subDir) 
    {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        if (extension == null || !ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new ValidateException("Invalid image type");
        }

        String filename = file.getOriginalFilename();
        Path targetDir = storageProperties.resolvePath(subDir);
        Path targetPath = targetDir.resolve(filename);

        try {
            Files.createDirectories(targetDir);
            Files.copy(file.getInputStream(), targetPath);
        } catch (IOException e) {
            throw new RuntimeException("Error saving file", e);
        }

        return filename;
    }

    public void deleteFileIfExists(String filename, String folder) {

        if (filename == null || filename.isBlank()) return;

        Path path = Paths.get(
                storageProperties.getBasePath(),
                folder,
                filename
            );

        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting file", e);
        }
    }

    public String buildPublicUrl(String filename, String subDir) {
        return storageProperties.buildPublicUrl(subDir, filename);
    }
}
