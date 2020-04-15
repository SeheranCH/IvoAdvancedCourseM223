package ch.course223.advanced.domainmodels.authority.mapper;

import ch.course223.advanced.core.ExtendedDTOMapper;
import ch.course223.advanced.domainmodels.authority.Authority;
import ch.course223.advanced.domainmodels.authority.AuthorityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel="spring", unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface AuthorityMapperExtended extends ExtendedDTOMapper<Authority, AuthorityDTO> {
	
}