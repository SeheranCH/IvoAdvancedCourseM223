package ch.course223.advanced.domainmodels.user;

import ch.course223.advanced.core.ExtendedJpaRepository;
import ch.course223.advanced.core.ExtendedServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl extends ExtendedServiceImpl<User> implements UserService{

    public UserServiceImpl(ExtendedJpaRepository<User> repository) {
        super(repository);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            return new UserDetailsImpl(findByEmail(email));
        } catch (NoSuchElementException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    @Override
    public User findByEmail(String email) {
        return findOrThrow(((UserRepository)repository).findByEmail(email));
    }
}
