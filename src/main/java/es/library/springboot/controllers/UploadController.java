package es.library.springboot.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

	private static final String RUTA_BASE = "C:/imagenes/";

	@PostMapping("/imagenes")
	public ResponseEntity<String> subirImagen(String carpeta, @RequestParam("file") MultipartFile file) 
	{
		try 
		{
			// Carpeta de destino
			File carpetaDestino = new File(RUTA_BASE);
			if (!carpetaDestino.exists()) 
			{
				carpetaDestino.mkdirs(); // crea carpetas si no existen
			}

			// Guardar archivo
			String rutaArchivo = RUTA_BASE + file.getOriginalFilename();
			file.transferTo(new File(rutaArchivo));

			// URL de acceso
			String urlAcceso = "http://localhost:8080/uploads/" + carpeta + "/" + file.getOriginalFilename();

			return ResponseEntity.ok(urlAcceso);

		} 
		catch (IOException e) 
		{
			return ResponseEntity.status(500).body("Error al subir la imagen: " + e.getMessage());
		}
	}
}