package com.bizu.querycenter;

import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Role;
import com.bizu.querycenter.repository.EmployeeRepository;
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

	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;

	public QuerycenterApplication(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
		this.employeeRepository = employeeRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(QuerycenterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Employee employee1 = Employee.builder()
				._id(100)
				.name("testUser")
				.email("testuser@gmail.com")
				.password(passwordEncoder.encode("userpass"))
				.isActive(true)
				.role(Role.USER)
				.build();

		Employee employee2 = Employee.builder()
				._id(101)
				.name("TestAdmin")
				.email("testadmin@gmail.com")
				.password(passwordEncoder.encode("adminpass"))
				.isActive(true)
				.role(Role.ADMIN)
				.build();

		employeeRepository.save(employee1);
		employeeRepository.save(employee2);
		System.out.println(employee1);
		System.out.println(employee2);

		System.out.println("GetRole " + employee2.getRole());
		System.out.println("GetRole name " + employee2.getRole().name());
		System.out.println("GetRole getRole " + employee2.getRole().getRole());
	}
}
