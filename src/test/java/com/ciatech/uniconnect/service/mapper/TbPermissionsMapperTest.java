package com.ciatech.uniconnect.service.mapper;

import static com.ciatech.uniconnect.domain.TbPermissionsAsserts.*;
import static com.ciatech.uniconnect.domain.TbPermissionsTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TbPermissionsMapperTest {

    private TbPermissionsMapper tbPermissionsMapper;

    @BeforeEach
    void setUp() {
        tbPermissionsMapper = new TbPermissionsMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTbPermissionsSample1();
        var actual = tbPermissionsMapper.toEntity(tbPermissionsMapper.toDto(expected));
        assertTbPermissionsAllPropertiesEquals(expected, actual);
    }
}
