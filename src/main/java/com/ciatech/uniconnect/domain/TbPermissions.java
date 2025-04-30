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
 * A TbPermissions.
 */
@Entity
@Table(name = "tb_permissions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbPermissions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "permissions", "users" }, allowSetters = true)
    private Set<TbRoles> roles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TbPermissions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return this.action;
    }

    public TbPermissions action(String action) {
        this.setAction(action);
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return this.description;
    }

    public TbPermissions description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TbRoles> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<TbRoles> tbRoles) {
        if (this.roles != null) {
            this.roles.forEach(i -> i.removePermissions(this));
        }
        if (tbRoles != null) {
            tbRoles.forEach(i -> i.addPermissions(this));
        }
        this.roles = tbRoles;
    }

    public TbPermissions roles(Set<TbRoles> tbRoles) {
        this.setRoles(tbRoles);
        return this;
    }

    public TbPermissions addRoles(TbRoles tbRoles) {
        this.roles.add(tbRoles);
        tbRoles.getPermissions().add(this);
        return this;
    }

    public TbPermissions removeRoles(TbRoles tbRoles) {
        this.roles.remove(tbRoles);
        tbRoles.getPermissions().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbPermissions)) {
            return false;
        }
        return getId() != null && getId().equals(((TbPermissions) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbPermissions{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
