package com.ciatech.uniconnect.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ciatech.uniconnect.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbPermissionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbPermissionsDTO.class);
        TbPermissionsDTO tbPermissionsDTO1 = new TbPermissionsDTO();
        tbPermissionsDTO1.setId(1L);
        TbPermissionsDTO tbPermissionsDTO2 = new TbPermissionsDTO();
        assertThat(tbPermissionsDTO1).isNotEqualTo(tbPermissionsDTO2);
        tbPermissionsDTO2.setId(tbPermissionsDTO1.getId());
        assertThat(tbPermissionsDTO1).isEqualTo(tbPermissionsDTO2);
        tbPermissionsDTO2.setId(2L);
        assertThat(tbPermissionsDTO1).isNotEqualTo(tbPermissionsDTO2);
        tbPermissionsDTO1.setId(null);
        assertThat(tbPermissionsDTO1).isNotEqualTo(tbPermissionsDTO2);
    }
}
