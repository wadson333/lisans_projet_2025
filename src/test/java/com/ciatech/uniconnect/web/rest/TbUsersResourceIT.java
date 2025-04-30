package com.ciatech.uniconnect.web.rest;

import static com.ciatech.uniconnect.domain.TbUsersAsserts.*;
import static com.ciatech.uniconnect.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ciatech.uniconnect.IntegrationTest;
import com.ciatech.uniconnect.domain.TbUsers;
import com.ciatech.uniconnect.domain.enumeration.AccountStatus;
import com.ciatech.uniconnect.domain.enumeration.MaritalStatus;
import com.ciatech.uniconnect.domain.enumeration.Sex;
import com.ciatech.uniconnect.domain.enumeration.UserType;
import com.ciatech.uniconnect.repository.TbUsersRepository;
import com.ciatech.uniconnect.service.TbUsersService;
import com.ciatech.uniconnect.service.dto.TbUsersDTO;
import com.ciatech.uniconnect.service.mapper.TbUsersMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TbUsersResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TbUsersResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_TELEPHONE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NIF = "AAAAAAAAAA";
    private static final String UPDATED_NIF = "BBBBBBBBBB";

    private static final String DEFAULT_CIN = "AAAAAAAAAA";
    private static final String UPDATED_CIN = "BBBBBBBBBB";

    private static final String DEFAULT_NINU = "AAAAAAAAAA";
    private static final String UPDATED_NINU = "BBBBBBBBBB";

    private static final String DEFAULT_PASSPORT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LICENCE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_LICENCE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Sex DEFAULT_SEX = Sex.M;
    private static final Sex UPDATED_SEX = Sex.F;

    private static final MaritalStatus DEFAULT_MARITAL_STATUS = MaritalStatus.MARIE;
    private static final MaritalStatus UPDATED_MARITAL_STATUS = MaritalStatus.CELIBATAIRE;

    private static final AccountStatus DEFAULT_IS_ACTIVE = AccountStatus.ACTIVE;
    private static final AccountStatus UPDATED_IS_ACTIVE = AccountStatus.BLOCKED;

    private static final Boolean DEFAULT_TWO_FACTOR_ENABLED = false;
    private static final Boolean UPDATED_TWO_FACTOR_ENABLED = true;

    private static final Integer DEFAULT_FAILED_LOGIN_ATTEMPTS = 1;
    private static final Integer UPDATED_FAILED_LOGIN_ATTEMPTS = 2;

    private static final Boolean DEFAULT_IS_BLOCKED = false;
    private static final Boolean UPDATED_IS_BLOCKED = true;

    private static final UserType DEFAULT_USER_TYPE = UserType.STUDENT;
    private static final UserType UPDATED_USER_TYPE = UserType.EMPLOYEE;

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tb-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TbUsersRepository tbUsersRepository;

    @Mock
    private TbUsersRepository tbUsersRepositoryMock;

    @Autowired
    private TbUsersMapper tbUsersMapper;

    @Mock
    private TbUsersService tbUsersServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTbUsersMockMvc;

    private TbUsers tbUsers;

    private TbUsers insertedTbUsers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbUsers createEntity() {
        return new TbUsers()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .telephone(DEFAULT_TELEPHONE)
            .email(DEFAULT_EMAIL)
            .otherTelephone(DEFAULT_OTHER_TELEPHONE)
            .dob(DEFAULT_DOB)
            .nif(DEFAULT_NIF)
            .cin(DEFAULT_CIN)
            .ninu(DEFAULT_NINU)
            .passportNumber(DEFAULT_PASSPORT_NUMBER)
            .licenceNumber(DEFAULT_LICENCE_NUMBER)
            .code(DEFAULT_CODE)
            .sex(DEFAULT_SEX)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .isActive(DEFAULT_IS_ACTIVE)
            .twoFactorEnabled(DEFAULT_TWO_FACTOR_ENABLED)
            .failedLoginAttempts(DEFAULT_FAILED_LOGIN_ATTEMPTS)
            .isBlocked(DEFAULT_IS_BLOCKED)
            .userType(DEFAULT_USER_TYPE)
            .password(DEFAULT_PASSWORD);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbUsers createUpdatedEntity() {
        return new TbUsers()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .otherTelephone(UPDATED_OTHER_TELEPHONE)
            .dob(UPDATED_DOB)
            .nif(UPDATED_NIF)
            .cin(UPDATED_CIN)
            .ninu(UPDATED_NINU)
            .passportNumber(UPDATED_PASSPORT_NUMBER)
            .licenceNumber(UPDATED_LICENCE_NUMBER)
            .code(UPDATED_CODE)
            .sex(UPDATED_SEX)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .isActive(UPDATED_IS_ACTIVE)
            .twoFactorEnabled(UPDATED_TWO_FACTOR_ENABLED)
            .failedLoginAttempts(UPDATED_FAILED_LOGIN_ATTEMPTS)
            .isBlocked(UPDATED_IS_BLOCKED)
            .userType(UPDATED_USER_TYPE)
            .password(UPDATED_PASSWORD);
    }

    @BeforeEach
    public void initTest() {
        tbUsers = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedTbUsers != null) {
            tbUsersRepository.delete(insertedTbUsers);
            insertedTbUsers = null;
        }
    }

    @Test
    @Transactional
    void createTbUsers() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TbUsers
        TbUsersDTO tbUsersDTO = tbUsersMapper.toDto(tbUsers);
        var returnedTbUsersDTO = om.readValue(
            restTbUsersMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbUsersDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TbUsersDTO.class
        );

        // Validate the TbUsers in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTbUsers = tbUsersMapper.toEntity(returnedTbUsersDTO);
        assertTbUsersUpdatableFieldsEquals(returnedTbUsers, getPersistedTbUsers(returnedTbUsers));

        insertedTbUsers = returnedTbUsers;
    }

    @Test
    @Transactional
    void createTbUsersWithExistingId() throws Exception {
        // Create the TbUsers with an existing ID
        tbUsers.setId(1L);
        TbUsersDTO tbUsersDTO = tbUsersMapper.toDto(tbUsers);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbUsersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TbUsers in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tbUsers.setFirstName(null);

        // Create the TbUsers, which fails.
        TbUsersDTO tbUsersDTO = tbUsersMapper.toDto(tbUsers);

        restTbUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbUsersDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tbUsers.setLastName(null);

        // Create the TbUsers, which fails.
        TbUsersDTO tbUsersDTO = tbUsersMapper.toDto(tbUsers);

        restTbUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbUsersDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelephoneIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tbUsers.setTelephone(null);

        // Create the TbUsers, which fails.
        TbUsersDTO tbUsersDTO = tbUsersMapper.toDto(tbUsers);

        restTbUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbUsersDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tbUsers.setEmail(null);

        // Create the TbUsers, which fails.
        TbUsersDTO tbUsersDTO = tbUsersMapper.toDto(tbUsers);

        restTbUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbUsersDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDobIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tbUsers.setDob(null);

        // Create the TbUsers, which fails.
        TbUsersDTO tbUsersDTO = tbUsersMapper.toDto(tbUsers);

        restTbUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbUsersDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tbUsers.setCode(null);

        // Create the TbUsers, which fails.
        TbUsersDTO tbUsersDTO = tbUsersMapper.toDto(tbUsers);

        restTbUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbUsersDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tbUsers.setSex(null);

        // Create the TbUsers, which fails.
        TbUsersDTO tbUsersDTO = tbUsersMapper.toDto(tbUsers);

        restTbUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbUsersDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActiveIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tbUsers.setIsActive(null);

        // Create the TbUsers, which fails.
        TbUsersDTO tbUsersDTO = tbUsersMapper.toDto(tbUsers);

        restTbUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbUsersDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUserTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tbUsers.setUserType(null);

        // Create the TbUsers, which fails.
        TbUsersDTO tbUsersDTO = tbUsersMapper.toDto(tbUsers);

        restTbUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbUsersDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tbUsers.setPassword(null);

        // Create the TbUsers, which fails.
        TbUsersDTO tbUsersDTO = tbUsersMapper.toDto(tbUsers);

        restTbUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbUsersDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTbUsers() throws Exception {
        // Initialize the database
        insertedTbUsers = tbUsersRepository.saveAndFlush(tbUsers);

        // Get all the tbUsersList
        restTbUsersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].otherTelephone").value(hasItem(DEFAULT_OTHER_TELEPHONE)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN)))
            .andExpect(jsonPath("$.[*].ninu").value(hasItem(DEFAULT_NINU)))
            .andExpect(jsonPath("$.[*].passportNumber").value(hasItem(DEFAULT_PASSPORT_NUMBER)))
            .andExpect(jsonPath("$.[*].licenceNumber").value(hasItem(DEFAULT_LICENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.toString())))
            .andExpect(jsonPath("$.[*].twoFactorEnabled").value(hasItem(DEFAULT_TWO_FACTOR_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].failedLoginAttempts").value(hasItem(DEFAULT_FAILED_LOGIN_ATTEMPTS)))
            .andExpect(jsonPath("$.[*].isBlocked").value(hasItem(DEFAULT_IS_BLOCKED.booleanValue())))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTbUsersWithEagerRelationshipsIsEnabled() throws Exception {
        when(tbUsersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTbUsersMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tbUsersServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTbUsersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tbUsersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTbUsersMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tbUsersRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTbUsers() throws Exception {
        // Initialize the database
        insertedTbUsers = tbUsersRepository.saveAndFlush(tbUsers);

        // Get the tbUsers
        restTbUsersMockMvc
            .perform(get(ENTITY_API_URL_ID, tbUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tbUsers.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.otherTelephone").value(DEFAULT_OTHER_TELEPHONE))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF))
            .andExpect(jsonPath("$.cin").value(DEFAULT_CIN))
            .andExpect(jsonPath("$.ninu").value(DEFAULT_NINU))
            .andExpect(jsonPath("$.passportNumber").value(DEFAULT_PASSPORT_NUMBER))
            .andExpect(jsonPath("$.licenceNumber").value(DEFAULT_LICENCE_NUMBER))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.toString()))
            .andExpect(jsonPath("$.twoFactorEnabled").value(DEFAULT_TWO_FACTOR_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.failedLoginAttempts").value(DEFAULT_FAILED_LOGIN_ATTEMPTS))
            .andExpect(jsonPath("$.isBlocked").value(DEFAULT_IS_BLOCKED.booleanValue()))
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD));
    }

    @Test
    @Transactional
    void getNonExistingTbUsers() throws Exception {
        // Get the tbUsers
        restTbUsersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTbUsers() throws Exception {
        // Initialize the database
        insertedTbUsers = tbUsersRepository.saveAndFlush(tbUsers);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbUsers
        TbUsers updatedTbUsers = tbUsersRepository.findById(tbUsers.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTbUsers are not directly saved in db
        em.detach(updatedTbUsers);
        updatedTbUsers
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .otherTelephone(UPDATED_OTHER_TELEPHONE)
            .dob(UPDATED_DOB)
            .nif(UPDATED_NIF)
            .cin(UPDATED_CIN)
            .ninu(UPDATED_NINU)
            .passportNumber(UPDATED_PASSPORT_NUMBER)
            .licenceNumber(UPDATED_LICENCE_NUMBER)
            .code(UPDATED_CODE)
            .sex(UPDATED_SEX)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .isActive(UPDATED_IS_ACTIVE)
            .twoFactorEnabled(UPDATED_TWO_FACTOR_ENABLED)
            .failedLoginAttempts(UPDATED_FAILED_LOGIN_ATTEMPTS)
            .isBlocked(UPDATED_IS_BLOCKED)
            .userType(UPDATED_USER_TYPE)
            .password(UPDATED_PASSWORD);
        TbUsersDTO tbUsersDTO = tbUsersMapper.toDto(updatedTbUsers);

        restTbUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbUsersDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbUsersDTO))
            )
            .andExpect(status().isOk());

        // Validate the TbUsers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTbUsersToMatchAllProperties(updatedTbUsers);
    }

    @Test
    @Transactional
    void putNonExistingTbUsers() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbUsers.setId(longCount.incrementAndGet());

        // Create the TbUsers
        TbUsersDTO tbUsersDTO = tbUsersMapper.toDto(tbUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbUsersDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbUsers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTbUsers() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbUsers.setId(longCount.incrementAndGet());

        // Create the TbUsers
        TbUsersDTO tbUsersDTO = tbUsersMapper.toDto(tbUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tbUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbUsers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTbUsers() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbUsers.setId(longCount.incrementAndGet());

        // Create the TbUsers
        TbUsersDTO tbUsersDTO = tbUsersMapper.toDto(tbUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbUsersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbUsersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbUsers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTbUsersWithPatch() throws Exception {
        // Initialize the database
        insertedTbUsers = tbUsersRepository.saveAndFlush(tbUsers);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbUsers using partial update
        TbUsers partialUpdatedTbUsers = new TbUsers();
        partialUpdatedTbUsers.setId(tbUsers.getId());

        partialUpdatedTbUsers
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .dob(UPDATED_DOB)
            .nif(UPDATED_NIF)
            .ninu(UPDATED_NINU)
            .code(UPDATED_CODE)
            .sex(UPDATED_SEX)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .isActive(UPDATED_IS_ACTIVE)
            .failedLoginAttempts(UPDATED_FAILED_LOGIN_ATTEMPTS)
            .isBlocked(UPDATED_IS_BLOCKED)
            .password(UPDATED_PASSWORD);

        restTbUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbUsers.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTbUsers))
            )
            .andExpect(status().isOk());

        // Validate the TbUsers in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTbUsersUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTbUsers, tbUsers), getPersistedTbUsers(tbUsers));
    }

    @Test
    @Transactional
    void fullUpdateTbUsersWithPatch() throws Exception {
        // Initialize the database
        insertedTbUsers = tbUsersRepository.saveAndFlush(tbUsers);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbUsers using partial update
        TbUsers partialUpdatedTbUsers = new TbUsers();
        partialUpdatedTbUsers.setId(tbUsers.getId());

        partialUpdatedTbUsers
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .otherTelephone(UPDATED_OTHER_TELEPHONE)
            .dob(UPDATED_DOB)
            .nif(UPDATED_NIF)
            .cin(UPDATED_CIN)
            .ninu(UPDATED_NINU)
            .passportNumber(UPDATED_PASSPORT_NUMBER)
            .licenceNumber(UPDATED_LICENCE_NUMBER)
            .code(UPDATED_CODE)
            .sex(UPDATED_SEX)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .isActive(UPDATED_IS_ACTIVE)
            .twoFactorEnabled(UPDATED_TWO_FACTOR_ENABLED)
            .failedLoginAttempts(UPDATED_FAILED_LOGIN_ATTEMPTS)
            .isBlocked(UPDATED_IS_BLOCKED)
            .userType(UPDATED_USER_TYPE)
            .password(UPDATED_PASSWORD);

        restTbUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbUsers.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTbUsers))
            )
            .andExpect(status().isOk());

        // Validate the TbUsers in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTbUsersUpdatableFieldsEquals(partialUpdatedTbUsers, getPersistedTbUsers(partialUpdatedTbUsers));
    }

    @Test
    @Transactional
    void patchNonExistingTbUsers() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbUsers.setId(longCount.incrementAndGet());

        // Create the TbUsers
        TbUsersDTO tbUsersDTO = tbUsersMapper.toDto(tbUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tbUsersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tbUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbUsers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTbUsers() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbUsers.setId(longCount.incrementAndGet());

        // Create the TbUsers
        TbUsersDTO tbUsersDTO = tbUsersMapper.toDto(tbUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tbUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbUsers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTbUsers() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbUsers.setId(longCount.incrementAndGet());

        // Create the TbUsers
        TbUsersDTO tbUsersDTO = tbUsersMapper.toDto(tbUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbUsersMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tbUsersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbUsers in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTbUsers() throws Exception {
        // Initialize the database
        insertedTbUsers = tbUsersRepository.saveAndFlush(tbUsers);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tbUsers
        restTbUsersMockMvc
            .perform(delete(ENTITY_API_URL_ID, tbUsers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tbUsersRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected TbUsers getPersistedTbUsers(TbUsers tbUsers) {
        return tbUsersRepository.findById(tbUsers.getId()).orElseThrow();
    }

    protected void assertPersistedTbUsersToMatchAllProperties(TbUsers expectedTbUsers) {
        assertTbUsersAllPropertiesEquals(expectedTbUsers, getPersistedTbUsers(expectedTbUsers));
    }

    protected void assertPersistedTbUsersToMatchUpdatableProperties(TbUsers expectedTbUsers) {
        assertTbUsersAllUpdatablePropertiesEquals(expectedTbUsers, getPersistedTbUsers(expectedTbUsers));
    }
}
