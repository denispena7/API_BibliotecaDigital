package es.library.springboot.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class WraperResponse<T> 
{
	private T data;
	private boolean ok;
	private String message;
}