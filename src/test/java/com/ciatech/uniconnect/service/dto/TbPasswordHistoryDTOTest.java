package com.ciatech.uniconnect.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ciatech.uniconnect.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbPasswordHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbPasswordHistoryDTO.class);
        TbPasswordHistoryDTO tbPasswordHistoryDTO1 = new TbPasswordHistoryDTO();
        tbPasswordHistoryDTO1.setId(1L);
        TbPasswordHistoryDTO tbPasswordHistoryDTO2 = new TbPasswordHistoryDTO();
        assertThat(tbPasswordHistoryDTO1).isNotEqualTo(tbPasswordHistoryDTO2);
        tbPasswordHistoryDTO2.setId(tbPasswordHistoryDTO1.getId());
        assertThat(tbPasswordHistoryDTO1).isEqualTo(tbPasswordHistoryDTO2);
        tbPasswordHistoryDTO2.setId(2L);
        assertThat(tbPasswordHistoryDTO1).isNotEqualTo(tbPasswordHistoryDTO2);
        tbPasswordHistoryDTO1.setId(null);
        assertThat(tbPasswordHistoryDTO1).isNotEqualTo(tbPasswordHistoryDTO2);
    }
}
