package es.library.springboot.services;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "storage")
@Getter
@Setter
public class StorageProperties 
{
	private String basePath;
    private String publicBaseUrl;

    private String categoryPath;
    private String bookPath;
    private String userPath;
    private String authorPath;

    public Path resolvePath(String subDir) {
        return Paths.get(basePath, subDir);
    }

    public String buildPublicUrl(String subDir, String filename) {
        return publicBaseUrl + "/" + subDir + "/" + filename;
    }
}
