package com.ciatech.uniconnect.service.impl;

import com.ciatech.uniconnect.domain.TbPasswordHistory;
import com.ciatech.uniconnect.repository.TbPasswordHistoryRepository;
import com.ciatech.uniconnect.service.TbPasswordHistoryService;
import com.ciatech.uniconnect.service.dto.TbPasswordHistoryDTO;
import com.ciatech.uniconnect.service.mapper.TbPasswordHistoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ciatech.uniconnect.domain.TbPasswordHistory}.
 */
@Service
@Transactional
public class TbPasswordHistoryServiceImpl implements TbPasswordHistoryService {

    private static final Logger LOG = LoggerFactory.getLogger(TbPasswordHistoryServiceImpl.class);

    private final TbPasswordHistoryRepository tbPasswordHistoryRepository;

    private final TbPasswordHistoryMapper tbPasswordHistoryMapper;

    public TbPasswordHistoryServiceImpl(
        TbPasswordHistoryRepository tbPasswordHistoryRepository,
        TbPasswordHistoryMapper tbPasswordHistoryMapper
    ) {
        this.tbPasswordHistoryRepository = tbPasswordHistoryRepository;
        this.tbPasswordHistoryMapper = tbPasswordHistoryMapper;
    }

    @Override
    public TbPasswordHistoryDTO save(TbPasswordHistoryDTO tbPasswordHistoryDTO) {
        LOG.debug("Request to save TbPasswordHistory : {}", tbPasswordHistoryDTO);
        TbPasswordHistory tbPasswordHistory = tbPasswordHistoryMapper.toEntity(tbPasswordHistoryDTO);
        tbPasswordHistory = tbPasswordHistoryRepository.save(tbPasswordHistory);
        return tbPasswordHistoryMapper.toDto(tbPasswordHistory);
    }

    @Override
    public TbPasswordHistoryDTO update(TbPasswordHistoryDTO tbPasswordHistoryDTO) {
        LOG.debug("Request to update TbPasswordHistory : {}", tbPasswordHistoryDTO);
        TbPasswordHistory tbPasswordHistory = tbPasswordHistoryMapper.toEntity(tbPasswordHistoryDTO);
        tbPasswordHistory = tbPasswordHistoryRepository.save(tbPasswordHistory);
        return tbPasswordHistoryMapper.toDto(tbPasswordHistory);
    }

    @Override
    public Optional<TbPasswordHistoryDTO> partialUpdate(TbPasswordHistoryDTO tbPasswordHistoryDTO) {
        LOG.debug("Request to partially update TbPasswordHistory : {}", tbPasswordHistoryDTO);

        return tbPasswordHistoryRepository
            .findById(tbPasswordHistoryDTO.getId())
            .map(existingTbPasswordHistory -> {
                tbPasswordHistoryMapper.partialUpdate(existingTbPasswordHistory, tbPasswordHistoryDTO);

                return existingTbPasswordHistory;
            })
            .map(tbPasswordHistoryRepository::save)
            .map(tbPasswordHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TbPasswordHistoryDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all TbPasswordHistories");
        return tbPasswordHistoryRepository.findAll(pageable).map(tbPasswordHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TbPasswordHistoryDTO> findOne(Long id) {
        LOG.debug("Request to get TbPasswordHistory : {}", id);
        return tbPasswordHistoryRepository.findById(id).map(tbPasswordHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete TbPasswordHistory : {}", id);
        tbPasswordHistoryRepository.deleteById(id);
    }
}
