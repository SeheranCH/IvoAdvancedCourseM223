package ch.course223.advanced.domainmodels.user;

import ch.course223.advanced.core.ExtendedEntity;
import ch.course223.advanced.domainmodels.role.Role;
import ch.course223.advanced.validation.notnull.NotNull;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends ExtendedEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "account_expiration_date")
    private LocalDate accountExpirationDate;

    @Column(name = "credentials_expiration_date")
    private LocalDate credentialsExpirationDate;

    @Column(name = "locked")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean locked;

    @Column(name = "enabled")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_role",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public User(String id) {
        super(id);
    }

    public User() {

    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public LocalDate getAccountExpirationDate() {
        return accountExpirationDate;
    }

    public User setAccountExpirationDate(LocalDate accountExpirationDate) {
        this.accountExpirationDate = accountExpirationDate;
        return this;
    }

    public LocalDate getCredentialsExpirationDate() {
        return credentialsExpirationDate;
    }

    public User setCredentialsExpirationDate(LocalDate credentialsExpirationDate) {
        this.credentialsExpirationDate = credentialsExpirationDate;
        return this;
    }

    public Boolean getLocked() {
        return locked;
    }

    public User setLocked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public User setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public User setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }
}
