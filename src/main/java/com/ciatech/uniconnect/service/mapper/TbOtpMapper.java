package com.ciatech.uniconnect.service.mapper;

import com.ciatech.uniconnect.domain.TbOtp;
import com.ciatech.uniconnect.domain.TbUsers;
import com.ciatech.uniconnect.service.dto.TbOtpDTO;
import com.ciatech.uniconnect.service.dto.TbUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TbOtp} and its DTO {@link TbOtpDTO}.
 */
@Mapper(componentModel = "spring")
public interface TbOtpMapper extends EntityMapper<TbOtpDTO, TbOtp> {
    @Mapping(target = "user", source = "user", qualifiedByName = "tbUsersId")
    TbOtpDTO toDto(TbOtp s);

    @Named("tbUsersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TbUsersDTO toDtoTbUsersId(TbUsers tbUsers);
}
