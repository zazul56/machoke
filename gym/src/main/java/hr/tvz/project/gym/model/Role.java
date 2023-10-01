package hr.tvz.project.gym.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name="role")
public class Role {

	@Id
	private Long id;
	
	private String name;
	
	public Role() {}
	
	public Role(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
