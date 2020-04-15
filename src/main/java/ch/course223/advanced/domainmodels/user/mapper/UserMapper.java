package ch.course223.advanced.domainmodels.user.mapper;

import ch.course223.advanced.core.ExtendedDTOMapper;
import ch.course223.advanced.domainmodels.user.User;
import ch.course223.advanced.domainmodels.user.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends ExtendedDTOMapper<User, UserDTO> {

}
