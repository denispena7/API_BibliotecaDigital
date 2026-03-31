package es.library.springboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import es.library.springboot.config.Role;
import es.library.springboot.models.Rol;
import es.library.springboot.repositories.RolRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner 
{
	private final RolRepository roleRepo;

	@Override
	public void run(String... args) throws Exception 
	{
        if (roleRepo.count() == 0) {
        	roleRepo.save(new Rol(null, Role.ADMIN));
        	roleRepo.save(new Rol(null, Role.EMPLOYEE));
            roleRepo.save(new Rol(null, Role.USER));
        }
	}
}