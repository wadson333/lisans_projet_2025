package com.ciatech.uniconnect.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ciatech.uniconnect.domain.TbAddresses} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbAddressesDTO implements Serializable {

    private Long id;

    @NotNull
    private String fullAddress;

    private Integer codeSectionCommunale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public Integer getCodeSectionCommunale() {
        return codeSectionCommunale;
    }

    public void setCodeSectionCommunale(Integer codeSectionCommunale) {
        this.codeSectionCommunale = codeSectionCommunale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbAddressesDTO)) {
            return false;
        }

        TbAddressesDTO tbAddressesDTO = (TbAddressesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tbAddressesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbAddressesDTO{" +
            "id=" + getId() +
            ", fullAddress='" + getFullAddress() + "'" +
            ", codeSectionCommunale=" + getCodeSectionCommunale() +
            "}";
    }
}
