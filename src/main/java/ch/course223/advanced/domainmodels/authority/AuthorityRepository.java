package ch.course223.advanced.domainmodels.authority;

import ch.course223.advanced.core.ExtendedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends ExtendedJpaRepository<Authority> {

}
