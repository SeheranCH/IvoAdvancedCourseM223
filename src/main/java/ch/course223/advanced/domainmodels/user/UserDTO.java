package ch.course223.advanced.domainmodels.user;

import ch.course223.advanced.core.ExtendedDTO;
import ch.course223.advanced.domainmodels.role.RoleDTO;

import javax.validation.constraints.Email;
import java.util.Set;

public class UserDTO extends ExtendedDTO {

    private String firstName;

    private String lastName;

    @Email
    private String email;

    private Set<RoleDTO> roles;

    public UserDTO() {
    }

    public UserDTO(String id) {
        super(id);
    }

    public UserDTO(String firstName, String lastName, String email, Set<RoleDTO> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public UserDTO setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
        return this;
    }
}
