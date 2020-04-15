package ch.course223.advanced.domainmodels.role.mapper;

import ch.course223.advanced.core.ExtendedDTOMapper;
import ch.course223.advanced.domainmodels.role.Role;
import ch.course223.advanced.domainmodels.role.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel="spring", unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface RoleMapperExtended extends ExtendedDTOMapper<Role, RoleDTO> {
	
}
