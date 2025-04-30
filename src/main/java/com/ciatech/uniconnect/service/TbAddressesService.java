package com.ciatech.uniconnect.service;

import com.ciatech.uniconnect.service.dto.TbAddressesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ciatech.uniconnect.domain.TbAddresses}.
 */
public interface TbAddressesService {
    /**
     * Save a tbAddresses.
     *
     * @param tbAddressesDTO the entity to save.
     * @return the persisted entity.
     */
    TbAddressesDTO save(TbAddressesDTO tbAddressesDTO);

    /**
     * Updates a tbAddresses.
     *
     * @param tbAddressesDTO the entity to update.
     * @return the persisted entity.
     */
    TbAddressesDTO update(TbAddressesDTO tbAddressesDTO);

    /**
     * Partially updates a tbAddresses.
     *
     * @param tbAddressesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TbAddressesDTO> partialUpdate(TbAddressesDTO tbAddressesDTO);

    /**
     * Get all the tbAddresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TbAddressesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tbAddresses.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TbAddressesDTO> findOne(Long id);

    /**
     * Delete the "id" tbAddresses.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
