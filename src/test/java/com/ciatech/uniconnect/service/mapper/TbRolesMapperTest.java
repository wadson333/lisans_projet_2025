package com.ciatech.uniconnect.service.mapper;

import static com.ciatech.uniconnect.domain.TbRolesAsserts.*;
import static com.ciatech.uniconnect.domain.TbRolesTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TbRolesMapperTest {

    private TbRolesMapper tbRolesMapper;

    @BeforeEach
    void setUp() {
        tbRolesMapper = new TbRolesMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTbRolesSample1();
        var actual = tbRolesMapper.toEntity(tbRolesMapper.toDto(expected));
        assertTbRolesAllPropertiesEquals(expected, actual);
    }
}
