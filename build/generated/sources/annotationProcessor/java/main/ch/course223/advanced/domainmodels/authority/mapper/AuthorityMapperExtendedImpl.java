package ch.course223.advanced.domainmodels.authority.mapper;

import ch.course223.advanced.domainmodels.authority.Authority;
import ch.course223.advanced.domainmodels.authority.AuthorityDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-04-15T13:17:10+0200",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 11.0.1 (Oracle Corporation)"
)
@Component
public class AuthorityMapperExtendedImpl implements AuthorityMapperExtended {

    @Override
    public Authority fromDTO(AuthorityDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Authority authority = new Authority();

        authority.setId( dto.getId() );
        authority.setName( dto.getName() );

        return authority;
    }

    @Override
    public List<Authority> fromDTOs(List<AuthorityDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Authority> list = new ArrayList<Authority>( dtos.size() );
        for ( AuthorityDTO authorityDTO : dtos ) {
            list.add( fromDTO( authorityDTO ) );
        }

        return list;
    }

    @Override
    public Set<Authority> fromDTOs(Set<AuthorityDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        Set<Authority> set = new HashSet<Authority>( Math.max( (int) ( dtos.size() / .75f ) + 1, 16 ) );
        for ( AuthorityDTO authorityDTO : dtos ) {
            set.add( fromDTO( authorityDTO ) );
        }

        return set;
    }

    @Override
    public AuthorityDTO toDTO(Authority dm) {
        if ( dm == null ) {
            return null;
        }

        AuthorityDTO authorityDTO = new AuthorityDTO();

        authorityDTO.setId( dm.getId() );
        authorityDTO.setName( dm.getName() );

        return authorityDTO;
    }

    @Override
    public List<AuthorityDTO> toDTOs(List<Authority> dms) {
        if ( dms == null ) {
            return null;
        }

        List<AuthorityDTO> list = new ArrayList<AuthorityDTO>( dms.size() );
        for ( Authority authority : dms ) {
            list.add( toDTO( authority ) );
        }

        return list;
    }

    @Override
    public Set<AuthorityDTO> toDTOs(Set<Authority> dms) {
        if ( dms == null ) {
            return null;
        }

        Set<AuthorityDTO> set = new HashSet<AuthorityDTO>( Math.max( (int) ( dms.size() / .75f ) + 1, 16 ) );
        for ( Authority authority : dms ) {
            set.add( toDTO( authority ) );
        }

        return set;
    }
}
