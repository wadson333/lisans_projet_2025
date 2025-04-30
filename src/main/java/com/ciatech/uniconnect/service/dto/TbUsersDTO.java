package com.ciatech.uniconnect.service.dto;

import com.ciatech.uniconnect.domain.enumeration.AccountStatus;
import com.ciatech.uniconnect.domain.enumeration.MaritalStatus;
import com.ciatech.uniconnect.domain.enumeration.Sex;
import com.ciatech.uniconnect.domain.enumeration.UserType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.ciatech.uniconnect.domain.TbUsers} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbUsersDTO implements Serializable {

    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String telephone;

    @NotNull
    private String email;

    private String otherTelephone;

    @NotNull
    private LocalDate dob;

    private String nif;

    private String cin;

    private String ninu;

    private String passportNumber;

    private String licenceNumber;

    @NotNull
    private String code;

    @NotNull
    private Sex sex;

    private MaritalStatus maritalStatus;

    @NotNull
    private AccountStatus isActive;

    private Boolean twoFactorEnabled;

    private Integer failedLoginAttempts;

    private Boolean isBlocked;

    @NotNull
    private UserType userType;

    @NotNull
    private String password;

    private TbAddressesDTO tbAddresses;

    private Set<TbRolesDTO> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtherTelephone() {
        return otherTelephone;
    }

    public void setOtherTelephone(String otherTelephone) {
        this.otherTelephone = otherTelephone;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNinu() {
        return ninu;
    }

    public void setNinu(String ninu) {
        this.ninu = ninu;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public AccountStatus getIsActive() {
        return isActive;
    }

    public void setIsActive(AccountStatus isActive) {
        this.isActive = isActive;
    }

    public Boolean getTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public void setTwoFactorEnabled(Boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TbAddressesDTO getTbAddresses() {
        return tbAddresses;
    }

    public void setTbAddresses(TbAddressesDTO tbAddresses) {
        this.tbAddresses = tbAddresses;
    }

    public Set<TbRolesDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<TbRolesDTO> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbUsersDTO)) {
            return false;
        }

        TbUsersDTO tbUsersDTO = (TbUsersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tbUsersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbUsersDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            ", otherTelephone='" + getOtherTelephone() + "'" +
            ", dob='" + getDob() + "'" +
            ", nif='" + getNif() + "'" +
            ", cin='" + getCin() + "'" +
            ", ninu='" + getNinu() + "'" +
            ", passportNumber='" + getPassportNumber() + "'" +
            ", licenceNumber='" + getLicenceNumber() + "'" +
            ", code='" + getCode() + "'" +
            ", sex='" + getSex() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", twoFactorEnabled='" + getTwoFactorEnabled() + "'" +
            ", failedLoginAttempts=" + getFailedLoginAttempts() +
            ", isBlocked='" + getIsBlocked() + "'" +
            ", userType='" + getUserType() + "'" +
            ", password='" + getPassword() + "'" +
            ", tbAddresses=" + getTbAddresses() +
            ", roles=" + getRoles() +
            "}";
    }
}
