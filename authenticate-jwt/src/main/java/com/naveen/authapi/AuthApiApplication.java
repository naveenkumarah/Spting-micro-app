package com.naveen.authapi;

import com.naveen.authapi.entities.Role;
import com.naveen.authapi.entities.User;
import com.naveen.authapi.repositories.RoleRepository;
import com.naveen.authapi.repositories.UserRepository;
import com.naveen.authapi.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class AuthApiApplication implements CommandLineRunner {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private  PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(AuthApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Set<Role> roleSet = new HashSet<Role>(){{
			add(new Role(Role.roles.ADMIN));
			add(new Role(Role.roles.USER));
		}};

		roleRepository.saveAll(roleSet);

		userRepository.save(User.builder().email("naveen@gmail.com")
				.fullName("Naveen")
				.password(passwordEncoder.encode("Password"))
				.roles(roleRepository.findAllByName(Role.roles.ADMIN)).build());

		userRepository.save(User.builder().email("kumar@gmail.com")
				.fullName("Kumar")
				.password(passwordEncoder.encode("Password"))
				.roles(roleSet).build());
	}
}
