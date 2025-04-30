package com.ciatech.uniconnect.web.rest;

import static com.ciatech.uniconnect.domain.TbLoginAttemptsAsserts.*;
import static com.ciatech.uniconnect.web.rest.TestUtil.createUpdateProxyForBean;
import static com.ciatech.uniconnect.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ciatech.uniconnect.IntegrationTest;
import com.ciatech.uniconnect.domain.TbLoginAttempts;
import com.ciatech.uniconnect.domain.enumeration.LoginAttemptStatus;
import com.ciatech.uniconnect.repository.TbLoginAttemptsRepository;
import com.ciatech.uniconnect.service.dto.TbLoginAttemptsDTO;
import com.ciatech.uniconnect.service.mapper.TbLoginAttemptsMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TbLoginAttemptsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TbLoginAttemptsResourceIT {

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_USER_AGENT = "AAAAAAAAAA";
    private static final String UPDATED_USER_AGENT = "BBBBBBBBBB";

    private static final LoginAttemptStatus DEFAULT_STATUS = LoginAttemptStatus.SUCCESS;
    private static final LoginAttemptStatus UPDATED_STATUS = LoginAttemptStatus.FAILED;

    private static final ZonedDateTime DEFAULT_ATTEMPT_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ATTEMPT_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DEVICE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tb-login-attempts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TbLoginAttemptsRepository tbLoginAttemptsRepository;

    @Autowired
    private TbLoginAttemptsMapper tbLoginAttemptsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTbLoginAttemptsMockMvc;

    private TbLoginAttempts tbLoginAttempts;

    private TbLoginAttempts insertedTbLoginAttempts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbLoginAttempts createEntity() {
        return new TbLoginAttempts()
            .ipAddress(DEFAULT_IP_ADDRESS)
            .userAgent(DEFAULT_USER_AGENT)
            .status(DEFAULT_STATUS)
            .attemptAt(DEFAULT_ATTEMPT_AT)
            .deviceType(DEFAULT_DEVICE_TYPE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbLoginAttempts createUpdatedEntity() {
        return new TbLoginAttempts()
            .ipAddress(UPDATED_IP_ADDRESS)
            .userAgent(UPDATED_USER_AGENT)
            .status(UPDATED_STATUS)
            .attemptAt(UPDATED_ATTEMPT_AT)
            .deviceType(UPDATED_DEVICE_TYPE);
    }

    @BeforeEach
    public void initTest() {
        tbLoginAttempts = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedTbLoginAttempts != null) {
            tbLoginAttemptsRepository.delete(insertedTbLoginAttempts);
            insertedTbLoginAttempts = null;
        }
    }

    @Test
    @Transactional
    void createTbLoginAttempts() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TbLoginAttempts
        TbLoginAttemptsDTO tbLoginAttemptsDTO = tbLoginAttemptsMapper.toDto(tbLoginAttempts);
        var returnedTbLoginAttemptsDTO = om.readValue(
            restTbLoginAttemptsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbLoginAttemptsDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TbLoginAttemptsDTO.class
        );

        // Validate the TbLoginAttempts in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTbLoginAttempts = tbLoginAttemptsMapper.toEntity(returnedTbLoginAttemptsDTO);
        assertTbLoginAttemptsUpdatableFieldsEquals(returnedTbLoginAttempts, getPersistedTbLoginAttempts(returnedTbLoginAttempts));

        insertedTbLoginAttempts = returnedTbLoginAttempts;
    }

    @Test
    @Transactional
    void createTbLoginAttemptsWithExistingId() throws Exception {
        // Create the TbLoginAttempts with an existing ID
        tbLoginAttempts.setId(1L);
        TbLoginAttemptsDTO tbLoginAttemptsDTO = tbLoginAttemptsMapper.toDto(tbLoginAttempts);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbLoginAttemptsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbLoginAttemptsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TbLoginAttempts in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tbLoginAttempts.setStatus(null);

        // Create the TbLoginAttempts, which fails.
        TbLoginAttemptsDTO tbLoginAttemptsDTO = tbLoginAttemptsMapper.toDto(tbLoginAttempts);

        restTbLoginAttemptsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbLoginAttemptsDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTbLoginAttempts() throws Exception {
        // Initialize the database
        insertedTbLoginAttempts = tbLoginAttemptsRepository.saveAndFlush(tbLoginAttempts);

        // Get all the tbLoginAttemptsList
        restTbLoginAttemptsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbLoginAttempts.getId().intValue())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].userAgent").value(hasItem(DEFAULT_USER_AGENT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].attemptAt").value(hasItem(sameInstant(DEFAULT_ATTEMPT_AT))))
            .andExpect(jsonPath("$.[*].deviceType").value(hasItem(DEFAULT_DEVICE_TYPE)));
    }

    @Test
    @Transactional
    void getTbLoginAttempts() throws Exception {
        // Initialize the database
        insertedTbLoginAttempts = tbLoginAttemptsRepository.saveAndFlush(tbLoginAttempts);

        // Get the tbLoginAttempts
        restTbLoginAttemptsMockMvc
            .perform(get(ENTITY_API_URL_ID, tbLoginAttempts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tbLoginAttempts.getId().intValue()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
            .andExpect(jsonPath("$.userAgent").value(DEFAULT_USER_AGENT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.attemptAt").value(sameInstant(DEFAULT_ATTEMPT_AT)))
            .andExpect(jsonPath("$.deviceType").value(DEFAULT_DEVICE_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingTbLoginAttempts() throws Exception {
        // Get the tbLoginAttempts
        restTbLoginAttemptsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTbLoginAttempts() throws Exception {
        // Initialize the database
        insertedTbLoginAttempts = tbLoginAttemptsRepository.saveAndFlush(tbLoginAttempts);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbLoginAttempts
        TbLoginAttempts updatedTbLoginAttempts = tbLoginAttemptsRepository.findById(tbLoginAttempts.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTbLoginAttempts are not directly saved in db
        em.detach(updatedTbLoginAttempts);
        updatedTbLoginAttempts
            .ipAddress(UPDATED_IP_ADDRESS)
            .userAgent(UPDATED_USER_AGENT)
            .status(UPDATED_STATUS)
            .attemptAt(UPDATED_ATTEMPT_AT)
            .deviceType(UPDATED_DEVICE_TYPE);
        TbLoginAttemptsDTO tbLoginAttemptsDTO = tbLoginAttemptsMapper.toDto(updatedTbLoginAttempts);

        restTbLoginAttemptsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbLoginAttemptsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tbLoginAttemptsDTO))
            )
            .andExpect(status().isOk());

        // Validate the TbLoginAttempts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTbLoginAttemptsToMatchAllProperties(updatedTbLoginAttempts);
    }

    @Test
    @Transactional
    void putNonExistingTbLoginAttempts() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbLoginAttempts.setId(longCount.incrementAndGet());

        // Create the TbLoginAttempts
        TbLoginAttemptsDTO tbLoginAttemptsDTO = tbLoginAttemptsMapper.toDto(tbLoginAttempts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbLoginAttemptsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbLoginAttemptsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tbLoginAttemptsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbLoginAttempts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTbLoginAttempts() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbLoginAttempts.setId(longCount.incrementAndGet());

        // Create the TbLoginAttempts
        TbLoginAttemptsDTO tbLoginAttemptsDTO = tbLoginAttemptsMapper.toDto(tbLoginAttempts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbLoginAttemptsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tbLoginAttemptsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbLoginAttempts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTbLoginAttempts() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbLoginAttempts.setId(longCount.incrementAndGet());

        // Create the TbLoginAttempts
        TbLoginAttemptsDTO tbLoginAttemptsDTO = tbLoginAttemptsMapper.toDto(tbLoginAttempts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbLoginAttemptsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbLoginAttemptsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbLoginAttempts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTbLoginAttemptsWithPatch() throws Exception {
        // Initialize the database
        insertedTbLoginAttempts = tbLoginAttemptsRepository.saveAndFlush(tbLoginAttempts);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbLoginAttempts using partial update
        TbLoginAttempts partialUpdatedTbLoginAttempts = new TbLoginAttempts();
        partialUpdatedTbLoginAttempts.setId(tbLoginAttempts.getId());

        partialUpdatedTbLoginAttempts.status(UPDATED_STATUS).attemptAt(UPDATED_ATTEMPT_AT).deviceType(UPDATED_DEVICE_TYPE);

        restTbLoginAttemptsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbLoginAttempts.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTbLoginAttempts))
            )
            .andExpect(status().isOk());

        // Validate the TbLoginAttempts in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTbLoginAttemptsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTbLoginAttempts, tbLoginAttempts),
            getPersistedTbLoginAttempts(tbLoginAttempts)
        );
    }

    @Test
    @Transactional
    void fullUpdateTbLoginAttemptsWithPatch() throws Exception {
        // Initialize the database
        insertedTbLoginAttempts = tbLoginAttemptsRepository.saveAndFlush(tbLoginAttempts);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbLoginAttempts using partial update
        TbLoginAttempts partialUpdatedTbLoginAttempts = new TbLoginAttempts();
        partialUpdatedTbLoginAttempts.setId(tbLoginAttempts.getId());

        partialUpdatedTbLoginAttempts
            .ipAddress(UPDATED_IP_ADDRESS)
            .userAgent(UPDATED_USER_AGENT)
            .status(UPDATED_STATUS)
            .attemptAt(UPDATED_ATTEMPT_AT)
            .deviceType(UPDATED_DEVICE_TYPE);

        restTbLoginAttemptsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbLoginAttempts.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTbLoginAttempts))
            )
            .andExpect(status().isOk());

        // Validate the TbLoginAttempts in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTbLoginAttemptsUpdatableFieldsEquals(
            partialUpdatedTbLoginAttempts,
            getPersistedTbLoginAttempts(partialUpdatedTbLoginAttempts)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTbLoginAttempts() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbLoginAttempts.setId(longCount.incrementAndGet());

        // Create the TbLoginAttempts
        TbLoginAttemptsDTO tbLoginAttemptsDTO = tbLoginAttemptsMapper.toDto(tbLoginAttempts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbLoginAttemptsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tbLoginAttemptsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tbLoginAttemptsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbLoginAttempts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTbLoginAttempts() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbLoginAttempts.setId(longCount.incrementAndGet());

        // Create the TbLoginAttempts
        TbLoginAttemptsDTO tbLoginAttemptsDTO = tbLoginAttemptsMapper.toDto(tbLoginAttempts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbLoginAttemptsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tbLoginAttemptsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbLoginAttempts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTbLoginAttempts() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbLoginAttempts.setId(longCount.incrementAndGet());

        // Create the TbLoginAttempts
        TbLoginAttemptsDTO tbLoginAttemptsDTO = tbLoginAttemptsMapper.toDto(tbLoginAttempts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbLoginAttemptsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tbLoginAttemptsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbLoginAttempts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTbLoginAttempts() throws Exception {
        // Initialize the database
        insertedTbLoginAttempts = tbLoginAttemptsRepository.saveAndFlush(tbLoginAttempts);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tbLoginAttempts
        restTbLoginAttemptsMockMvc
            .perform(delete(ENTITY_API_URL_ID, tbLoginAttempts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tbLoginAttemptsRepository.count();
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

    protected TbLoginAttempts getPersistedTbLoginAttempts(TbLoginAttempts tbLoginAttempts) {
        return tbLoginAttemptsRepository.findById(tbLoginAttempts.getId()).orElseThrow();
    }

    protected void assertPersistedTbLoginAttemptsToMatchAllProperties(TbLoginAttempts expectedTbLoginAttempts) {
        assertTbLoginAttemptsAllPropertiesEquals(expectedTbLoginAttempts, getPersistedTbLoginAttempts(expectedTbLoginAttempts));
    }

    protected void assertPersistedTbLoginAttemptsToMatchUpdatableProperties(TbLoginAttempts expectedTbLoginAttempts) {
        assertTbLoginAttemptsAllUpdatablePropertiesEquals(expectedTbLoginAttempts, getPersistedTbLoginAttempts(expectedTbLoginAttempts));
    }
}
