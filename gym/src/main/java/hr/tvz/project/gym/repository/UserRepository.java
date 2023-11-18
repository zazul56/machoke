package hr.tvz.project.gym.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.tvz.project.gym.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUsernameAndDeletedIsFalse(String username);

	public User getUserById(long userID);

	Optional<User> findByUsernameAndDeletedFalse(String username);

	public boolean existsByUsernameAndDeletedFalse(String username);
	
	public boolean existsByUsernameAndIdNotAndDeletedFalse(String username, Long id);
	
}
