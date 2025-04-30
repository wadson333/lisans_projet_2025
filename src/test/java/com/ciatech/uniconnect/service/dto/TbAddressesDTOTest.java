package com.ciatech.uniconnect.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ciatech.uniconnect.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbAddressesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbAddressesDTO.class);
        TbAddressesDTO tbAddressesDTO1 = new TbAddressesDTO();
        tbAddressesDTO1.setId(1L);
        TbAddressesDTO tbAddressesDTO2 = new TbAddressesDTO();
        assertThat(tbAddressesDTO1).isNotEqualTo(tbAddressesDTO2);
        tbAddressesDTO2.setId(tbAddressesDTO1.getId());
        assertThat(tbAddressesDTO1).isEqualTo(tbAddressesDTO2);
        tbAddressesDTO2.setId(2L);
        assertThat(tbAddressesDTO1).isNotEqualTo(tbAddressesDTO2);
        tbAddressesDTO1.setId(null);
        assertThat(tbAddressesDTO1).isNotEqualTo(tbAddressesDTO2);
    }
}
