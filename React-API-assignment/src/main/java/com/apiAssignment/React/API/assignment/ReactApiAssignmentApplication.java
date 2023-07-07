package com.apiAssignment.React.API.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.apiAssignment.React.API.assignment.controller.UserController;
import com.apiAssignment.React.API.assignment.dto.UserDTO;
import com.apiAssignment.React.API.assignment.model.User;


@SpringBootApplication
public class ReactApiAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactApiAssignmentApplication.class, args);

	}


}
