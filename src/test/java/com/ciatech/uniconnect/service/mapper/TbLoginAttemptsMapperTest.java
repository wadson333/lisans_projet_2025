package com.ciatech.uniconnect.service.mapper;

import static com.ciatech.uniconnect.domain.TbLoginAttemptsAsserts.*;
import static com.ciatech.uniconnect.domain.TbLoginAttemptsTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TbLoginAttemptsMapperTest {

    private TbLoginAttemptsMapper tbLoginAttemptsMapper;

    @BeforeEach
    void setUp() {
        tbLoginAttemptsMapper = new TbLoginAttemptsMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTbLoginAttemptsSample1();
        var actual = tbLoginAttemptsMapper.toEntity(tbLoginAttemptsMapper.toDto(expected));
        assertTbLoginAttemptsAllPropertiesEquals(expected, actual);
    }
}
