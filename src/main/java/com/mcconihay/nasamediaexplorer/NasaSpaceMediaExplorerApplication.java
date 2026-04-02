package com.mcconihay.nasamediaexplorer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mcconihay.nasamediaexplorer.entity.UserEntity;
import com.mcconihay.nasamediaexplorer.service.UserService;

@SpringBootApplication
public class NasaSpaceMediaExplorerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NasaSpaceMediaExplorerApplication.class, args);
	}

	@Bean
	CommandLineRunner seedAdmin(UserService userService,
	                            PasswordEncoder passwordEncoder) {

		return args -> {

			if (userService.findByUsername("admin").isEmpty()) {

				UserEntity admin = new UserEntity();
				admin.setUsername("admin");
				admin.setEmail("admin@nasa.com");
				admin.setPasswordHash(passwordEncoder.encode("password"));
				admin.setEnabled(true);

				userService.createUser(admin);

				System.out.println("Default admin user created.");
			}
		};
	}
}