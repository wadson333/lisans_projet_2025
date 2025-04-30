package com.ciatech.uniconnect.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ciatech.uniconnect.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbOtpDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbOtpDTO.class);
        TbOtpDTO tbOtpDTO1 = new TbOtpDTO();
        tbOtpDTO1.setId(1L);
        TbOtpDTO tbOtpDTO2 = new TbOtpDTO();
        assertThat(tbOtpDTO1).isNotEqualTo(tbOtpDTO2);
        tbOtpDTO2.setId(tbOtpDTO1.getId());
        assertThat(tbOtpDTO1).isEqualTo(tbOtpDTO2);
        tbOtpDTO2.setId(2L);
        assertThat(tbOtpDTO1).isNotEqualTo(tbOtpDTO2);
        tbOtpDTO1.setId(null);
        assertThat(tbOtpDTO1).isNotEqualTo(tbOtpDTO2);
    }
}
