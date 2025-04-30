package com.ciatech.uniconnect.domain;

import static com.ciatech.uniconnect.domain.TbAddressesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ciatech.uniconnect.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbAddressesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbAddresses.class);
        TbAddresses tbAddresses1 = getTbAddressesSample1();
        TbAddresses tbAddresses2 = new TbAddresses();
        assertThat(tbAddresses1).isNotEqualTo(tbAddresses2);

        tbAddresses2.setId(tbAddresses1.getId());
        assertThat(tbAddresses1).isEqualTo(tbAddresses2);

        tbAddresses2 = getTbAddressesSample2();
        assertThat(tbAddresses1).isNotEqualTo(tbAddresses2);
    }
}
