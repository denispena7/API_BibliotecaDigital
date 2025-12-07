package es.library.springboot;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer 
{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) 
    {
        // "file:/" para rutas absolutas en Windows
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/C:/imagenes/");
    }
}
