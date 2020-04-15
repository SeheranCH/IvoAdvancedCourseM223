package ch.course223.advanced.core;

import java.util.List;
import java.util.Set;

public interface ExtendedDTOMapper<DM extends ExtendedEntity, DTO extends ExtendedDTO> {

    DM fromDTO(DTO dto);

    List<DM> fromDTOs(List<DTO> dtos);

    Set<DM> fromDTOs(Set<DTO> dtos);

    DTO toDTO(DM dm);

    List<DTO> toDTOs(List<DM> dms);

    Set<DTO> toDTOs(Set<DM> dms);

}
