package com.ciatech.uniconnect.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ciatech.uniconnect.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbRolesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbRolesDTO.class);
        TbRolesDTO tbRolesDTO1 = new TbRolesDTO();
        tbRolesDTO1.setId(1L);
        TbRolesDTO tbRolesDTO2 = new TbRolesDTO();
        assertThat(tbRolesDTO1).isNotEqualTo(tbRolesDTO2);
        tbRolesDTO2.setId(tbRolesDTO1.getId());
        assertThat(tbRolesDTO1).isEqualTo(tbRolesDTO2);
        tbRolesDTO2.setId(2L);
        assertThat(tbRolesDTO1).isNotEqualTo(tbRolesDTO2);
        tbRolesDTO1.setId(null);
        assertThat(tbRolesDTO1).isNotEqualTo(tbRolesDTO2);
    }
}
