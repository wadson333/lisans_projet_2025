package com.ciatech.uniconnect.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.ciatech.uniconnect.domain.TbRoles} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbRolesDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private Boolean isSystemRole;

    private Boolean isEditable;

    private Set<TbPermissionsDTO> permissions = new HashSet<>();

    private Set<TbUsersDTO> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsSystemRole() {
        return isSystemRole;
    }

    public void setIsSystemRole(Boolean isSystemRole) {
        this.isSystemRole = isSystemRole;
    }

    public Boolean getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(Boolean isEditable) {
        this.isEditable = isEditable;
    }

    public Set<TbPermissionsDTO> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<TbPermissionsDTO> permissions) {
        this.permissions = permissions;
    }

    public Set<TbUsersDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<TbUsersDTO> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbRolesDTO)) {
            return false;
        }

        TbRolesDTO tbRolesDTO = (TbRolesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tbRolesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbRolesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", isSystemRole='" + getIsSystemRole() + "'" +
            ", isEditable='" + getIsEditable() + "'" +
            ", permissions=" + getPermissions() +
            ", users=" + getUsers() +
            "}";
    }
}
