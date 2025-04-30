package com.ciatech.uniconnect.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.ciatech.uniconnect.domain.TbPermissions} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbPermissionsDTO implements Serializable {

    private Long id;

    @NotNull
    private String action;

    private String description;

    private Set<TbRolesDTO> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TbRolesDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<TbRolesDTO> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbPermissionsDTO)) {
            return false;
        }

        TbPermissionsDTO tbPermissionsDTO = (TbPermissionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tbPermissionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbPermissionsDTO{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", description='" + getDescription() + "'" +
            ", roles=" + getRoles() +
            "}";
    }
}
