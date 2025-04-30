package com.ciatech.uniconnect.service.mapper;

import com.ciatech.uniconnect.domain.TbPasswordHistory;
import com.ciatech.uniconnect.domain.TbUsers;
import com.ciatech.uniconnect.service.dto.TbPasswordHistoryDTO;
import com.ciatech.uniconnect.service.dto.TbUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TbPasswordHistory} and its DTO {@link TbPasswordHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface TbPasswordHistoryMapper extends EntityMapper<TbPasswordHistoryDTO, TbPasswordHistory> {
    @Mapping(target = "user", source = "user", qualifiedByName = "tbUsersId")
    TbPasswordHistoryDTO toDto(TbPasswordHistory s);

    @Named("tbUsersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TbUsersDTO toDtoTbUsersId(TbUsers tbUsers);
}
