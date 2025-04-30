package com.ciatech.uniconnect.service;

import com.ciatech.uniconnect.service.dto.TbUsersDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ciatech.uniconnect.domain.TbUsers}.
 */
public interface TbUsersService {
    /**
     * Save a tbUsers.
     *
     * @param tbUsersDTO the entity to save.
     * @return the persisted entity.
     */
    TbUsersDTO save(TbUsersDTO tbUsersDTO);

    /**
     * Updates a tbUsers.
     *
     * @param tbUsersDTO the entity to update.
     * @return the persisted entity.
     */
    TbUsersDTO update(TbUsersDTO tbUsersDTO);

    /**
     * Partially updates a tbUsers.
     *
     * @param tbUsersDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TbUsersDTO> partialUpdate(TbUsersDTO tbUsersDTO);

    /**
     * Get all the tbUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TbUsersDTO> findAll(Pageable pageable);

    /**
     * Get all the tbUsers with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TbUsersDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" tbUsers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TbUsersDTO> findOne(Long id);

    /**
     * Delete the "id" tbUsers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
