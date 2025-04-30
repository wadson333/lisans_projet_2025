package com.ciatech.uniconnect.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TbRoles.
 */
@Entity
@Table(name = "tb_roles")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_system_role")
    private Boolean isSystemRole;

    @Column(name = "is_editable")
    private Boolean isEditable;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_tb_roles__permissions",
        joinColumns = @JoinColumn(name = "tb_roles_id"),
        inverseJoinColumns = @JoinColumn(name = "permissions_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "roles" }, allowSetters = true)
    private Set<TbPermissions> permissions = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tbAddresses", "roles" }, allowSetters = true)
    private Set<TbUsers> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TbRoles id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public TbRoles name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public TbRoles description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsSystemRole() {
        return this.isSystemRole;
    }

    public TbRoles isSystemRole(Boolean isSystemRole) {
        this.setIsSystemRole(isSystemRole);
        return this;
    }

    public void setIsSystemRole(Boolean isSystemRole) {
        this.isSystemRole = isSystemRole;
    }

    public Boolean getIsEditable() {
        return this.isEditable;
    }

    public TbRoles isEditable(Boolean isEditable) {
        this.setIsEditable(isEditable);
        return this;
    }

    public void setIsEditable(Boolean isEditable) {
        this.isEditable = isEditable;
    }

    public Set<TbPermissions> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(Set<TbPermissions> tbPermissions) {
        this.permissions = tbPermissions;
    }

    public TbRoles permissions(Set<TbPermissions> tbPermissions) {
        this.setPermissions(tbPermissions);
        return this;
    }

    public TbRoles addPermissions(TbPermissions tbPermissions) {
        this.permissions.add(tbPermissions);
        return this;
    }

    public TbRoles removePermissions(TbPermissions tbPermissions) {
        this.permissions.remove(tbPermissions);
        return this;
    }

    public Set<TbUsers> getUsers() {
        return this.users;
    }

    public void setUsers(Set<TbUsers> tbUsers) {
        if (this.users != null) {
            this.users.forEach(i -> i.removeRoles(this));
        }
        if (tbUsers != null) {
            tbUsers.forEach(i -> i.addRoles(this));
        }
        this.users = tbUsers;
    }

    public TbRoles users(Set<TbUsers> tbUsers) {
        this.setUsers(tbUsers);
        return this;
    }

    public TbRoles addUsers(TbUsers tbUsers) {
        this.users.add(tbUsers);
        tbUsers.getRoles().add(this);
        return this;
    }

    public TbRoles removeUsers(TbUsers tbUsers) {
        this.users.remove(tbUsers);
        tbUsers.getRoles().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbRoles)) {
            return false;
        }
        return getId() != null && getId().equals(((TbRoles) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbRoles{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", isSystemRole='" + getIsSystemRole() + "'" +
            ", isEditable='" + getIsEditable() + "'" +
            "}";
    }
}
