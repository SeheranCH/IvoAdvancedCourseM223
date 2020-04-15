package ch.course223.advanced.domainmodels.role;

import ch.course223.advanced.core.ExtendedEntity;
import ch.course223.advanced.domainmodels.authority.Authority;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role extends ExtendedEntity {

  @Column(name = "name", nullable = false)
  private String name;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinTable(
          name = "role_authority",
          joinColumns = @JoinColumn(name = "role_id"),
          inverseJoinColumns = @JoinColumn(name = "authority_id")
  )
  private Set<Authority> authorities;

  public Role() {
    super();
  }

  public String getName() {
    return name;
  }

  public Role setName(String name) {
    this.name = name;
    return this;
  }

  public Set<Authority> getAuthorities() {
    return authorities;
  }

  public Role setAuthorities(Set<Authority> authorities) {
    this.authorities = authorities;
    return this;
  }
}
