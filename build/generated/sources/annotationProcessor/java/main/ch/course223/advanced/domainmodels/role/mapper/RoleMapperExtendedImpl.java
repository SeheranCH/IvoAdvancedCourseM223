package ch.course223.advanced.domainmodels.role.mapper;

import ch.course223.advanced.domainmodels.authority.Authority;
import ch.course223.advanced.domainmodels.authority.AuthorityDTO;
import ch.course223.advanced.domainmodels.role.Role;
import ch.course223.advanced.domainmodels.role.RoleDTO;
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
public class RoleMapperExtendedImpl implements RoleMapperExtended {

    @Override
    public Role fromDTO(RoleDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( dto.getId() );
        role.setName( dto.getName() );
        role.setAuthorities( authorityDTOSetToAuthoritySet( dto.getAuthorities() ) );

        return role;
    }

    @Override
    public List<Role> fromDTOs(List<RoleDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Role> list = new ArrayList<Role>( dtos.size() );
        for ( RoleDTO roleDTO : dtos ) {
            list.add( fromDTO( roleDTO ) );
        }

        return list;
    }

    @Override
    public Set<Role> fromDTOs(Set<RoleDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        Set<Role> set = new HashSet<Role>( Math.max( (int) ( dtos.size() / .75f ) + 1, 16 ) );
        for ( RoleDTO roleDTO : dtos ) {
            set.add( fromDTO( roleDTO ) );
        }

        return set;
    }

    @Override
    public RoleDTO toDTO(Role dm) {
        if ( dm == null ) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setId( dm.getId() );
        roleDTO.setName( dm.getName() );
        roleDTO.setAuthorities( authoritySetToAuthorityDTOSet( dm.getAuthorities() ) );

        return roleDTO;
    }

    @Override
    public List<RoleDTO> toDTOs(List<Role> dms) {
        if ( dms == null ) {
            return null;
        }

        List<RoleDTO> list = new ArrayList<RoleDTO>( dms.size() );
        for ( Role role : dms ) {
            list.add( toDTO( role ) );
        }

        return list;
    }

    @Override
    public Set<RoleDTO> toDTOs(Set<Role> dms) {
        if ( dms == null ) {
            return null;
        }

        Set<RoleDTO> set = new HashSet<RoleDTO>( Math.max( (int) ( dms.size() / .75f ) + 1, 16 ) );
        for ( Role role : dms ) {
            set.add( toDTO( role ) );
        }

        return set;
    }

    protected Authority authorityDTOToAuthority(AuthorityDTO authorityDTO) {
        if ( authorityDTO == null ) {
            return null;
        }

        Authority authority = new Authority();

        authority.setId( authorityDTO.getId() );
        authority.setName( authorityDTO.getName() );

        return authority;
    }

    protected Set<Authority> authorityDTOSetToAuthoritySet(Set<AuthorityDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Authority> set1 = new HashSet<Authority>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( AuthorityDTO authorityDTO : set ) {
            set1.add( authorityDTOToAuthority( authorityDTO ) );
        }

        return set1;
    }

    protected AuthorityDTO authorityToAuthorityDTO(Authority authority) {
        if ( authority == null ) {
            return null;
        }

        AuthorityDTO authorityDTO = new AuthorityDTO();

        authorityDTO.setId( authority.getId() );
        authorityDTO.setName( authority.getName() );

        return authorityDTO;
    }

    protected Set<AuthorityDTO> authoritySetToAuthorityDTOSet(Set<Authority> set) {
        if ( set == null ) {
            return null;
        }

        Set<AuthorityDTO> set1 = new HashSet<AuthorityDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Authority authority : set ) {
            set1.add( authorityToAuthorityDTO( authority ) );
        }

        return set1;
    }
}
