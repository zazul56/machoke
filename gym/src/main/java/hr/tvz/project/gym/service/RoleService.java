package hr.tvz.project.gym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.tvz.project.gym.model.Role;
import hr.tvz.project.gym.repository.RoleRepository;


@Service
public class RoleService {

	private RoleRepository roleRepository;
	
	
	@Autowired
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	public Role getRoleById(long roleID) {
		return roleRepository.getRoleById(roleID);
	}
	
}
