package hr.tvz.project.gym.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.tvz.project.gym.dto.LoginDto;
import hr.tvz.project.gym.exception.FieldNotFoundException;
import hr.tvz.project.gym.exception.NotUniqueFieldException;
import hr.tvz.project.gym.model.User;
import hr.tvz.project.gym.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
	private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
    	Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User newUser){
    	
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
