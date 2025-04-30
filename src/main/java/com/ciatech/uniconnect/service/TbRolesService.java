package com.ciatech.uniconnect.service;

import com.ciatech.uniconnect.service.dto.TbRolesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ciatech.uniconnect.domain.TbRoles}.
 */
public interface TbRolesService {
    /**
     * Save a tbRoles.
     *
     * @param tbRolesDTO the entity to save.
     * @return the persisted entity.
     */
    TbRolesDTO save(TbRolesDTO tbRolesDTO);

    /**
     * Updates a tbRoles.
     *
     * @param tbRolesDTO the entity to update.
     * @return the persisted entity.
     */
    TbRolesDTO update(TbRolesDTO tbRolesDTO);

    /**
     * Partially updates a tbRoles.
     *
     * @param tbRolesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TbRolesDTO> partialUpdate(TbRolesDTO tbRolesDTO);

    /**
     * Get all the tbRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TbRolesDTO> findAll(Pageable pageable);

    /**
     * Get all the tbRoles with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TbRolesDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" tbRoles.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TbRolesDTO> findOne(Long id);

    /**
     * Delete the "id" tbRoles.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
