package com.ciatech.uniconnect.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ciatech.uniconnect.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbUsersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbUsersDTO.class);
        TbUsersDTO tbUsersDTO1 = new TbUsersDTO();
        tbUsersDTO1.setId(1L);
        TbUsersDTO tbUsersDTO2 = new TbUsersDTO();
        assertThat(tbUsersDTO1).isNotEqualTo(tbUsersDTO2);
        tbUsersDTO2.setId(tbUsersDTO1.getId());
        assertThat(tbUsersDTO1).isEqualTo(tbUsersDTO2);
        tbUsersDTO2.setId(2L);
        assertThat(tbUsersDTO1).isNotEqualTo(tbUsersDTO2);
        tbUsersDTO1.setId(null);
        assertThat(tbUsersDTO1).isNotEqualTo(tbUsersDTO2);
    }
}
