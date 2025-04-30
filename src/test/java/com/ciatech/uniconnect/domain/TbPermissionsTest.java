package com.ciatech.uniconnect.domain;

import static com.ciatech.uniconnect.domain.TbPermissionsTestSamples.*;
import static com.ciatech.uniconnect.domain.TbRolesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ciatech.uniconnect.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TbPermissionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbPermissions.class);
        TbPermissions tbPermissions1 = getTbPermissionsSample1();
        TbPermissions tbPermissions2 = new TbPermissions();
        assertThat(tbPermissions1).isNotEqualTo(tbPermissions2);

        tbPermissions2.setId(tbPermissions1.getId());
        assertThat(tbPermissions1).isEqualTo(tbPermissions2);

        tbPermissions2 = getTbPermissionsSample2();
        assertThat(tbPermissions1).isNotEqualTo(tbPermissions2);
    }

    @Test
    void rolesTest() {
        TbPermissions tbPermissions = getTbPermissionsRandomSampleGenerator();
        TbRoles tbRolesBack = getTbRolesRandomSampleGenerator();

        tbPermissions.addRoles(tbRolesBack);
        assertThat(tbPermissions.getRoles()).containsOnly(tbRolesBack);
        assertThat(tbRolesBack.getPermissions()).containsOnly(tbPermissions);

        tbPermissions.removeRoles(tbRolesBack);
        assertThat(tbPermissions.getRoles()).doesNotContain(tbRolesBack);
        assertThat(tbRolesBack.getPermissions()).doesNotContain(tbPermissions);

        tbPermissions.roles(new HashSet<>(Set.of(tbRolesBack)));
        assertThat(tbPermissions.getRoles()).containsOnly(tbRolesBack);
        assertThat(tbRolesBack.getPermissions()).containsOnly(tbPermissions);

        tbPermissions.setRoles(new HashSet<>());
        assertThat(tbPermissions.getRoles()).doesNotContain(tbRolesBack);
        assertThat(tbRolesBack.getPermissions()).doesNotContain(tbPermissions);
    }
}
