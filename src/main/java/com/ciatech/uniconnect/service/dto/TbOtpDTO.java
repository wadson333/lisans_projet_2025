package com.ciatech.uniconnect.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.ciatech.uniconnect.domain.TbOtp} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbOtpDTO implements Serializable {

    private Long id;

    @NotNull
    private String otpCode;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant expirationTime;

    private Integer attemptCount;

    private Integer maxAttempts;

    private TbUsersDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Instant expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Integer getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(Integer attemptCount) {
        this.attemptCount = attemptCount;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public TbUsersDTO getUser() {
        return user;
    }

    public void setUser(TbUsersDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbOtpDTO)) {
            return false;
        }

        TbOtpDTO tbOtpDTO = (TbOtpDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tbOtpDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbOtpDTO{" +
            "id=" + getId() +
            ", otpCode='" + getOtpCode() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", expirationTime='" + getExpirationTime() + "'" +
            ", attemptCount=" + getAttemptCount() +
            ", maxAttempts=" + getMaxAttempts() +
            ", user=" + getUser() +
            "}";
    }
}
