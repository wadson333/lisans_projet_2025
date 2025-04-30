package com.ciatech.uniconnect.domain;

import com.ciatech.uniconnect.domain.enumeration.AccountStatus;
import com.ciatech.uniconnect.domain.enumeration.MaritalStatus;
import com.ciatech.uniconnect.domain.enumeration.Sex;
import com.ciatech.uniconnect.domain.enumeration.UserType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TbUsers.
 */
@Entity
@Table(name = "tb_users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "other_telephone")
    private String otherTelephone;

    @NotNull
    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "nif", unique = true)
    private String nif;

    @Column(name = "cin", unique = true)
    private String cin;

    @Column(name = "ninu", unique = true)
    private String ninu;

    @Column(name = "passport_number", unique = true)
    private String passportNumber;

    @Column(name = "licence_number", unique = true)
    private String licenceNumber;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "is_active", nullable = false)
    private AccountStatus isActive;

    @Column(name = "two_factor_enabled")
    private Boolean twoFactorEnabled;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    private TbAddresses tbAddresses;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_tb_users__roles",
        joinColumns = @JoinColumn(name = "tb_users_id"),
        inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "permissions", "users" }, allowSetters = true)
    private Set<TbRoles> roles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TbUsers id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public TbUsers firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public TbUsers lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public TbUsers telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }

    public TbUsers email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtherTelephone() {
        return this.otherTelephone;
    }

    public TbUsers otherTelephone(String otherTelephone) {
        this.setOtherTelephone(otherTelephone);
        return this;
    }

    public void setOtherTelephone(String otherTelephone) {
        this.otherTelephone = otherTelephone;
    }

    public LocalDate getDob() {
        return this.dob;
    }

    public TbUsers dob(LocalDate dob) {
        this.setDob(dob);
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getNif() {
        return this.nif;
    }

    public TbUsers nif(String nif) {
        this.setNif(nif);
        return this;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getCin() {
        return this.cin;
    }

    public TbUsers cin(String cin) {
        this.setCin(cin);
        return this;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNinu() {
        return this.ninu;
    }

    public TbUsers ninu(String ninu) {
        this.setNinu(ninu);
        return this;
    }

    public void setNinu(String ninu) {
        this.ninu = ninu;
    }

    public String getPassportNumber() {
        return this.passportNumber;
    }

    public TbUsers passportNumber(String passportNumber) {
        this.setPassportNumber(passportNumber);
        return this;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getLicenceNumber() {
        return this.licenceNumber;
    }

    public TbUsers licenceNumber(String licenceNumber) {
        this.setLicenceNumber(licenceNumber);
        return this;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public String getCode() {
        return this.code;
    }

    public TbUsers code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Sex getSex() {
        return this.sex;
    }

    public TbUsers sex(Sex sex) {
        this.setSex(sex);
        return this;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public MaritalStatus getMaritalStatus() {
        return this.maritalStatus;
    }

    public TbUsers maritalStatus(MaritalStatus maritalStatus) {
        this.setMaritalStatus(maritalStatus);
        return this;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public AccountStatus getIsActive() {
        return this.isActive;
    }

    public TbUsers isActive(AccountStatus isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(AccountStatus isActive) {
        this.isActive = isActive;
    }

    public Boolean getTwoFactorEnabled() {
        return this.twoFactorEnabled;
    }

    public TbUsers twoFactorEnabled(Boolean twoFactorEnabled) {
        this.setTwoFactorEnabled(twoFactorEnabled);
        return this;
    }

    public void setTwoFactorEnabled(Boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }

    public Integer getFailedLoginAttempts() {
        return this.failedLoginAttempts;
    }

    public TbUsers failedLoginAttempts(Integer failedLoginAttempts) {
        this.setFailedLoginAttempts(failedLoginAttempts);
        return this;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public Boolean getIsBlocked() {
        return this.isBlocked;
    }

    public TbUsers isBlocked(Boolean isBlocked) {
        this.setIsBlocked(isBlocked);
        return this;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public UserType getUserType() {
        return this.userType;
    }

    public TbUsers userType(UserType userType) {
        this.setUserType(userType);
        return this;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return this.password;
    }

    public TbUsers password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TbAddresses getTbAddresses() {
        return this.tbAddresses;
    }

    public void setTbAddresses(TbAddresses tbAddresses) {
        this.tbAddresses = tbAddresses;
    }

    public TbUsers tbAddresses(TbAddresses tbAddresses) {
        this.setTbAddresses(tbAddresses);
        return this;
    }

    public Set<TbRoles> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<TbRoles> tbRoles) {
        this.roles = tbRoles;
    }

    public TbUsers roles(Set<TbRoles> tbRoles) {
        this.setRoles(tbRoles);
        return this;
    }

    public TbUsers addRoles(TbRoles tbRoles) {
        this.roles.add(tbRoles);
        return this;
    }

    public TbUsers removeRoles(TbRoles tbRoles) {
        this.roles.remove(tbRoles);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbUsers)) {
            return false;
        }
        return getId() != null && getId().equals(((TbUsers) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbUsers{" +
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
            "}";
    }
}
