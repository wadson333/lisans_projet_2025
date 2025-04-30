package com.ciatech.uniconnect.service.mapper;

import static com.ciatech.uniconnect.domain.TbAddressesAsserts.*;
import static com.ciatech.uniconnect.domain.TbAddressesTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TbAddressesMapperTest {

    private TbAddressesMapper tbAddressesMapper;

    @BeforeEach
    void setUp() {
        tbAddressesMapper = new TbAddressesMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTbAddressesSample1();
        var actual = tbAddressesMapper.toEntity(tbAddressesMapper.toDto(expected));
        assertTbAddressesAllPropertiesEquals(expected, actual);
    }
}
