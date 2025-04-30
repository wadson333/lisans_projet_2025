package com.ciatech.uniconnect.web.rest;

import static com.ciatech.uniconnect.domain.TbPasswordHistoryAsserts.*;
import static com.ciatech.uniconnect.web.rest.TestUtil.createUpdateProxyForBean;
import static com.ciatech.uniconnect.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ciatech.uniconnect.IntegrationTest;
import com.ciatech.uniconnect.domain.TbPasswordHistory;
import com.ciatech.uniconnect.repository.TbPasswordHistoryRepository;
import com.ciatech.uniconnect.service.dto.TbPasswordHistoryDTO;
import com.ciatech.uniconnect.service.mapper.TbPasswordHistoryMapper;
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
 * Integration tests for the {@link TbPasswordHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TbPasswordHistoryResourceIT {

    private static final String DEFAULT_PASSWORD_HASH = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD_SALT = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD_SALT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_IS_CURRENT = false;
    private static final Boolean UPDATED_IS_CURRENT = true;

    private static final String DEFAULT_CHANGE_REASON = "AAAAAAAAAA";
    private static final String UPDATED_CHANGE_REASON = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_VALID_TO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_VALID_TO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_CAN_REUSE = false;
    private static final Boolean UPDATED_CAN_REUSE = true;

    private static final String ENTITY_API_URL = "/api/tb-password-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TbPasswordHistoryRepository tbPasswordHistoryRepository;

    @Autowired
    private TbPasswordHistoryMapper tbPasswordHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTbPasswordHistoryMockMvc;

    private TbPasswordHistory tbPasswordHistory;

    private TbPasswordHistory insertedTbPasswordHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbPasswordHistory createEntity() {
        return new TbPasswordHistory()
            .passwordHash(DEFAULT_PASSWORD_HASH)
            .passwordSalt(DEFAULT_PASSWORD_SALT)
            .createdAt(DEFAULT_CREATED_AT)
            .isCurrent(DEFAULT_IS_CURRENT)
            .changeReason(DEFAULT_CHANGE_REASON)
            .validTo(DEFAULT_VALID_TO)
            .canReuse(DEFAULT_CAN_REUSE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbPasswordHistory createUpdatedEntity() {
        return new TbPasswordHistory()
            .passwordHash(UPDATED_PASSWORD_HASH)
            .passwordSalt(UPDATED_PASSWORD_SALT)
            .createdAt(UPDATED_CREATED_AT)
            .isCurrent(UPDATED_IS_CURRENT)
            .changeReason(UPDATED_CHANGE_REASON)
            .validTo(UPDATED_VALID_TO)
            .canReuse(UPDATED_CAN_REUSE);
    }

    @BeforeEach
    public void initTest() {
        tbPasswordHistory = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedTbPasswordHistory != null) {
            tbPasswordHistoryRepository.delete(insertedTbPasswordHistory);
            insertedTbPasswordHistory = null;
        }
    }

    @Test
    @Transactional
    void createTbPasswordHistory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TbPasswordHistory
        TbPasswordHistoryDTO tbPasswordHistoryDTO = tbPasswordHistoryMapper.toDto(tbPasswordHistory);
        var returnedTbPasswordHistoryDTO = om.readValue(
            restTbPasswordHistoryMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbPasswordHistoryDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TbPasswordHistoryDTO.class
        );

        // Validate the TbPasswordHistory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTbPasswordHistory = tbPasswordHistoryMapper.toEntity(returnedTbPasswordHistoryDTO);
        assertTbPasswordHistoryUpdatableFieldsEquals(returnedTbPasswordHistory, getPersistedTbPasswordHistory(returnedTbPasswordHistory));

        insertedTbPasswordHistory = returnedTbPasswordHistory;
    }

    @Test
    @Transactional
    void createTbPasswordHistoryWithExistingId() throws Exception {
        // Create the TbPasswordHistory with an existing ID
        tbPasswordHistory.setId(1L);
        TbPasswordHistoryDTO tbPasswordHistoryDTO = tbPasswordHistoryMapper.toDto(tbPasswordHistory);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbPasswordHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbPasswordHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TbPasswordHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPasswordHashIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tbPasswordHistory.setPasswordHash(null);

        // Create the TbPasswordHistory, which fails.
        TbPasswordHistoryDTO tbPasswordHistoryDTO = tbPasswordHistoryMapper.toDto(tbPasswordHistory);

        restTbPasswordHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbPasswordHistoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTbPasswordHistories() throws Exception {
        // Initialize the database
        insertedTbPasswordHistory = tbPasswordHistoryRepository.saveAndFlush(tbPasswordHistory);

        // Get all the tbPasswordHistoryList
        restTbPasswordHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbPasswordHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].passwordHash").value(hasItem(DEFAULT_PASSWORD_HASH)))
            .andExpect(jsonPath("$.[*].passwordSalt").value(hasItem(DEFAULT_PASSWORD_SALT)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].isCurrent").value(hasItem(DEFAULT_IS_CURRENT.booleanValue())))
            .andExpect(jsonPath("$.[*].changeReason").value(hasItem(DEFAULT_CHANGE_REASON)))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(sameInstant(DEFAULT_VALID_TO))))
            .andExpect(jsonPath("$.[*].canReuse").value(hasItem(DEFAULT_CAN_REUSE.booleanValue())));
    }

    @Test
    @Transactional
    void getTbPasswordHistory() throws Exception {
        // Initialize the database
        insertedTbPasswordHistory = tbPasswordHistoryRepository.saveAndFlush(tbPasswordHistory);

        // Get the tbPasswordHistory
        restTbPasswordHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, tbPasswordHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tbPasswordHistory.getId().intValue()))
            .andExpect(jsonPath("$.passwordHash").value(DEFAULT_PASSWORD_HASH))
            .andExpect(jsonPath("$.passwordSalt").value(DEFAULT_PASSWORD_SALT))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.isCurrent").value(DEFAULT_IS_CURRENT.booleanValue()))
            .andExpect(jsonPath("$.changeReason").value(DEFAULT_CHANGE_REASON))
            .andExpect(jsonPath("$.validTo").value(sameInstant(DEFAULT_VALID_TO)))
            .andExpect(jsonPath("$.canReuse").value(DEFAULT_CAN_REUSE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingTbPasswordHistory() throws Exception {
        // Get the tbPasswordHistory
        restTbPasswordHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTbPasswordHistory() throws Exception {
        // Initialize the database
        insertedTbPasswordHistory = tbPasswordHistoryRepository.saveAndFlush(tbPasswordHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbPasswordHistory
        TbPasswordHistory updatedTbPasswordHistory = tbPasswordHistoryRepository.findById(tbPasswordHistory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTbPasswordHistory are not directly saved in db
        em.detach(updatedTbPasswordHistory);
        updatedTbPasswordHistory
            .passwordHash(UPDATED_PASSWORD_HASH)
            .passwordSalt(UPDATED_PASSWORD_SALT)
            .createdAt(UPDATED_CREATED_AT)
            .isCurrent(UPDATED_IS_CURRENT)
            .changeReason(UPDATED_CHANGE_REASON)
            .validTo(UPDATED_VALID_TO)
            .canReuse(UPDATED_CAN_REUSE);
        TbPasswordHistoryDTO tbPasswordHistoryDTO = tbPasswordHistoryMapper.toDto(updatedTbPasswordHistory);

        restTbPasswordHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbPasswordHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tbPasswordHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the TbPasswordHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTbPasswordHistoryToMatchAllProperties(updatedTbPasswordHistory);
    }

    @Test
    @Transactional
    void putNonExistingTbPasswordHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbPasswordHistory.setId(longCount.incrementAndGet());

        // Create the TbPasswordHistory
        TbPasswordHistoryDTO tbPasswordHistoryDTO = tbPasswordHistoryMapper.toDto(tbPasswordHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbPasswordHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbPasswordHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tbPasswordHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbPasswordHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTbPasswordHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbPasswordHistory.setId(longCount.incrementAndGet());

        // Create the TbPasswordHistory
        TbPasswordHistoryDTO tbPasswordHistoryDTO = tbPasswordHistoryMapper.toDto(tbPasswordHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbPasswordHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tbPasswordHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbPasswordHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTbPasswordHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbPasswordHistory.setId(longCount.incrementAndGet());

        // Create the TbPasswordHistory
        TbPasswordHistoryDTO tbPasswordHistoryDTO = tbPasswordHistoryMapper.toDto(tbPasswordHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbPasswordHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbPasswordHistoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbPasswordHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTbPasswordHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedTbPasswordHistory = tbPasswordHistoryRepository.saveAndFlush(tbPasswordHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbPasswordHistory using partial update
        TbPasswordHistory partialUpdatedTbPasswordHistory = new TbPasswordHistory();
        partialUpdatedTbPasswordHistory.setId(tbPasswordHistory.getId());

        partialUpdatedTbPasswordHistory
            .passwordHash(UPDATED_PASSWORD_HASH)
            .passwordSalt(UPDATED_PASSWORD_SALT)
            .isCurrent(UPDATED_IS_CURRENT)
            .canReuse(UPDATED_CAN_REUSE);

        restTbPasswordHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbPasswordHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTbPasswordHistory))
            )
            .andExpect(status().isOk());

        // Validate the TbPasswordHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTbPasswordHistoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTbPasswordHistory, tbPasswordHistory),
            getPersistedTbPasswordHistory(tbPasswordHistory)
        );
    }

    @Test
    @Transactional
    void fullUpdateTbPasswordHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedTbPasswordHistory = tbPasswordHistoryRepository.saveAndFlush(tbPasswordHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbPasswordHistory using partial update
        TbPasswordHistory partialUpdatedTbPasswordHistory = new TbPasswordHistory();
        partialUpdatedTbPasswordHistory.setId(tbPasswordHistory.getId());

        partialUpdatedTbPasswordHistory
            .passwordHash(UPDATED_PASSWORD_HASH)
            .passwordSalt(UPDATED_PASSWORD_SALT)
            .createdAt(UPDATED_CREATED_AT)
            .isCurrent(UPDATED_IS_CURRENT)
            .changeReason(UPDATED_CHANGE_REASON)
            .validTo(UPDATED_VALID_TO)
            .canReuse(UPDATED_CAN_REUSE);

        restTbPasswordHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbPasswordHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTbPasswordHistory))
            )
            .andExpect(status().isOk());

        // Validate the TbPasswordHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTbPasswordHistoryUpdatableFieldsEquals(
            partialUpdatedTbPasswordHistory,
            getPersistedTbPasswordHistory(partialUpdatedTbPasswordHistory)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTbPasswordHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbPasswordHistory.setId(longCount.incrementAndGet());

        // Create the TbPasswordHistory
        TbPasswordHistoryDTO tbPasswordHistoryDTO = tbPasswordHistoryMapper.toDto(tbPasswordHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbPasswordHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tbPasswordHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tbPasswordHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbPasswordHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTbPasswordHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbPasswordHistory.setId(longCount.incrementAndGet());

        // Create the TbPasswordHistory
        TbPasswordHistoryDTO tbPasswordHistoryDTO = tbPasswordHistoryMapper.toDto(tbPasswordHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbPasswordHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tbPasswordHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbPasswordHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTbPasswordHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbPasswordHistory.setId(longCount.incrementAndGet());

        // Create the TbPasswordHistory
        TbPasswordHistoryDTO tbPasswordHistoryDTO = tbPasswordHistoryMapper.toDto(tbPasswordHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbPasswordHistoryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tbPasswordHistoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbPasswordHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTbPasswordHistory() throws Exception {
        // Initialize the database
        insertedTbPasswordHistory = tbPasswordHistoryRepository.saveAndFlush(tbPasswordHistory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tbPasswordHistory
        restTbPasswordHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, tbPasswordHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tbPasswordHistoryRepository.count();
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

    protected TbPasswordHistory getPersistedTbPasswordHistory(TbPasswordHistory tbPasswordHistory) {
        return tbPasswordHistoryRepository.findById(tbPasswordHistory.getId()).orElseThrow();
    }

    protected void assertPersistedTbPasswordHistoryToMatchAllProperties(TbPasswordHistory expectedTbPasswordHistory) {
        assertTbPasswordHistoryAllPropertiesEquals(expectedTbPasswordHistory, getPersistedTbPasswordHistory(expectedTbPasswordHistory));
    }

    protected void assertPersistedTbPasswordHistoryToMatchUpdatableProperties(TbPasswordHistory expectedTbPasswordHistory) {
        assertTbPasswordHistoryAllUpdatablePropertiesEquals(
            expectedTbPasswordHistory,
            getPersistedTbPasswordHistory(expectedTbPasswordHistory)
        );
    }
}
