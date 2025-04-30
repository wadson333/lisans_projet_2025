package com.ciatech.uniconnect.service;

import com.ciatech.uniconnect.service.dto.TbPasswordHistoryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ciatech.uniconnect.domain.TbPasswordHistory}.
 */
public interface TbPasswordHistoryService {
    /**
     * Save a tbPasswordHistory.
     *
     * @param tbPasswordHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    TbPasswordHistoryDTO save(TbPasswordHistoryDTO tbPasswordHistoryDTO);

    /**
     * Updates a tbPasswordHistory.
     *
     * @param tbPasswordHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    TbPasswordHistoryDTO update(TbPasswordHistoryDTO tbPasswordHistoryDTO);

    /**
     * Partially updates a tbPasswordHistory.
     *
     * @param tbPasswordHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TbPasswordHistoryDTO> partialUpdate(TbPasswordHistoryDTO tbPasswordHistoryDTO);

    /**
     * Get all the tbPasswordHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TbPasswordHistoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tbPasswordHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TbPasswordHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" tbPasswordHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
