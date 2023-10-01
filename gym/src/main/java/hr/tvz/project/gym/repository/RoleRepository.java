package hr.tvz.project.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.tvz.project.gym.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	public Role getRoleById(long roleID);
}
