package com.ciatech.uniconnect.service.mapper;

import com.ciatech.uniconnect.domain.TbLoginAttempts;
import com.ciatech.uniconnect.domain.TbUsers;
import com.ciatech.uniconnect.service.dto.TbLoginAttemptsDTO;
import com.ciatech.uniconnect.service.dto.TbUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TbLoginAttempts} and its DTO {@link TbLoginAttemptsDTO}.
 */
@Mapper(componentModel = "spring")
public interface TbLoginAttemptsMapper extends EntityMapper<TbLoginAttemptsDTO, TbLoginAttempts> {
    @Mapping(target = "user", source = "user", qualifiedByName = "tbUsersId")
    TbLoginAttemptsDTO toDto(TbLoginAttempts s);

    @Named("tbUsersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TbUsersDTO toDtoTbUsersId(TbUsers tbUsers);
}
