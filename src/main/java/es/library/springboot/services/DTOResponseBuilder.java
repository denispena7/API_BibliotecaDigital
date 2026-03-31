package es.library.springboot.services;

import org.springframework.stereotype.Component;

import es.library.springboot.DTOs.responses.AuthorResponseDTO;
import es.library.springboot.DTOs.responses.BookResponseDTO;
import es.library.springboot.DTOs.responses.CategoryResponseDTO;
import es.library.springboot.DTOs.responses.UserSelfReadDTO;
import es.library.springboot.mapper.AuthorMapper;
import es.library.springboot.mapper.BookMapper;
import es.library.springboot.mapper.UserMapper;
import es.library.springboot.models.Author;
import es.library.springboot.models.Book;
import es.library.springboot.models.Category;
import es.library.springboot.models.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DTOResponseBuilder 
{
	private final FileStorageService fileStorageService;
    private final StorageProperties storageProperties;
	private final BookMapper bookMapper;
	private final AuthorMapper autMapper;
	private final UserMapper userMapper;

    public CategoryResponseDTO buildCatResponse(Category category) 
    {
        return new CategoryResponseDTO(
            category.getIdCategoria(),
            category.getNombreCategoria(),
            fileStorageService.buildPublicUrl(
                category.getImagenCategoria(),
                storageProperties.getCategoryPath()
            )
        );
    }
    
	public BookResponseDTO buildBookResponse(Book book) 
	{
	    BookResponseDTO dto = bookMapper.toBookDTO(book);

	    return new BookResponseDTO(
	            dto.idLibro(),
	            dto.tituloLibro(),
	            dto.anioPublicacion(),
	            dto.sinopsisLibro(),
	            dto.estadoLibro(),
	            fileStorageService.buildPublicUrl(book.getPortadaLibro(),storageProperties.getBookPath()),
	            dto.nombreAutor(),
	            dto.categorias()
	    );
	}
	
	public AuthorResponseDTO buildAuthorResponse(Author aut) 
    {
	    AuthorResponseDTO dto = autMapper.toAutDTO(aut);

	    return new AuthorResponseDTO(
	            dto.idAutor(),
	            dto.nombreAutor(),
	            dto.nacionalidadAutor(),
	            dto.fechaNacimiento(),
	            fileStorageService.buildPublicUrl(aut.getImagenAutor(),storageProperties.getAuthorPath()
	    ));
	}
	
	public UserSelfReadDTO buildUserResponse(User user) 
	{
	    UserSelfReadDTO dto = userMapper.toUserDTO(user);

	    return new UserSelfReadDTO(
	    		dto.nombreRealUsuario(),
	    		dto.apellidosUsuario(),
	    		dto.direccionUsuario(),
	    		dto.ciudadUsuario(),
	    		dto.localidadUsuario(),
	    		dto.cpUsuario(),
	    		dto.telefonoUsuario(),
	    		dto.emailUsuario(),
	        fileStorageService.buildPublicUrl(user.getIconoUsuario(),storageProperties.getUserPath()));
	}
}
