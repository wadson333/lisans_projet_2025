package com.ciatech.uniconnect.service.mapper;

import com.ciatech.uniconnect.domain.TbAddresses;
import com.ciatech.uniconnect.service.dto.TbAddressesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TbAddresses} and its DTO {@link TbAddressesDTO}.
 */
@Mapper(componentModel = "spring")
public interface TbAddressesMapper extends EntityMapper<TbAddressesDTO, TbAddresses> {}
