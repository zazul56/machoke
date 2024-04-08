package hr.tvz.project.gym.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.tvz.project.gym.dto.LoginDto;
import hr.tvz.project.gym.exception.FieldNotFoundException;
import hr.tvz.project.gym.exception.NotUniqueFieldException;
import hr.tvz.project.gym.model.User;
import hr.tvz.project.gym.service.UserService;
import hr.tvz.project.gym.utils.JwtUtils;

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
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto){
        String jwt = null;
		try {
	    	Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
	                loginDto.getUsername(), loginDto.getPassword()));

	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	        JwtUtils setup = new JwtUtils();
			jwt = setup.generateToken(userDetails);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

        return ResponseEntity.ok(new JwtResponse(jwt));
        
        //return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
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
    

    // A simple class to format the JWT response
    public static class JwtResponse {
        private String token;
        private String type = "Bearer";

        public JwtResponse(String token) {
            this.token = token;
        }

        // Getters and Setters
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
    
}
