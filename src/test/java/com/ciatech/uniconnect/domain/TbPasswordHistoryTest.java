package com.ciatech.uniconnect.domain;

import static com.ciatech.uniconnect.domain.TbPasswordHistoryTestSamples.*;
import static com.ciatech.uniconnect.domain.TbUsersTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ciatech.uniconnect.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbPasswordHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbPasswordHistory.class);
        TbPasswordHistory tbPasswordHistory1 = getTbPasswordHistorySample1();
        TbPasswordHistory tbPasswordHistory2 = new TbPasswordHistory();
        assertThat(tbPasswordHistory1).isNotEqualTo(tbPasswordHistory2);

        tbPasswordHistory2.setId(tbPasswordHistory1.getId());
        assertThat(tbPasswordHistory1).isEqualTo(tbPasswordHistory2);

        tbPasswordHistory2 = getTbPasswordHistorySample2();
        assertThat(tbPasswordHistory1).isNotEqualTo(tbPasswordHistory2);
    }

    @Test
    void userTest() {
        TbPasswordHistory tbPasswordHistory = getTbPasswordHistoryRandomSampleGenerator();
        TbUsers tbUsersBack = getTbUsersRandomSampleGenerator();

        tbPasswordHistory.setUser(tbUsersBack);
        assertThat(tbPasswordHistory.getUser()).isEqualTo(tbUsersBack);

        tbPasswordHistory.user(null);
        assertThat(tbPasswordHistory.getUser()).isNull();
    }
}
