package com.ciatech.uniconnect.service.mapper;

import com.ciatech.uniconnect.domain.TbPermissions;
import com.ciatech.uniconnect.domain.TbRoles;
import com.ciatech.uniconnect.service.dto.TbPermissionsDTO;
import com.ciatech.uniconnect.service.dto.TbRolesDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TbPermissions} and its DTO {@link TbPermissionsDTO}.
 */
@Mapper(componentModel = "spring")
public interface TbPermissionsMapper extends EntityMapper<TbPermissionsDTO, TbPermissions> {
    @Mapping(target = "roles", source = "roles", qualifiedByName = "tbRolesIdSet")
    TbPermissionsDTO toDto(TbPermissions s);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "removeRoles", ignore = true)
    TbPermissions toEntity(TbPermissionsDTO tbPermissionsDTO);

    @Named("tbRolesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TbRolesDTO toDtoTbRolesId(TbRoles tbRoles);

    @Named("tbRolesIdSet")
    default Set<TbRolesDTO> toDtoTbRolesIdSet(Set<TbRoles> tbRoles) {
        return tbRoles.stream().map(this::toDtoTbRolesId).collect(Collectors.toSet());
    }
}
