package com.ciatech.uniconnect.domain;

import static com.ciatech.uniconnect.domain.TbAddressesTestSamples.*;
import static com.ciatech.uniconnect.domain.TbRolesTestSamples.*;
import static com.ciatech.uniconnect.domain.TbUsersTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ciatech.uniconnect.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TbUsersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbUsers.class);
        TbUsers tbUsers1 = getTbUsersSample1();
        TbUsers tbUsers2 = new TbUsers();
        assertThat(tbUsers1).isNotEqualTo(tbUsers2);

        tbUsers2.setId(tbUsers1.getId());
        assertThat(tbUsers1).isEqualTo(tbUsers2);

        tbUsers2 = getTbUsersSample2();
        assertThat(tbUsers1).isNotEqualTo(tbUsers2);
    }

    @Test
    void tbAddressesTest() {
        TbUsers tbUsers = getTbUsersRandomSampleGenerator();
        TbAddresses tbAddressesBack = getTbAddressesRandomSampleGenerator();

        tbUsers.setTbAddresses(tbAddressesBack);
        assertThat(tbUsers.getTbAddresses()).isEqualTo(tbAddressesBack);

        tbUsers.tbAddresses(null);
        assertThat(tbUsers.getTbAddresses()).isNull();
    }

    @Test
    void rolesTest() {
        TbUsers tbUsers = getTbUsersRandomSampleGenerator();
        TbRoles tbRolesBack = getTbRolesRandomSampleGenerator();

        tbUsers.addRoles(tbRolesBack);
        assertThat(tbUsers.getRoles()).containsOnly(tbRolesBack);

        tbUsers.removeRoles(tbRolesBack);
        assertThat(tbUsers.getRoles()).doesNotContain(tbRolesBack);

        tbUsers.roles(new HashSet<>(Set.of(tbRolesBack)));
        assertThat(tbUsers.getRoles()).containsOnly(tbRolesBack);

        tbUsers.setRoles(new HashSet<>());
        assertThat(tbUsers.getRoles()).doesNotContain(tbRolesBack);
    }
}
