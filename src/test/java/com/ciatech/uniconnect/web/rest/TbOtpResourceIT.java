package com.ciatech.uniconnect.web.rest;

import static com.ciatech.uniconnect.domain.TbOtpAsserts.*;
import static com.ciatech.uniconnect.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ciatech.uniconnect.IntegrationTest;
import com.ciatech.uniconnect.domain.TbOtp;
import com.ciatech.uniconnect.repository.TbOtpRepository;
import com.ciatech.uniconnect.service.dto.TbOtpDTO;
import com.ciatech.uniconnect.service.mapper.TbOtpMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link TbOtpResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TbOtpResourceIT {

    private static final String DEFAULT_OTP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_OTP_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPIRATION_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRATION_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ATTEMPT_COUNT = 1;
    private static final Integer UPDATED_ATTEMPT_COUNT = 2;

    private static final Integer DEFAULT_MAX_ATTEMPTS = 1;
    private static final Integer UPDATED_MAX_ATTEMPTS = 2;

    private static final String ENTITY_API_URL = "/api/tb-otps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TbOtpRepository tbOtpRepository;

    @Autowired
    private TbOtpMapper tbOtpMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTbOtpMockMvc;

    private TbOtp tbOtp;

    private TbOtp insertedTbOtp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbOtp createEntity() {
        return new TbOtp()
            .otpCode(DEFAULT_OTP_CODE)
            .createdAt(DEFAULT_CREATED_AT)
            .expirationTime(DEFAULT_EXPIRATION_TIME)
            .attemptCount(DEFAULT_ATTEMPT_COUNT)
            .maxAttempts(DEFAULT_MAX_ATTEMPTS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbOtp createUpdatedEntity() {
        return new TbOtp()
            .otpCode(UPDATED_OTP_CODE)
            .createdAt(UPDATED_CREATED_AT)
            .expirationTime(UPDATED_EXPIRATION_TIME)
            .attemptCount(UPDATED_ATTEMPT_COUNT)
            .maxAttempts(UPDATED_MAX_ATTEMPTS);
    }

    @BeforeEach
    public void initTest() {
        tbOtp = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedTbOtp != null) {
            tbOtpRepository.delete(insertedTbOtp);
            insertedTbOtp = null;
        }
    }

    @Test
    @Transactional
    void createTbOtp() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TbOtp
        TbOtpDTO tbOtpDTO = tbOtpMapper.toDto(tbOtp);
        var returnedTbOtpDTO = om.readValue(
            restTbOtpMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbOtpDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TbOtpDTO.class
        );

        // Validate the TbOtp in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTbOtp = tbOtpMapper.toEntity(returnedTbOtpDTO);
        assertTbOtpUpdatableFieldsEquals(returnedTbOtp, getPersistedTbOtp(returnedTbOtp));

        insertedTbOtp = returnedTbOtp;
    }

    @Test
    @Transactional
    void createTbOtpWithExistingId() throws Exception {
        // Create the TbOtp with an existing ID
        tbOtp.setId(1L);
        TbOtpDTO tbOtpDTO = tbOtpMapper.toDto(tbOtp);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbOtpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbOtpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TbOtp in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOtpCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tbOtp.setOtpCode(null);

        // Create the TbOtp, which fails.
        TbOtpDTO tbOtpDTO = tbOtpMapper.toDto(tbOtp);

        restTbOtpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbOtpDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tbOtp.setCreatedAt(null);

        // Create the TbOtp, which fails.
        TbOtpDTO tbOtpDTO = tbOtpMapper.toDto(tbOtp);

        restTbOtpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbOtpDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExpirationTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tbOtp.setExpirationTime(null);

        // Create the TbOtp, which fails.
        TbOtpDTO tbOtpDTO = tbOtpMapper.toDto(tbOtp);

        restTbOtpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbOtpDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTbOtps() throws Exception {
        // Initialize the database
        insertedTbOtp = tbOtpRepository.saveAndFlush(tbOtp);

        // Get all the tbOtpList
        restTbOtpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbOtp.getId().intValue())))
            .andExpect(jsonPath("$.[*].otpCode").value(hasItem(DEFAULT_OTP_CODE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].expirationTime").value(hasItem(DEFAULT_EXPIRATION_TIME.toString())))
            .andExpect(jsonPath("$.[*].attemptCount").value(hasItem(DEFAULT_ATTEMPT_COUNT)))
            .andExpect(jsonPath("$.[*].maxAttempts").value(hasItem(DEFAULT_MAX_ATTEMPTS)));
    }

    @Test
    @Transactional
    void getTbOtp() throws Exception {
        // Initialize the database
        insertedTbOtp = tbOtpRepository.saveAndFlush(tbOtp);

        // Get the tbOtp
        restTbOtpMockMvc
            .perform(get(ENTITY_API_URL_ID, tbOtp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tbOtp.getId().intValue()))
            .andExpect(jsonPath("$.otpCode").value(DEFAULT_OTP_CODE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.expirationTime").value(DEFAULT_EXPIRATION_TIME.toString()))
            .andExpect(jsonPath("$.attemptCount").value(DEFAULT_ATTEMPT_COUNT))
            .andExpect(jsonPath("$.maxAttempts").value(DEFAULT_MAX_ATTEMPTS));
    }

    @Test
    @Transactional
    void getNonExistingTbOtp() throws Exception {
        // Get the tbOtp
        restTbOtpMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTbOtp() throws Exception {
        // Initialize the database
        insertedTbOtp = tbOtpRepository.saveAndFlush(tbOtp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbOtp
        TbOtp updatedTbOtp = tbOtpRepository.findById(tbOtp.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTbOtp are not directly saved in db
        em.detach(updatedTbOtp);
        updatedTbOtp
            .otpCode(UPDATED_OTP_CODE)
            .createdAt(UPDATED_CREATED_AT)
            .expirationTime(UPDATED_EXPIRATION_TIME)
            .attemptCount(UPDATED_ATTEMPT_COUNT)
            .maxAttempts(UPDATED_MAX_ATTEMPTS);
        TbOtpDTO tbOtpDTO = tbOtpMapper.toDto(updatedTbOtp);

        restTbOtpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbOtpDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbOtpDTO))
            )
            .andExpect(status().isOk());

        // Validate the TbOtp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTbOtpToMatchAllProperties(updatedTbOtp);
    }

    @Test
    @Transactional
    void putNonExistingTbOtp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbOtp.setId(longCount.incrementAndGet());

        // Create the TbOtp
        TbOtpDTO tbOtpDTO = tbOtpMapper.toDto(tbOtp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbOtpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbOtpDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbOtpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbOtp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTbOtp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbOtp.setId(longCount.incrementAndGet());

        // Create the TbOtp
        TbOtpDTO tbOtpDTO = tbOtpMapper.toDto(tbOtp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbOtpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tbOtpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbOtp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTbOtp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbOtp.setId(longCount.incrementAndGet());

        // Create the TbOtp
        TbOtpDTO tbOtpDTO = tbOtpMapper.toDto(tbOtp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbOtpMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbOtpDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbOtp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTbOtpWithPatch() throws Exception {
        // Initialize the database
        insertedTbOtp = tbOtpRepository.saveAndFlush(tbOtp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbOtp using partial update
        TbOtp partialUpdatedTbOtp = new TbOtp();
        partialUpdatedTbOtp.setId(tbOtp.getId());

        partialUpdatedTbOtp.otpCode(UPDATED_OTP_CODE);

        restTbOtpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbOtp.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTbOtp))
            )
            .andExpect(status().isOk());

        // Validate the TbOtp in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTbOtpUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTbOtp, tbOtp), getPersistedTbOtp(tbOtp));
    }

    @Test
    @Transactional
    void fullUpdateTbOtpWithPatch() throws Exception {
        // Initialize the database
        insertedTbOtp = tbOtpRepository.saveAndFlush(tbOtp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbOtp using partial update
        TbOtp partialUpdatedTbOtp = new TbOtp();
        partialUpdatedTbOtp.setId(tbOtp.getId());

        partialUpdatedTbOtp
            .otpCode(UPDATED_OTP_CODE)
            .createdAt(UPDATED_CREATED_AT)
            .expirationTime(UPDATED_EXPIRATION_TIME)
            .attemptCount(UPDATED_ATTEMPT_COUNT)
            .maxAttempts(UPDATED_MAX_ATTEMPTS);

        restTbOtpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbOtp.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTbOtp))
            )
            .andExpect(status().isOk());

        // Validate the TbOtp in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTbOtpUpdatableFieldsEquals(partialUpdatedTbOtp, getPersistedTbOtp(partialUpdatedTbOtp));
    }

    @Test
    @Transactional
    void patchNonExistingTbOtp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbOtp.setId(longCount.incrementAndGet());

        // Create the TbOtp
        TbOtpDTO tbOtpDTO = tbOtpMapper.toDto(tbOtp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbOtpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tbOtpDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tbOtpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbOtp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTbOtp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbOtp.setId(longCount.incrementAndGet());

        // Create the TbOtp
        TbOtpDTO tbOtpDTO = tbOtpMapper.toDto(tbOtp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbOtpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tbOtpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbOtp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTbOtp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbOtp.setId(longCount.incrementAndGet());

        // Create the TbOtp
        TbOtpDTO tbOtpDTO = tbOtpMapper.toDto(tbOtp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbOtpMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tbOtpDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbOtp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTbOtp() throws Exception {
        // Initialize the database
        insertedTbOtp = tbOtpRepository.saveAndFlush(tbOtp);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tbOtp
        restTbOtpMockMvc
            .perform(delete(ENTITY_API_URL_ID, tbOtp.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tbOtpRepository.count();
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

    protected TbOtp getPersistedTbOtp(TbOtp tbOtp) {
        return tbOtpRepository.findById(tbOtp.getId()).orElseThrow();
    }

    protected void assertPersistedTbOtpToMatchAllProperties(TbOtp expectedTbOtp) {
        assertTbOtpAllPropertiesEquals(expectedTbOtp, getPersistedTbOtp(expectedTbOtp));
    }

    protected void assertPersistedTbOtpToMatchUpdatableProperties(TbOtp expectedTbOtp) {
        assertTbOtpAllUpdatablePropertiesEquals(expectedTbOtp, getPersistedTbOtp(expectedTbOtp));
    }
}
