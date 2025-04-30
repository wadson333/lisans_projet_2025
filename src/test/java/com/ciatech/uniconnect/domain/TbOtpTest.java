package com.ciatech.uniconnect.domain;

import static com.ciatech.uniconnect.domain.TbOtpTestSamples.*;
import static com.ciatech.uniconnect.domain.TbUsersTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ciatech.uniconnect.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbOtpTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbOtp.class);
        TbOtp tbOtp1 = getTbOtpSample1();
        TbOtp tbOtp2 = new TbOtp();
        assertThat(tbOtp1).isNotEqualTo(tbOtp2);

        tbOtp2.setId(tbOtp1.getId());
        assertThat(tbOtp1).isEqualTo(tbOtp2);

        tbOtp2 = getTbOtpSample2();
        assertThat(tbOtp1).isNotEqualTo(tbOtp2);
    }

    @Test
    void userTest() {
        TbOtp tbOtp = getTbOtpRandomSampleGenerator();
        TbUsers tbUsersBack = getTbUsersRandomSampleGenerator();

        tbOtp.setUser(tbUsersBack);
        assertThat(tbOtp.getUser()).isEqualTo(tbUsersBack);

        tbOtp.user(null);
        assertThat(tbOtp.getUser()).isNull();
    }
}
