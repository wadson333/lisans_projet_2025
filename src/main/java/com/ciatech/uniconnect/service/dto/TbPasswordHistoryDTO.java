package com.ciatech.uniconnect.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.ciatech.uniconnect.domain.TbPasswordHistory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbPasswordHistoryDTO implements Serializable {

    private Long id;

    @NotNull
    private String passwordHash;

    private String passwordSalt;

    private ZonedDateTime createdAt;

    private Boolean isCurrent;

    private String changeReason;

    private ZonedDateTime validTo;

    private Boolean canReuse;

    private TbUsersDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public ZonedDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(ZonedDateTime validTo) {
        this.validTo = validTo;
    }

    public Boolean getCanReuse() {
        return canReuse;
    }

    public void setCanReuse(Boolean canReuse) {
        this.canReuse = canReuse;
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
        if (!(o instanceof TbPasswordHistoryDTO)) {
            return false;
        }

        TbPasswordHistoryDTO tbPasswordHistoryDTO = (TbPasswordHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tbPasswordHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbPasswordHistoryDTO{" +
            "id=" + getId() +
            ", passwordHash='" + getPasswordHash() + "'" +
            ", passwordSalt='" + getPasswordSalt() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", isCurrent='" + getIsCurrent() + "'" +
            ", changeReason='" + getChangeReason() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", canReuse='" + getCanReuse() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
