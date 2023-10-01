package hr.tvz.project.gym.controller;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hr.tvz.project.gym.exception.FieldNotFoundException;
import hr.tvz.project.gym.exception.NotUniqueFieldException;
import hr.tvz.project.gym.model.User;
import hr.tvz.project.gym.service.RoleService;
import hr.tvz.project.gym.service.UserService;

@RestController
public class UserController {

	
	private UserService userService;
	private RoleService roleService;
	private MessageSource messageSource;
	
	public UserController(UserService userService, RoleService roleService, MessageSource messageSource) {
		this.userService = userService;
		this.roleService = roleService;
		this.messageSource = messageSource;
	}
	
	
	@GetMapping("/users")
	List<User> all() {
		return userService.findAllUsers();
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id){
		User user = userService.getUserById(id);
		if (user != null) {
			return ResponseEntity.ok(user);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

	
	@PostMapping("/users")
	ResponseEntity<String> newUser(@RequestBody User newUser) {
		ResponseEntity<String> respEnt = null;
		try {
			userService.create(newUser);
			respEnt = ResponseEntity.ok(messageSource.getMessage("data.user.added.success", null, LocaleContextHolder.getLocale()));
		} catch (NoSuchMessageException e) {
			e.printStackTrace();
		} catch (NotUniqueFieldException e) {
			e.printStackTrace();
			respEnt = ResponseEntity.ok(e.getMessage());
		} catch (FieldNotFoundException e) {
			e.printStackTrace();
			respEnt = ResponseEntity.ok(e.getMessage());
		}
		
		return respEnt;
	}
	
}
