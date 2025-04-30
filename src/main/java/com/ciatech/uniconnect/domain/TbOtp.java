package com.ciatech.uniconnect.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TbOtp.
 */
@Entity
@Table(name = "tb_otp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbOtp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "otp_code", nullable = false)
    private String otpCode;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "expiration_time", nullable = false)
    private Instant expirationTime;

    @Column(name = "attempt_count")
    private Integer attemptCount;

    @Column(name = "max_attempts")
    private Integer maxAttempts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tbAddresses", "roles" }, allowSetters = true)
    private TbUsers user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TbOtp id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOtpCode() {
        return this.otpCode;
    }

    public TbOtp otpCode(String otpCode) {
        this.setOtpCode(otpCode);
        return this;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public TbOtp createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getExpirationTime() {
        return this.expirationTime;
    }

    public TbOtp expirationTime(Instant expirationTime) {
        this.setExpirationTime(expirationTime);
        return this;
    }

    public void setExpirationTime(Instant expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Integer getAttemptCount() {
        return this.attemptCount;
    }

    public TbOtp attemptCount(Integer attemptCount) {
        this.setAttemptCount(attemptCount);
        return this;
    }

    public void setAttemptCount(Integer attemptCount) {
        this.attemptCount = attemptCount;
    }

    public Integer getMaxAttempts() {
        return this.maxAttempts;
    }

    public TbOtp maxAttempts(Integer maxAttempts) {
        this.setMaxAttempts(maxAttempts);
        return this;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public TbUsers getUser() {
        return this.user;
    }

    public void setUser(TbUsers tbUsers) {
        this.user = tbUsers;
    }

    public TbOtp user(TbUsers tbUsers) {
        this.setUser(tbUsers);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbOtp)) {
            return false;
        }
        return getId() != null && getId().equals(((TbOtp) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbOtp{" +
            "id=" + getId() +
            ", otpCode='" + getOtpCode() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", expirationTime='" + getExpirationTime() + "'" +
            ", attemptCount=" + getAttemptCount() +
            ", maxAttempts=" + getMaxAttempts() +
            "}";
    }
}
