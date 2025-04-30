package com.ciatech.uniconnect.service.dto;

import com.ciatech.uniconnect.domain.enumeration.LoginAttemptStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.ciatech.uniconnect.domain.TbLoginAttempts} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbLoginAttemptsDTO implements Serializable {

    private Long id;

    private String ipAddress;

    private String userAgent;

    @NotNull
    private LoginAttemptStatus status;

    private ZonedDateTime attemptAt;

    private String deviceType;

    private TbUsersDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public LoginAttemptStatus getStatus() {
        return status;
    }

    public void setStatus(LoginAttemptStatus status) {
        this.status = status;
    }

    public ZonedDateTime getAttemptAt() {
        return attemptAt;
    }

    public void setAttemptAt(ZonedDateTime attemptAt) {
        this.attemptAt = attemptAt;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
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
        if (!(o instanceof TbLoginAttemptsDTO)) {
            return false;
        }

        TbLoginAttemptsDTO tbLoginAttemptsDTO = (TbLoginAttemptsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tbLoginAttemptsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbLoginAttemptsDTO{" +
            "id=" + getId() +
            ", ipAddress='" + getIpAddress() + "'" +
            ", userAgent='" + getUserAgent() + "'" +
            ", status='" + getStatus() + "'" +
            ", attemptAt='" + getAttemptAt() + "'" +
            ", deviceType='" + getDeviceType() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
