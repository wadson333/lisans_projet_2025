package com.ciatech.uniconnect.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ciatech.uniconnect.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbLoginAttemptsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbLoginAttemptsDTO.class);
        TbLoginAttemptsDTO tbLoginAttemptsDTO1 = new TbLoginAttemptsDTO();
        tbLoginAttemptsDTO1.setId(1L);
        TbLoginAttemptsDTO tbLoginAttemptsDTO2 = new TbLoginAttemptsDTO();
        assertThat(tbLoginAttemptsDTO1).isNotEqualTo(tbLoginAttemptsDTO2);
        tbLoginAttemptsDTO2.setId(tbLoginAttemptsDTO1.getId());
        assertThat(tbLoginAttemptsDTO1).isEqualTo(tbLoginAttemptsDTO2);
        tbLoginAttemptsDTO2.setId(2L);
        assertThat(tbLoginAttemptsDTO1).isNotEqualTo(tbLoginAttemptsDTO2);
        tbLoginAttemptsDTO1.setId(null);
        assertThat(tbLoginAttemptsDTO1).isNotEqualTo(tbLoginAttemptsDTO2);
    }
}
