package es.library.springboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ValidateException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public ValidateException(String message) {super(message);}
}
