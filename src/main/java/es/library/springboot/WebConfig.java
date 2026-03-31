package es.library.springboot;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import es.library.springboot.services.StorageProperties;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final StorageProperties storageProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String location = "file:" + storageProperties.getBasePath() + "/";

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(location);
    }
}
