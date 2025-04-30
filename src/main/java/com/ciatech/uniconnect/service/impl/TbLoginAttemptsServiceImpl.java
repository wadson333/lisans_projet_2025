package com.ciatech.uniconnect.service.impl;

import com.ciatech.uniconnect.domain.TbLoginAttempts;
import com.ciatech.uniconnect.repository.TbLoginAttemptsRepository;
import com.ciatech.uniconnect.service.TbLoginAttemptsService;
import com.ciatech.uniconnect.service.dto.TbLoginAttemptsDTO;
import com.ciatech.uniconnect.service.mapper.TbLoginAttemptsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ciatech.uniconnect.domain.TbLoginAttempts}.
 */
@Service
@Transactional
public class TbLoginAttemptsServiceImpl implements TbLoginAttemptsService {

    private static final Logger LOG = LoggerFactory.getLogger(TbLoginAttemptsServiceImpl.class);

    private final TbLoginAttemptsRepository tbLoginAttemptsRepository;

    private final TbLoginAttemptsMapper tbLoginAttemptsMapper;

    public TbLoginAttemptsServiceImpl(TbLoginAttemptsRepository tbLoginAttemptsRepository, TbLoginAttemptsMapper tbLoginAttemptsMapper) {
        this.tbLoginAttemptsRepository = tbLoginAttemptsRepository;
        this.tbLoginAttemptsMapper = tbLoginAttemptsMapper;
    }

    @Override
    public TbLoginAttemptsDTO save(TbLoginAttemptsDTO tbLoginAttemptsDTO) {
        LOG.debug("Request to save TbLoginAttempts : {}", tbLoginAttemptsDTO);
        TbLoginAttempts tbLoginAttempts = tbLoginAttemptsMapper.toEntity(tbLoginAttemptsDTO);
        tbLoginAttempts = tbLoginAttemptsRepository.save(tbLoginAttempts);
        return tbLoginAttemptsMapper.toDto(tbLoginAttempts);
    }

    @Override
    public TbLoginAttemptsDTO update(TbLoginAttemptsDTO tbLoginAttemptsDTO) {
        LOG.debug("Request to update TbLoginAttempts : {}", tbLoginAttemptsDTO);
        TbLoginAttempts tbLoginAttempts = tbLoginAttemptsMapper.toEntity(tbLoginAttemptsDTO);
        tbLoginAttempts = tbLoginAttemptsRepository.save(tbLoginAttempts);
        return tbLoginAttemptsMapper.toDto(tbLoginAttempts);
    }

    @Override
    public Optional<TbLoginAttemptsDTO> partialUpdate(TbLoginAttemptsDTO tbLoginAttemptsDTO) {
        LOG.debug("Request to partially update TbLoginAttempts : {}", tbLoginAttemptsDTO);

        return tbLoginAttemptsRepository
            .findById(tbLoginAttemptsDTO.getId())
            .map(existingTbLoginAttempts -> {
                tbLoginAttemptsMapper.partialUpdate(existingTbLoginAttempts, tbLoginAttemptsDTO);

                return existingTbLoginAttempts;
            })
            .map(tbLoginAttemptsRepository::save)
            .map(tbLoginAttemptsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TbLoginAttemptsDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all TbLoginAttempts");
        return tbLoginAttemptsRepository.findAll(pageable).map(tbLoginAttemptsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TbLoginAttemptsDTO> findOne(Long id) {
        LOG.debug("Request to get TbLoginAttempts : {}", id);
        return tbLoginAttemptsRepository.findById(id).map(tbLoginAttemptsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete TbLoginAttempts : {}", id);
        tbLoginAttemptsRepository.deleteById(id);
    }
}
