package com.ciatech.uniconnect.web.rest;

import static com.ciatech.uniconnect.domain.TbPermissionsAsserts.*;
import static com.ciatech.uniconnect.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ciatech.uniconnect.IntegrationTest;
import com.ciatech.uniconnect.domain.TbPermissions;
import com.ciatech.uniconnect.repository.TbPermissionsRepository;
import com.ciatech.uniconnect.service.dto.TbPermissionsDTO;
import com.ciatech.uniconnect.service.mapper.TbPermissionsMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link TbPermissionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TbPermissionsResourceIT {

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tb-permissions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TbPermissionsRepository tbPermissionsRepository;

    @Autowired
    private TbPermissionsMapper tbPermissionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTbPermissionsMockMvc;

    private TbPermissions tbPermissions;

    private TbPermissions insertedTbPermissions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbPermissions createEntity() {
        return new TbPermissions().action(DEFAULT_ACTION).description(DEFAULT_DESCRIPTION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbPermissions createUpdatedEntity() {
        return new TbPermissions().action(UPDATED_ACTION).description(UPDATED_DESCRIPTION);
    }

    @BeforeEach
    public void initTest() {
        tbPermissions = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedTbPermissions != null) {
            tbPermissionsRepository.delete(insertedTbPermissions);
            insertedTbPermissions = null;
        }
    }

    @Test
    @Transactional
    void createTbPermissions() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TbPermissions
        TbPermissionsDTO tbPermissionsDTO = tbPermissionsMapper.toDto(tbPermissions);
        var returnedTbPermissionsDTO = om.readValue(
            restTbPermissionsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbPermissionsDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TbPermissionsDTO.class
        );

        // Validate the TbPermissions in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTbPermissions = tbPermissionsMapper.toEntity(returnedTbPermissionsDTO);
        assertTbPermissionsUpdatableFieldsEquals(returnedTbPermissions, getPersistedTbPermissions(returnedTbPermissions));

        insertedTbPermissions = returnedTbPermissions;
    }

    @Test
    @Transactional
    void createTbPermissionsWithExistingId() throws Exception {
        // Create the TbPermissions with an existing ID
        tbPermissions.setId(1L);
        TbPermissionsDTO tbPermissionsDTO = tbPermissionsMapper.toDto(tbPermissions);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbPermissionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbPermissionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TbPermissions in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkActionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tbPermissions.setAction(null);

        // Create the TbPermissions, which fails.
        TbPermissionsDTO tbPermissionsDTO = tbPermissionsMapper.toDto(tbPermissions);

        restTbPermissionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbPermissionsDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTbPermissions() throws Exception {
        // Initialize the database
        insertedTbPermissions = tbPermissionsRepository.saveAndFlush(tbPermissions);

        // Get all the tbPermissionsList
        restTbPermissionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbPermissions.getId().intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getTbPermissions() throws Exception {
        // Initialize the database
        insertedTbPermissions = tbPermissionsRepository.saveAndFlush(tbPermissions);

        // Get the tbPermissions
        restTbPermissionsMockMvc
            .perform(get(ENTITY_API_URL_ID, tbPermissions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tbPermissions.getId().intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingTbPermissions() throws Exception {
        // Get the tbPermissions
        restTbPermissionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTbPermissions() throws Exception {
        // Initialize the database
        insertedTbPermissions = tbPermissionsRepository.saveAndFlush(tbPermissions);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbPermissions
        TbPermissions updatedTbPermissions = tbPermissionsRepository.findById(tbPermissions.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTbPermissions are not directly saved in db
        em.detach(updatedTbPermissions);
        updatedTbPermissions.action(UPDATED_ACTION).description(UPDATED_DESCRIPTION);
        TbPermissionsDTO tbPermissionsDTO = tbPermissionsMapper.toDto(updatedTbPermissions);

        restTbPermissionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbPermissionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tbPermissionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the TbPermissions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTbPermissionsToMatchAllProperties(updatedTbPermissions);
    }

    @Test
    @Transactional
    void putNonExistingTbPermissions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbPermissions.setId(longCount.incrementAndGet());

        // Create the TbPermissions
        TbPermissionsDTO tbPermissionsDTO = tbPermissionsMapper.toDto(tbPermissions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbPermissionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbPermissionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tbPermissionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbPermissions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTbPermissions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbPermissions.setId(longCount.incrementAndGet());

        // Create the TbPermissions
        TbPermissionsDTO tbPermissionsDTO = tbPermissionsMapper.toDto(tbPermissions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbPermissionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tbPermissionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbPermissions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTbPermissions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbPermissions.setId(longCount.incrementAndGet());

        // Create the TbPermissions
        TbPermissionsDTO tbPermissionsDTO = tbPermissionsMapper.toDto(tbPermissions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbPermissionsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbPermissionsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbPermissions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTbPermissionsWithPatch() throws Exception {
        // Initialize the database
        insertedTbPermissions = tbPermissionsRepository.saveAndFlush(tbPermissions);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbPermissions using partial update
        TbPermissions partialUpdatedTbPermissions = new TbPermissions();
        partialUpdatedTbPermissions.setId(tbPermissions.getId());

        partialUpdatedTbPermissions.action(UPDATED_ACTION);

        restTbPermissionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbPermissions.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTbPermissions))
            )
            .andExpect(status().isOk());

        // Validate the TbPermissions in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTbPermissionsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTbPermissions, tbPermissions),
            getPersistedTbPermissions(tbPermissions)
        );
    }

    @Test
    @Transactional
    void fullUpdateTbPermissionsWithPatch() throws Exception {
        // Initialize the database
        insertedTbPermissions = tbPermissionsRepository.saveAndFlush(tbPermissions);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbPermissions using partial update
        TbPermissions partialUpdatedTbPermissions = new TbPermissions();
        partialUpdatedTbPermissions.setId(tbPermissions.getId());

        partialUpdatedTbPermissions.action(UPDATED_ACTION).description(UPDATED_DESCRIPTION);

        restTbPermissionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbPermissions.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTbPermissions))
            )
            .andExpect(status().isOk());

        // Validate the TbPermissions in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTbPermissionsUpdatableFieldsEquals(partialUpdatedTbPermissions, getPersistedTbPermissions(partialUpdatedTbPermissions));
    }

    @Test
    @Transactional
    void patchNonExistingTbPermissions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbPermissions.setId(longCount.incrementAndGet());

        // Create the TbPermissions
        TbPermissionsDTO tbPermissionsDTO = tbPermissionsMapper.toDto(tbPermissions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbPermissionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tbPermissionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tbPermissionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbPermissions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTbPermissions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbPermissions.setId(longCount.incrementAndGet());

        // Create the TbPermissions
        TbPermissionsDTO tbPermissionsDTO = tbPermissionsMapper.toDto(tbPermissions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbPermissionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tbPermissionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbPermissions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTbPermissions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbPermissions.setId(longCount.incrementAndGet());

        // Create the TbPermissions
        TbPermissionsDTO tbPermissionsDTO = tbPermissionsMapper.toDto(tbPermissions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbPermissionsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tbPermissionsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbPermissions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTbPermissions() throws Exception {
        // Initialize the database
        insertedTbPermissions = tbPermissionsRepository.saveAndFlush(tbPermissions);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tbPermissions
        restTbPermissionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, tbPermissions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tbPermissionsRepository.count();
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

    protected TbPermissions getPersistedTbPermissions(TbPermissions tbPermissions) {
        return tbPermissionsRepository.findById(tbPermissions.getId()).orElseThrow();
    }

    protected void assertPersistedTbPermissionsToMatchAllProperties(TbPermissions expectedTbPermissions) {
        assertTbPermissionsAllPropertiesEquals(expectedTbPermissions, getPersistedTbPermissions(expectedTbPermissions));
    }

    protected void assertPersistedTbPermissionsToMatchUpdatableProperties(TbPermissions expectedTbPermissions) {
        assertTbPermissionsAllUpdatablePropertiesEquals(expectedTbPermissions, getPersistedTbPermissions(expectedTbPermissions));
    }
}
