package com.task.management;

import com.task.management.entity.Task;
import com.task.management.entity.User;
import com.task.management.repository.UserRepository;
import com.task.management.service.GuestService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TaskManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner(UserRepository userService, GuestService service) {
//		return args -> {
//
//			var user = User.builder()
//					.name("Yanti")
//					.username("yanti")
//					.password("12345")
//					.division("IT")
//					.build();
//
//			List<Task> tasks = new ArrayList<>();
//			tasks.add(Task
//					.builder()
//					.user(user)
//							.deadline(LocalDateTime.now())
//							.description("Test")
//							.title("Test")
//					.build());
//			user.setTasks(tasks);
//
//			userService.save(user);
//
//			var user2 = User.builder()
//					.name("anto")
//					.username("anto")
//					.password("12345")
//					.division("IT")
//					.build();
//			service.save(user2);
//
//
//		};
//	}
}
