package com.ciatech.uniconnect.service;

import com.ciatech.uniconnect.service.dto.TbOtpDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ciatech.uniconnect.domain.TbOtp}.
 */
public interface TbOtpService {
    /**
     * Save a tbOtp.
     *
     * @param tbOtpDTO the entity to save.
     * @return the persisted entity.
     */
    TbOtpDTO save(TbOtpDTO tbOtpDTO);

    /**
     * Updates a tbOtp.
     *
     * @param tbOtpDTO the entity to update.
     * @return the persisted entity.
     */
    TbOtpDTO update(TbOtpDTO tbOtpDTO);

    /**
     * Partially updates a tbOtp.
     *
     * @param tbOtpDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TbOtpDTO> partialUpdate(TbOtpDTO tbOtpDTO);

    /**
     * Get all the tbOtps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TbOtpDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tbOtp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TbOtpDTO> findOne(Long id);

    /**
     * Delete the "id" tbOtp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
