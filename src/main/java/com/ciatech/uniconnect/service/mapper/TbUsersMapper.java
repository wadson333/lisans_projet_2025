package com.ciatech.uniconnect.service.mapper;

import com.ciatech.uniconnect.domain.TbAddresses;
import com.ciatech.uniconnect.domain.TbRoles;
import com.ciatech.uniconnect.domain.TbUsers;
import com.ciatech.uniconnect.service.dto.TbAddressesDTO;
import com.ciatech.uniconnect.service.dto.TbRolesDTO;
import com.ciatech.uniconnect.service.dto.TbUsersDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TbUsers} and its DTO {@link TbUsersDTO}.
 */
@Mapper(componentModel = "spring")
public interface TbUsersMapper extends EntityMapper<TbUsersDTO, TbUsers> {
    @Mapping(target = "tbAddresses", source = "tbAddresses", qualifiedByName = "tbAddressesId")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "tbRolesIdSet")
    TbUsersDTO toDto(TbUsers s);

    @Mapping(target = "removeRoles", ignore = true)
    TbUsers toEntity(TbUsersDTO tbUsersDTO);

    @Named("tbAddressesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TbAddressesDTO toDtoTbAddressesId(TbAddresses tbAddresses);

    @Named("tbRolesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TbRolesDTO toDtoTbRolesId(TbRoles tbRoles);

    @Named("tbRolesIdSet")
    default Set<TbRolesDTO> toDtoTbRolesIdSet(Set<TbRoles> tbRoles) {
        return tbRoles.stream().map(this::toDtoTbRolesId).collect(Collectors.toSet());
    }
}
