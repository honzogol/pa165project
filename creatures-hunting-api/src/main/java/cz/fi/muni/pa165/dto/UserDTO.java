package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.enums.UserRole;

/**
 * DTO class for User entity
 *
 * @author Balcirak Peter <peter.balcirak@gmail.com>
 */
public class UserDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private UserRole role;

	public Long getId() {

		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserDTO{" +
				"id=" + getId() +
				", firstName='" + getFirstName() + '\'' +
				", lastName='" + getLastName() + '\'' +
				", email='" + getEmail() + '\'' +
				", role=" + getRole() +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof UserDTO)) return false;

		UserDTO userDTO = (UserDTO) o;

		if (getFirstName() != null ? !getFirstName().equals(userDTO.getFirstName()) : userDTO.getFirstName() != null)
			return false;
		if (getLastName() != null ? !getLastName().equals(userDTO.getLastName()) : userDTO.getLastName() != null)
			return false;
		return getEmail() != null ? getEmail().equals(userDTO.getEmail()) : userDTO.getEmail() == null;
	}

	@Override
	public int hashCode() {
		int result = getFirstName() != null ? getFirstName().hashCode() : 0;
		result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
		result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
		return result;
	}

}
