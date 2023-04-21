package com.bizu.querycenter;

import com.bizu.querycenter.dto.Request.SaveEmployeeRequest;
import com.bizu.querycenter.dto.Response.EmployeeResponse;
import com.bizu.querycenter.model.Role;
import com.bizu.querycenter.repository.RoleRepository;
import com.bizu.querycenter.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableMongoRepositories
@EnableCaching
public class QuerycenterApplication implements CommandLineRunner {

	private final EmployeeService employeeService;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public QuerycenterApplication(EmployeeService employeeService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.employeeService = employeeService;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(QuerycenterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Role roleAdmin = Role.builder()
				._id(1)
				.name("ADMIN")
				.build();

		Role roleUser = Role.builder()
				._id(2)
				.name("USER")
				.build();

		roleRepository.save(roleAdmin);
		roleRepository.save(roleUser);

		SaveEmployeeRequest employee1 = SaveEmployeeRequest.builder()
				.name("Duru Kuşlu")
				.email("duru@gmail.com")
				.password(passwordEncoder.encode("durupass"))
				.build();

		SaveEmployeeRequest employee2 = SaveEmployeeRequest.builder()
				.name("AliMuratKuslu")
				.email("alim@gmail.com")
				.password(passwordEncoder.encode("alipass"))
				.build();

		EmployeeResponse response1 = employeeService.saveEmployee(employee1);
		EmployeeResponse response2 = employeeService.saveEmployee(employee2);
		System.out.println(response1);
		System.out.println(response2);

	}
}
