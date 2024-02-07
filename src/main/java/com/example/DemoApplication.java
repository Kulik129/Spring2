package com.example;

import com.example.model.User;
import com.example.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication {
	private final UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@PostConstruct
	public void created() {
		User user = new User();
		user.setId(1L);
		user.setLogin("user");
		user.setPassword("pass");
		user.setRole("user");
		repository.save(user);

		User admin = new User();
		admin.setId(2L);
		admin.setLogin("admin");
		admin.setPassword("pass");
		admin.setRole("admin");
		repository.save(admin);

		User auth = new User();
		auth.setId(3L);
		auth.setLogin("auth");
		auth.setPassword("pass");
		auth.setRole("user");
		repository.save(auth);
	}

}
