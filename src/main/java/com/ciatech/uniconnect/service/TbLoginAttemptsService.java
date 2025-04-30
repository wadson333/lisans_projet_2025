package com.ciatech.uniconnect.service;

import com.ciatech.uniconnect.service.dto.TbLoginAttemptsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ciatech.uniconnect.domain.TbLoginAttempts}.
 */
public interface TbLoginAttemptsService {
    /**
     * Save a tbLoginAttempts.
     *
     * @param tbLoginAttemptsDTO the entity to save.
     * @return the persisted entity.
     */
    TbLoginAttemptsDTO save(TbLoginAttemptsDTO tbLoginAttemptsDTO);

    /**
     * Updates a tbLoginAttempts.
     *
     * @param tbLoginAttemptsDTO the entity to update.
     * @return the persisted entity.
     */
    TbLoginAttemptsDTO update(TbLoginAttemptsDTO tbLoginAttemptsDTO);

    /**
     * Partially updates a tbLoginAttempts.
     *
     * @param tbLoginAttemptsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TbLoginAttemptsDTO> partialUpdate(TbLoginAttemptsDTO tbLoginAttemptsDTO);

    /**
     * Get all the tbLoginAttempts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TbLoginAttemptsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tbLoginAttempts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TbLoginAttemptsDTO> findOne(Long id);

    /**
     * Delete the "id" tbLoginAttempts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
