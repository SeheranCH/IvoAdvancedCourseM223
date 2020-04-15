package ch.course223.advanced.domainmodels.role;

import ch.course223.advanced.core.ExtendedJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RoleRepository extends ExtendedJpaRepository<Role> {

}
