package ch.course223.advanced.domainmodels.user;

import ch.course223.advanced.core.ExtendedService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService, ExtendedService<User> {
    User findByEmail(String email);
}
