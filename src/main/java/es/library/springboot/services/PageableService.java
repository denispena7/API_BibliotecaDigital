package es.library.springboot.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableService 
{
	public static Pageable getPageable(int page, int size, String sort)
	{
		int safeOffset = Math.max(page, 0);
		int safeLimit = Math.min(size, 10);
		
		return 	PageRequest.of(
				safeOffset, 
				safeLimit, 
				Sort.by(sort).ascending());
	}
}
