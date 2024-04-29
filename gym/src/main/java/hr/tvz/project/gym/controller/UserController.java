package hr.tvz.project.gym.controller;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.tvz.project.gym.exception.FieldNotFoundException;
import hr.tvz.project.gym.exception.NotUniqueFieldException;
import hr.tvz.project.gym.model.Role;
import hr.tvz.project.gym.model.User;
import hr.tvz.project.gym.service.RoleService;
import hr.tvz.project.gym.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	private UserService userService;
	private MessageSource messageSource;
	
	public UserController(UserService userService, RoleService roleService, MessageSource messageSource) {
		this.userService = userService;
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
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/users/role/{username}")
	public ResponseEntity<Role> getUserRoleByUsername(@PathVariable String username){
		Role role = userService.getUserRoleByUsername(username);
		if(role != null) {
			return ResponseEntity.ok(role);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	
	@PostMapping("/users")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
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
	
	@PostMapping("/users/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User existingUser){
		ResponseEntity<String> respEnt = null;
		try {
			existingUser.setId(id);
			userService.update(existingUser);
			respEnt = ResponseEntity.ok(messageSource.getMessage("data.user.updated.success", null, LocaleContextHolder.getLocale()));
		} catch (NoSuchMessageException e) {
			e.printStackTrace();
		} catch (NotUniqueFieldException e) {
			e.printStackTrace();
			respEnt = ResponseEntity.ok(e.getMessage());
		}
		
		return respEnt;
	}
	
	@PostMapping("/users/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	ResponseEntity<String> deleteUser(@PathVariable Long id){
		ResponseEntity<String> respEnt = null;
		try {
			userService.deleteByID(id);
			respEnt = ResponseEntity.ok(messageSource.getMessage("data.user.deleted.success", null, LocaleContextHolder.getLocale()));
		} catch (NoSuchMessageException e) {
			e.printStackTrace();
		}
		
		return respEnt;
	}
	
}
