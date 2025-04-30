package com.ciatech.uniconnect.domain;

import static com.ciatech.uniconnect.domain.TbPermissionsTestSamples.*;
import static com.ciatech.uniconnect.domain.TbRolesTestSamples.*;
import static com.ciatech.uniconnect.domain.TbUsersTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ciatech.uniconnect.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TbRolesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbRoles.class);
        TbRoles tbRoles1 = getTbRolesSample1();
        TbRoles tbRoles2 = new TbRoles();
        assertThat(tbRoles1).isNotEqualTo(tbRoles2);

        tbRoles2.setId(tbRoles1.getId());
        assertThat(tbRoles1).isEqualTo(tbRoles2);

        tbRoles2 = getTbRolesSample2();
        assertThat(tbRoles1).isNotEqualTo(tbRoles2);
    }

    @Test
    void permissionsTest() {
        TbRoles tbRoles = getTbRolesRandomSampleGenerator();
        TbPermissions tbPermissionsBack = getTbPermissionsRandomSampleGenerator();

        tbRoles.addPermissions(tbPermissionsBack);
        assertThat(tbRoles.getPermissions()).containsOnly(tbPermissionsBack);

        tbRoles.removePermissions(tbPermissionsBack);
        assertThat(tbRoles.getPermissions()).doesNotContain(tbPermissionsBack);

        tbRoles.permissions(new HashSet<>(Set.of(tbPermissionsBack)));
        assertThat(tbRoles.getPermissions()).containsOnly(tbPermissionsBack);

        tbRoles.setPermissions(new HashSet<>());
        assertThat(tbRoles.getPermissions()).doesNotContain(tbPermissionsBack);
    }

    @Test
    void usersTest() {
        TbRoles tbRoles = getTbRolesRandomSampleGenerator();
        TbUsers tbUsersBack = getTbUsersRandomSampleGenerator();

        tbRoles.addUsers(tbUsersBack);
        assertThat(tbRoles.getUsers()).containsOnly(tbUsersBack);
        assertThat(tbUsersBack.getRoles()).containsOnly(tbRoles);

        tbRoles.removeUsers(tbUsersBack);
        assertThat(tbRoles.getUsers()).doesNotContain(tbUsersBack);
        assertThat(tbUsersBack.getRoles()).doesNotContain(tbRoles);

        tbRoles.users(new HashSet<>(Set.of(tbUsersBack)));
        assertThat(tbRoles.getUsers()).containsOnly(tbUsersBack);
        assertThat(tbUsersBack.getRoles()).containsOnly(tbRoles);

        tbRoles.setUsers(new HashSet<>());
        assertThat(tbRoles.getUsers()).doesNotContain(tbUsersBack);
        assertThat(tbUsersBack.getRoles()).doesNotContain(tbRoles);
    }
}
