package com.ciatech.uniconnect.service;

import com.ciatech.uniconnect.service.dto.TbPermissionsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ciatech.uniconnect.domain.TbPermissions}.
 */
public interface TbPermissionsService {
    /**
     * Save a tbPermissions.
     *
     * @param tbPermissionsDTO the entity to save.
     * @return the persisted entity.
     */
    TbPermissionsDTO save(TbPermissionsDTO tbPermissionsDTO);

    /**
     * Updates a tbPermissions.
     *
     * @param tbPermissionsDTO the entity to update.
     * @return the persisted entity.
     */
    TbPermissionsDTO update(TbPermissionsDTO tbPermissionsDTO);

    /**
     * Partially updates a tbPermissions.
     *
     * @param tbPermissionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TbPermissionsDTO> partialUpdate(TbPermissionsDTO tbPermissionsDTO);

    /**
     * Get all the tbPermissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TbPermissionsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tbPermissions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TbPermissionsDTO> findOne(Long id);

    /**
     * Delete the "id" tbPermissions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
