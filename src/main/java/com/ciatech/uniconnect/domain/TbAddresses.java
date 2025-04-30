package com.ciatech.uniconnect.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TbAddresses.
 */
@Entity
@Table(name = "tb_addresses")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbAddresses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "full_address", nullable = false)
    private String fullAddress;

    @Column(name = "code_section_communale")
    private Integer codeSectionCommunale;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TbAddresses id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullAddress() {
        return this.fullAddress;
    }

    public TbAddresses fullAddress(String fullAddress) {
        this.setFullAddress(fullAddress);
        return this;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public Integer getCodeSectionCommunale() {
        return this.codeSectionCommunale;
    }

    public TbAddresses codeSectionCommunale(Integer codeSectionCommunale) {
        this.setCodeSectionCommunale(codeSectionCommunale);
        return this;
    }

    public void setCodeSectionCommunale(Integer codeSectionCommunale) {
        this.codeSectionCommunale = codeSectionCommunale;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbAddresses)) {
            return false;
        }
        return getId() != null && getId().equals(((TbAddresses) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbAddresses{" +
            "id=" + getId() +
            ", fullAddress='" + getFullAddress() + "'" +
            ", codeSectionCommunale=" + getCodeSectionCommunale() +
            "}";
    }
}
