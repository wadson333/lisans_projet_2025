package com.ciatech.uniconnect.service.mapper;

import static com.ciatech.uniconnect.domain.TbOtpAsserts.*;
import static com.ciatech.uniconnect.domain.TbOtpTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TbOtpMapperTest {

    private TbOtpMapper tbOtpMapper;

    @BeforeEach
    void setUp() {
        tbOtpMapper = new TbOtpMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTbOtpSample1();
        var actual = tbOtpMapper.toEntity(tbOtpMapper.toDto(expected));
        assertTbOtpAllPropertiesEquals(expected, actual);
    }
}
