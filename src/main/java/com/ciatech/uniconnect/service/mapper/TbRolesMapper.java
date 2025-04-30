package com.ciatech.uniconnect.service.mapper;

import com.ciatech.uniconnect.domain.TbPermissions;
import com.ciatech.uniconnect.domain.TbRoles;
import com.ciatech.uniconnect.domain.TbUsers;
import com.ciatech.uniconnect.service.dto.TbPermissionsDTO;
import com.ciatech.uniconnect.service.dto.TbRolesDTO;
import com.ciatech.uniconnect.service.dto.TbUsersDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TbRoles} and its DTO {@link TbRolesDTO}.
 */
@Mapper(componentModel = "spring")
public interface TbRolesMapper extends EntityMapper<TbRolesDTO, TbRoles> {
    @Mapping(target = "permissions", source = "permissions", qualifiedByName = "tbPermissionsIdSet")
    @Mapping(target = "users", source = "users", qualifiedByName = "tbUsersIdSet")
    TbRolesDTO toDto(TbRoles s);

    @Mapping(target = "removePermissions", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "removeUsers", ignore = true)
    TbRoles toEntity(TbRolesDTO tbRolesDTO);

    @Named("tbPermissionsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TbPermissionsDTO toDtoTbPermissionsId(TbPermissions tbPermissions);

    @Named("tbPermissionsIdSet")
    default Set<TbPermissionsDTO> toDtoTbPermissionsIdSet(Set<TbPermissions> tbPermissions) {
        return tbPermissions.stream().map(this::toDtoTbPermissionsId).collect(Collectors.toSet());
    }

    @Named("tbUsersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TbUsersDTO toDtoTbUsersId(TbUsers tbUsers);

    @Named("tbUsersIdSet")
    default Set<TbUsersDTO> toDtoTbUsersIdSet(Set<TbUsers> tbUsers) {
        return tbUsers.stream().map(this::toDtoTbUsersId).collect(Collectors.toSet());
    }
}
