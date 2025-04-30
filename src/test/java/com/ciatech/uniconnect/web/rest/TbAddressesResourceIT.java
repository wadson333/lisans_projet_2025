package com.ciatech.uniconnect.web.rest;

import static com.ciatech.uniconnect.domain.TbAddressesAsserts.*;
import static com.ciatech.uniconnect.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ciatech.uniconnect.IntegrationTest;
import com.ciatech.uniconnect.domain.TbAddresses;
import com.ciatech.uniconnect.repository.TbAddressesRepository;
import com.ciatech.uniconnect.service.dto.TbAddressesDTO;
import com.ciatech.uniconnect.service.mapper.TbAddressesMapper;
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
 * Integration tests for the {@link TbAddressesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TbAddressesResourceIT {

    private static final String DEFAULT_FULL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_FULL_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODE_SECTION_COMMUNALE = 1;
    private static final Integer UPDATED_CODE_SECTION_COMMUNALE = 2;

    private static final String ENTITY_API_URL = "/api/tb-addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TbAddressesRepository tbAddressesRepository;

    @Autowired
    private TbAddressesMapper tbAddressesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTbAddressesMockMvc;

    private TbAddresses tbAddresses;

    private TbAddresses insertedTbAddresses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbAddresses createEntity() {
        return new TbAddresses().fullAddress(DEFAULT_FULL_ADDRESS).codeSectionCommunale(DEFAULT_CODE_SECTION_COMMUNALE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbAddresses createUpdatedEntity() {
        return new TbAddresses().fullAddress(UPDATED_FULL_ADDRESS).codeSectionCommunale(UPDATED_CODE_SECTION_COMMUNALE);
    }

    @BeforeEach
    public void initTest() {
        tbAddresses = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedTbAddresses != null) {
            tbAddressesRepository.delete(insertedTbAddresses);
            insertedTbAddresses = null;
        }
    }

    @Test
    @Transactional
    void createTbAddresses() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TbAddresses
        TbAddressesDTO tbAddressesDTO = tbAddressesMapper.toDto(tbAddresses);
        var returnedTbAddressesDTO = om.readValue(
            restTbAddressesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbAddressesDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TbAddressesDTO.class
        );

        // Validate the TbAddresses in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTbAddresses = tbAddressesMapper.toEntity(returnedTbAddressesDTO);
        assertTbAddressesUpdatableFieldsEquals(returnedTbAddresses, getPersistedTbAddresses(returnedTbAddresses));

        insertedTbAddresses = returnedTbAddresses;
    }

    @Test
    @Transactional
    void createTbAddressesWithExistingId() throws Exception {
        // Create the TbAddresses with an existing ID
        tbAddresses.setId(1L);
        TbAddressesDTO tbAddressesDTO = tbAddressesMapper.toDto(tbAddresses);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbAddressesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbAddressesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TbAddresses in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFullAddressIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tbAddresses.setFullAddress(null);

        // Create the TbAddresses, which fails.
        TbAddressesDTO tbAddressesDTO = tbAddressesMapper.toDto(tbAddresses);

        restTbAddressesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbAddressesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTbAddresses() throws Exception {
        // Initialize the database
        insertedTbAddresses = tbAddressesRepository.saveAndFlush(tbAddresses);

        // Get all the tbAddressesList
        restTbAddressesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbAddresses.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullAddress").value(hasItem(DEFAULT_FULL_ADDRESS)))
            .andExpect(jsonPath("$.[*].codeSectionCommunale").value(hasItem(DEFAULT_CODE_SECTION_COMMUNALE)));
    }

    @Test
    @Transactional
    void getTbAddresses() throws Exception {
        // Initialize the database
        insertedTbAddresses = tbAddressesRepository.saveAndFlush(tbAddresses);

        // Get the tbAddresses
        restTbAddressesMockMvc
            .perform(get(ENTITY_API_URL_ID, tbAddresses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tbAddresses.getId().intValue()))
            .andExpect(jsonPath("$.fullAddress").value(DEFAULT_FULL_ADDRESS))
            .andExpect(jsonPath("$.codeSectionCommunale").value(DEFAULT_CODE_SECTION_COMMUNALE));
    }

    @Test
    @Transactional
    void getNonExistingTbAddresses() throws Exception {
        // Get the tbAddresses
        restTbAddressesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTbAddresses() throws Exception {
        // Initialize the database
        insertedTbAddresses = tbAddressesRepository.saveAndFlush(tbAddresses);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbAddresses
        TbAddresses updatedTbAddresses = tbAddressesRepository.findById(tbAddresses.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTbAddresses are not directly saved in db
        em.detach(updatedTbAddresses);
        updatedTbAddresses.fullAddress(UPDATED_FULL_ADDRESS).codeSectionCommunale(UPDATED_CODE_SECTION_COMMUNALE);
        TbAddressesDTO tbAddressesDTO = tbAddressesMapper.toDto(updatedTbAddresses);

        restTbAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbAddressesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tbAddressesDTO))
            )
            .andExpect(status().isOk());

        // Validate the TbAddresses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTbAddressesToMatchAllProperties(updatedTbAddresses);
    }

    @Test
    @Transactional
    void putNonExistingTbAddresses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbAddresses.setId(longCount.incrementAndGet());

        // Create the TbAddresses
        TbAddressesDTO tbAddressesDTO = tbAddressesMapper.toDto(tbAddresses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbAddressesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tbAddressesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbAddresses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTbAddresses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbAddresses.setId(longCount.incrementAndGet());

        // Create the TbAddresses
        TbAddressesDTO tbAddressesDTO = tbAddressesMapper.toDto(tbAddresses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tbAddressesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbAddresses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTbAddresses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbAddresses.setId(longCount.incrementAndGet());

        // Create the TbAddresses
        TbAddressesDTO tbAddressesDTO = tbAddressesMapper.toDto(tbAddresses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbAddressesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbAddressesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbAddresses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTbAddressesWithPatch() throws Exception {
        // Initialize the database
        insertedTbAddresses = tbAddressesRepository.saveAndFlush(tbAddresses);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbAddresses using partial update
        TbAddresses partialUpdatedTbAddresses = new TbAddresses();
        partialUpdatedTbAddresses.setId(tbAddresses.getId());

        partialUpdatedTbAddresses.codeSectionCommunale(UPDATED_CODE_SECTION_COMMUNALE);

        restTbAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbAddresses.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTbAddresses))
            )
            .andExpect(status().isOk());

        // Validate the TbAddresses in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTbAddressesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTbAddresses, tbAddresses),
            getPersistedTbAddresses(tbAddresses)
        );
    }

    @Test
    @Transactional
    void fullUpdateTbAddressesWithPatch() throws Exception {
        // Initialize the database
        insertedTbAddresses = tbAddressesRepository.saveAndFlush(tbAddresses);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbAddresses using partial update
        TbAddresses partialUpdatedTbAddresses = new TbAddresses();
        partialUpdatedTbAddresses.setId(tbAddresses.getId());

        partialUpdatedTbAddresses.fullAddress(UPDATED_FULL_ADDRESS).codeSectionCommunale(UPDATED_CODE_SECTION_COMMUNALE);

        restTbAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbAddresses.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTbAddresses))
            )
            .andExpect(status().isOk());

        // Validate the TbAddresses in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTbAddressesUpdatableFieldsEquals(partialUpdatedTbAddresses, getPersistedTbAddresses(partialUpdatedTbAddresses));
    }

    @Test
    @Transactional
    void patchNonExistingTbAddresses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbAddresses.setId(longCount.incrementAndGet());

        // Create the TbAddresses
        TbAddressesDTO tbAddressesDTO = tbAddressesMapper.toDto(tbAddresses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tbAddressesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tbAddressesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbAddresses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTbAddresses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbAddresses.setId(longCount.incrementAndGet());

        // Create the TbAddresses
        TbAddressesDTO tbAddressesDTO = tbAddressesMapper.toDto(tbAddresses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tbAddressesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbAddresses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTbAddresses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbAddresses.setId(longCount.incrementAndGet());

        // Create the TbAddresses
        TbAddressesDTO tbAddressesDTO = tbAddressesMapper.toDto(tbAddresses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbAddressesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tbAddressesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbAddresses in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTbAddresses() throws Exception {
        // Initialize the database
        insertedTbAddresses = tbAddressesRepository.saveAndFlush(tbAddresses);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tbAddresses
        restTbAddressesMockMvc
            .perform(delete(ENTITY_API_URL_ID, tbAddresses.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tbAddressesRepository.count();
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

    protected TbAddresses getPersistedTbAddresses(TbAddresses tbAddresses) {
        return tbAddressesRepository.findById(tbAddresses.getId()).orElseThrow();
    }

    protected void assertPersistedTbAddressesToMatchAllProperties(TbAddresses expectedTbAddresses) {
        assertTbAddressesAllPropertiesEquals(expectedTbAddresses, getPersistedTbAddresses(expectedTbAddresses));
    }

    protected void assertPersistedTbAddressesToMatchUpdatableProperties(TbAddresses expectedTbAddresses) {
        assertTbAddressesAllUpdatablePropertiesEquals(expectedTbAddresses, getPersistedTbAddresses(expectedTbAddresses));
    }
}
