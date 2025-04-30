package com.ciatech.uniconnect.domain;

import static com.ciatech.uniconnect.domain.TbLoginAttemptsTestSamples.*;
import static com.ciatech.uniconnect.domain.TbUsersTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ciatech.uniconnect.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbLoginAttemptsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbLoginAttempts.class);
        TbLoginAttempts tbLoginAttempts1 = getTbLoginAttemptsSample1();
        TbLoginAttempts tbLoginAttempts2 = new TbLoginAttempts();
        assertThat(tbLoginAttempts1).isNotEqualTo(tbLoginAttempts2);

        tbLoginAttempts2.setId(tbLoginAttempts1.getId());
        assertThat(tbLoginAttempts1).isEqualTo(tbLoginAttempts2);

        tbLoginAttempts2 = getTbLoginAttemptsSample2();
        assertThat(tbLoginAttempts1).isNotEqualTo(tbLoginAttempts2);
    }

    @Test
    void userTest() {
        TbLoginAttempts tbLoginAttempts = getTbLoginAttemptsRandomSampleGenerator();
        TbUsers tbUsersBack = getTbUsersRandomSampleGenerator();

        tbLoginAttempts.setUser(tbUsersBack);
        assertThat(tbLoginAttempts.getUser()).isEqualTo(tbUsersBack);

        tbLoginAttempts.user(null);
        assertThat(tbLoginAttempts.getUser()).isNull();
    }
}
