package com.ciatech.uniconnect.service.mapper;

import static com.ciatech.uniconnect.domain.TbUsersAsserts.*;
import static com.ciatech.uniconnect.domain.TbUsersTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TbUsersMapperTest {

    private TbUsersMapper tbUsersMapper;

    @BeforeEach
    void setUp() {
        tbUsersMapper = new TbUsersMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTbUsersSample1();
        var actual = tbUsersMapper.toEntity(tbUsersMapper.toDto(expected));
        assertTbUsersAllPropertiesEquals(expected, actual);
    }
}
