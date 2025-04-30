package com.ciatech.uniconnect.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TbPasswordHistory.
 */
@Entity
@Table(name = "tb_password_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbPasswordHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "password_salt")
    private String passwordSalt;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "is_current")
    private Boolean isCurrent;

    @Column(name = "change_reason")
    private String changeReason;

    @Column(name = "valid_to")
    private ZonedDateTime validTo;

    @Column(name = "can_reuse")
    private Boolean canReuse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tbAddresses", "roles" }, allowSetters = true)
    private TbUsers user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TbPasswordHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public TbPasswordHistory passwordHash(String passwordHash) {
        this.setPasswordHash(passwordHash);
        return this;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordSalt() {
        return this.passwordSalt;
    }

    public TbPasswordHistory passwordSalt(String passwordSalt) {
        this.setPasswordSalt(passwordSalt);
        return this;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public TbPasswordHistory createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsCurrent() {
        return this.isCurrent;
    }

    public TbPasswordHistory isCurrent(Boolean isCurrent) {
        this.setIsCurrent(isCurrent);
        return this;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getChangeReason() {
        return this.changeReason;
    }

    public TbPasswordHistory changeReason(String changeReason) {
        this.setChangeReason(changeReason);
        return this;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public ZonedDateTime getValidTo() {
        return this.validTo;
    }

    public TbPasswordHistory validTo(ZonedDateTime validTo) {
        this.setValidTo(validTo);
        return this;
    }

    public void setValidTo(ZonedDateTime validTo) {
        this.validTo = validTo;
    }

    public Boolean getCanReuse() {
        return this.canReuse;
    }

    public TbPasswordHistory canReuse(Boolean canReuse) {
        this.setCanReuse(canReuse);
        return this;
    }

    public void setCanReuse(Boolean canReuse) {
        this.canReuse = canReuse;
    }

    public TbUsers getUser() {
        return this.user;
    }

    public void setUser(TbUsers tbUsers) {
        this.user = tbUsers;
    }

    public TbPasswordHistory user(TbUsers tbUsers) {
        this.setUser(tbUsers);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbPasswordHistory)) {
            return false;
        }
        return getId() != null && getId().equals(((TbPasswordHistory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbPasswordHistory{" +
            "id=" + getId() +
            ", passwordHash='" + getPasswordHash() + "'" +
            ", passwordSalt='" + getPasswordSalt() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", isCurrent='" + getIsCurrent() + "'" +
            ", changeReason='" + getChangeReason() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", canReuse='" + getCanReuse() + "'" +
            "}";
    }
}
