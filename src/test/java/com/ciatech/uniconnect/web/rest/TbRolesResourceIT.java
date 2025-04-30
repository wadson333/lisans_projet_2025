package com.ciatech.uniconnect.web.rest;

import static com.ciatech.uniconnect.domain.TbRolesAsserts.*;
import static com.ciatech.uniconnect.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ciatech.uniconnect.IntegrationTest;
import com.ciatech.uniconnect.domain.TbRoles;
import com.ciatech.uniconnect.repository.TbRolesRepository;
import com.ciatech.uniconnect.service.TbRolesService;
import com.ciatech.uniconnect.service.dto.TbRolesDTO;
import com.ciatech.uniconnect.service.mapper.TbRolesMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link TbRolesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TbRolesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SYSTEM_ROLE = false;
    private static final Boolean UPDATED_IS_SYSTEM_ROLE = true;

    private static final Boolean DEFAULT_IS_EDITABLE = false;
    private static final Boolean UPDATED_IS_EDITABLE = true;

    private static final String ENTITY_API_URL = "/api/tb-roles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TbRolesRepository tbRolesRepository;

    @Mock
    private TbRolesRepository tbRolesRepositoryMock;

    @Autowired
    private TbRolesMapper tbRolesMapper;

    @Mock
    private TbRolesService tbRolesServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTbRolesMockMvc;

    private TbRoles tbRoles;

    private TbRoles insertedTbRoles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbRoles createEntity() {
        return new TbRoles()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .isSystemRole(DEFAULT_IS_SYSTEM_ROLE)
            .isEditable(DEFAULT_IS_EDITABLE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbRoles createUpdatedEntity() {
        return new TbRoles()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isSystemRole(UPDATED_IS_SYSTEM_ROLE)
            .isEditable(UPDATED_IS_EDITABLE);
    }

    @BeforeEach
    public void initTest() {
        tbRoles = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedTbRoles != null) {
            tbRolesRepository.delete(insertedTbRoles);
            insertedTbRoles = null;
        }
    }

    @Test
    @Transactional
    void createTbRoles() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TbRoles
        TbRolesDTO tbRolesDTO = tbRolesMapper.toDto(tbRoles);
        var returnedTbRolesDTO = om.readValue(
            restTbRolesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbRolesDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TbRolesDTO.class
        );

        // Validate the TbRoles in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTbRoles = tbRolesMapper.toEntity(returnedTbRolesDTO);
        assertTbRolesUpdatableFieldsEquals(returnedTbRoles, getPersistedTbRoles(returnedTbRoles));

        insertedTbRoles = returnedTbRoles;
    }

    @Test
    @Transactional
    void createTbRolesWithExistingId() throws Exception {
        // Create the TbRoles with an existing ID
        tbRoles.setId(1L);
        TbRolesDTO tbRolesDTO = tbRolesMapper.toDto(tbRoles);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbRolesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbRolesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TbRoles in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tbRoles.setName(null);

        // Create the TbRoles, which fails.
        TbRolesDTO tbRolesDTO = tbRolesMapper.toDto(tbRoles);

        restTbRolesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbRolesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTbRoles() throws Exception {
        // Initialize the database
        insertedTbRoles = tbRolesRepository.saveAndFlush(tbRoles);

        // Get all the tbRolesList
        restTbRolesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbRoles.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isSystemRole").value(hasItem(DEFAULT_IS_SYSTEM_ROLE.booleanValue())))
            .andExpect(jsonPath("$.[*].isEditable").value(hasItem(DEFAULT_IS_EDITABLE.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTbRolesWithEagerRelationshipsIsEnabled() throws Exception {
        when(tbRolesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTbRolesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tbRolesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTbRolesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tbRolesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTbRolesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tbRolesRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTbRoles() throws Exception {
        // Initialize the database
        insertedTbRoles = tbRolesRepository.saveAndFlush(tbRoles);

        // Get the tbRoles
        restTbRolesMockMvc
            .perform(get(ENTITY_API_URL_ID, tbRoles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tbRoles.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isSystemRole").value(DEFAULT_IS_SYSTEM_ROLE.booleanValue()))
            .andExpect(jsonPath("$.isEditable").value(DEFAULT_IS_EDITABLE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingTbRoles() throws Exception {
        // Get the tbRoles
        restTbRolesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTbRoles() throws Exception {
        // Initialize the database
        insertedTbRoles = tbRolesRepository.saveAndFlush(tbRoles);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbRoles
        TbRoles updatedTbRoles = tbRolesRepository.findById(tbRoles.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTbRoles are not directly saved in db
        em.detach(updatedTbRoles);
        updatedTbRoles
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isSystemRole(UPDATED_IS_SYSTEM_ROLE)
            .isEditable(UPDATED_IS_EDITABLE);
        TbRolesDTO tbRolesDTO = tbRolesMapper.toDto(updatedTbRoles);

        restTbRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbRolesDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbRolesDTO))
            )
            .andExpect(status().isOk());

        // Validate the TbRoles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTbRolesToMatchAllProperties(updatedTbRoles);
    }

    @Test
    @Transactional
    void putNonExistingTbRoles() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbRoles.setId(longCount.incrementAndGet());

        // Create the TbRoles
        TbRolesDTO tbRolesDTO = tbRolesMapper.toDto(tbRoles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbRolesDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbRolesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbRoles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTbRoles() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbRoles.setId(longCount.incrementAndGet());

        // Create the TbRoles
        TbRolesDTO tbRolesDTO = tbRolesMapper.toDto(tbRoles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbRolesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tbRolesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbRoles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTbRoles() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbRoles.setId(longCount.incrementAndGet());

        // Create the TbRoles
        TbRolesDTO tbRolesDTO = tbRolesMapper.toDto(tbRoles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbRolesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tbRolesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbRoles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTbRolesWithPatch() throws Exception {
        // Initialize the database
        insertedTbRoles = tbRolesRepository.saveAndFlush(tbRoles);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbRoles using partial update
        TbRoles partialUpdatedTbRoles = new TbRoles();
        partialUpdatedTbRoles.setId(tbRoles.getId());

        partialUpdatedTbRoles.description(UPDATED_DESCRIPTION).isSystemRole(UPDATED_IS_SYSTEM_ROLE);

        restTbRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbRoles.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTbRoles))
            )
            .andExpect(status().isOk());

        // Validate the TbRoles in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTbRolesUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTbRoles, tbRoles), getPersistedTbRoles(tbRoles));
    }

    @Test
    @Transactional
    void fullUpdateTbRolesWithPatch() throws Exception {
        // Initialize the database
        insertedTbRoles = tbRolesRepository.saveAndFlush(tbRoles);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tbRoles using partial update
        TbRoles partialUpdatedTbRoles = new TbRoles();
        partialUpdatedTbRoles.setId(tbRoles.getId());

        partialUpdatedTbRoles
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isSystemRole(UPDATED_IS_SYSTEM_ROLE)
            .isEditable(UPDATED_IS_EDITABLE);

        restTbRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbRoles.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTbRoles))
            )
            .andExpect(status().isOk());

        // Validate the TbRoles in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTbRolesUpdatableFieldsEquals(partialUpdatedTbRoles, getPersistedTbRoles(partialUpdatedTbRoles));
    }

    @Test
    @Transactional
    void patchNonExistingTbRoles() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbRoles.setId(longCount.incrementAndGet());

        // Create the TbRoles
        TbRolesDTO tbRolesDTO = tbRolesMapper.toDto(tbRoles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tbRolesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tbRolesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbRoles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTbRoles() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbRoles.setId(longCount.incrementAndGet());

        // Create the TbRoles
        TbRolesDTO tbRolesDTO = tbRolesMapper.toDto(tbRoles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbRolesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tbRolesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbRoles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTbRoles() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tbRoles.setId(longCount.incrementAndGet());

        // Create the TbRoles
        TbRolesDTO tbRolesDTO = tbRolesMapper.toDto(tbRoles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbRolesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tbRolesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbRoles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTbRoles() throws Exception {
        // Initialize the database
        insertedTbRoles = tbRolesRepository.saveAndFlush(tbRoles);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tbRoles
        restTbRolesMockMvc
            .perform(delete(ENTITY_API_URL_ID, tbRoles.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tbRolesRepository.count();
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

    protected TbRoles getPersistedTbRoles(TbRoles tbRoles) {
        return tbRolesRepository.findById(tbRoles.getId()).orElseThrow();
    }

    protected void assertPersistedTbRolesToMatchAllProperties(TbRoles expectedTbRoles) {
        assertTbRolesAllPropertiesEquals(expectedTbRoles, getPersistedTbRoles(expectedTbRoles));
    }

    protected void assertPersistedTbRolesToMatchUpdatableProperties(TbRoles expectedTbRoles) {
        assertTbRolesAllUpdatablePropertiesEquals(expectedTbRoles, getPersistedTbRoles(expectedTbRoles));
    }
}
