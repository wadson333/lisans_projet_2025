package com.ciatech.uniconnect.domain;

import com.ciatech.uniconnect.domain.enumeration.LoginAttemptStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TbLoginAttempts.
 */
@Entity
@Table(name = "tb_login_attempts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbLoginAttempts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LoginAttemptStatus status;

    @Column(name = "attempt_at")
    private ZonedDateTime attemptAt;

    @Column(name = "device_type")
    private String deviceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tbAddresses", "roles" }, allowSetters = true)
    private TbUsers user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TbLoginAttempts id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public TbLoginAttempts ipAddress(String ipAddress) {
        this.setIpAddress(ipAddress);
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public TbLoginAttempts userAgent(String userAgent) {
        this.setUserAgent(userAgent);
        return this;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public LoginAttemptStatus getStatus() {
        return this.status;
    }

    public TbLoginAttempts status(LoginAttemptStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(LoginAttemptStatus status) {
        this.status = status;
    }

    public ZonedDateTime getAttemptAt() {
        return this.attemptAt;
    }

    public TbLoginAttempts attemptAt(ZonedDateTime attemptAt) {
        this.setAttemptAt(attemptAt);
        return this;
    }

    public void setAttemptAt(ZonedDateTime attemptAt) {
        this.attemptAt = attemptAt;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public TbLoginAttempts deviceType(String deviceType) {
        this.setDeviceType(deviceType);
        return this;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public TbUsers getUser() {
        return this.user;
    }

    public void setUser(TbUsers tbUsers) {
        this.user = tbUsers;
    }

    public TbLoginAttempts user(TbUsers tbUsers) {
        this.setUser(tbUsers);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbLoginAttempts)) {
            return false;
        }
        return getId() != null && getId().equals(((TbLoginAttempts) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbLoginAttempts{" +
            "id=" + getId() +
            ", ipAddress='" + getIpAddress() + "'" +
            ", userAgent='" + getUserAgent() + "'" +
            ", status='" + getStatus() + "'" +
            ", attemptAt='" + getAttemptAt() + "'" +
            ", deviceType='" + getDeviceType() + "'" +
            "}";
    }
}
