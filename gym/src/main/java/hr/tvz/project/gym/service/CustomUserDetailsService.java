package hr.tvz.project.gym.service;


import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hr.tvz.project.gym.model.User;
import hr.tvz.project.gym.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	User user = userRepository.findByUsernameAndDeletedFalse(username)
    				.orElseThrow(() ->
                         new UsernameNotFoundException("User not found with username: "+ username));

        Set<GrantedAuthority> authorities = user
							                .getRoles()
							                .stream()
							                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                authorities);
    }
	    
}
