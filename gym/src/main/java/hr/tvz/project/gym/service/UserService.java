package hr.tvz.project.gym.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import hr.tvz.project.gym.exception.FieldNotFoundException;
import hr.tvz.project.gym.exception.NotUniqueFieldException;
import hr.tvz.project.gym.model.Role;
import hr.tvz.project.gym.model.User;
import hr.tvz.project.gym.repository.UserRepository;
import hr.tvz.project.gym.utils.GymConstants;
import jakarta.persistence.TypedQuery;

@Service
public class UserService {

	private UserRepository userRepository;
	private RoleService roleService;
	private MessageSource msgSrc;
	private SessionFactory sessionFactory;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepository userRepository, RoleService roleService, MessageSource msgSrc, SessionFactory sessionFactory) {
		this.userRepository = userRepository;
		this.roleService = roleService;
		this.msgSrc = msgSrc;
		this.sessionFactory = sessionFactory;
	}
	
	public User findByUsernameAndDeletedIsFalse(String username) {
		return userRepository.findByUsernameAndDeletedIsFalse(username);
	}

	public User getUserById(long userID) {
		return userRepository.getUserById(userID);
	}

	public List<User> find(User user) {    	
    	Session session = sessionFactory.openSession();
		TypedQuery<User> query = session.createQuery(
    	        """
    	        	SELECT u
    	        		FROM user u
    	        			JOIN FETCH u.user_role r
    	        	WHERE 
    	        		(NULL = :ID OR u.ID = :ID) 
	        			AND ('' LIKE :username OR u.username LIKE :username)
	        			AND ('' LIKE :firstName OR u.firstName LIKE :firstName)
	        			AND ('' LIKE :lastName OR u.firstName LIKE :lastName)
    	        	ORDER BY u.lastName, u.firstName
    	        """,
    	        User.class);

		query.setParameter("ID", user.getId() != null ? user.getId() : null);
		query.setParameter("username", user.getUsername() != null ? user.getUsername() : "");
		query.setParameter("firstName", user.getFirstname() != null ? user.getFirstname() : "");
		query.setParameter("lastName", user.getLastname() != null ? user.getLastname() : "");
		
    	List<User> users = query.getResultList();
    	session.close();
    	
		return users;
	}
	

	public User create(User user) throws NoSuchMessageException, NotUniqueFieldException, FieldNotFoundException {
		
		if(user.getFirstname() == null || user.getLastname() == null 
			|| user.getUsername() == null || user.getEmail() == null 
			|| user.getPassword() == null) {
			throw new FieldNotFoundException(msgSrc.getMessage("warning.data.user.not.found.mandatory", null, LocaleContextHolder.getLocale()));
		}
		
		if(userRepository.existsByUsernameAndDeletedFalse(user.getUsername())){
			throw new NotUniqueFieldException(msgSrc.getMessage("warning.data.user.not.unique.username", null, LocaleContextHolder.getLocale()));
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Role role = roleService.getRoleById(GymConstants.ROLE_USER_ID);
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		
		user = userRepository.save(user);
		
		return user;
	}
	
	public User update(User user) throws NoSuchMessageException, NotUniqueFieldException {
		if(userRepository.existsByUsernameAndIdNotAndDeletedFalse(user.getUsername(), user.getId())){
			throw new NotUniqueFieldException(msgSrc.getMessage("warning.data.user.not.unique.username", null, LocaleContextHolder.getLocale()));
		}
		
		boolean isEmptyPassword = user.getPassword() == null || user.getPassword().isBlank();
		if(isEmptyPassword) {
			setCurrentUserPassword(user);
		}else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		
		user = userRepository.save(user);
		
		return user;
	}

	private void setCurrentUserPassword(User user) {
		User userWithPassword = userRepository.getUserById(user.getId());
		user.setPassword(userWithPassword.getPassword());
	}

	public void deleteByID(Long ID) {
		User user = getUserById(ID);
		userRepository.delete(user);
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	public Role getUserRoleByUsername(String username) {
		User user = userRepository.findByUsernameAndDeletedIsFalse(username);
		for(Role r:user.getRoles()) {
			return r;
		}
		return null;
	}
}
