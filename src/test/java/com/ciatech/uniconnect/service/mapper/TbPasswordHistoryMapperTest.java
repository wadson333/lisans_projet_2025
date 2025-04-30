package com.ciatech.uniconnect.service.mapper;

import static com.ciatech.uniconnect.domain.TbPasswordHistoryAsserts.*;
import static com.ciatech.uniconnect.domain.TbPasswordHistoryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TbPasswordHistoryMapperTest {

    private TbPasswordHistoryMapper tbPasswordHistoryMapper;

    @BeforeEach
    void setUp() {
        tbPasswordHistoryMapper = new TbPasswordHistoryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTbPasswordHistorySample1();
        var actual = tbPasswordHistoryMapper.toEntity(tbPasswordHistoryMapper.toDto(expected));
        assertTbPasswordHistoryAllPropertiesEquals(expected, actual);
    }
}
