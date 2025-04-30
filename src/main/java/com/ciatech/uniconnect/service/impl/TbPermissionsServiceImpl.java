package com.ciatech.uniconnect.service.impl;

import com.ciatech.uniconnect.domain.TbPermissions;
import com.ciatech.uniconnect.repository.TbPermissionsRepository;
import com.ciatech.uniconnect.service.TbPermissionsService;
import com.ciatech.uniconnect.service.dto.TbPermissionsDTO;
import com.ciatech.uniconnect.service.mapper.TbPermissionsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ciatech.uniconnect.domain.TbPermissions}.
 */
@Service
@Transactional
public class TbPermissionsServiceImpl implements TbPermissionsService {

    private static final Logger LOG = LoggerFactory.getLogger(TbPermissionsServiceImpl.class);

    private final TbPermissionsRepository tbPermissionsRepository;

    private final TbPermissionsMapper tbPermissionsMapper;

    public TbPermissionsServiceImpl(TbPermissionsRepository tbPermissionsRepository, TbPermissionsMapper tbPermissionsMapper) {
        this.tbPermissionsRepository = tbPermissionsRepository;
        this.tbPermissionsMapper = tbPermissionsMapper;
    }

    @Override
    public TbPermissionsDTO save(TbPermissionsDTO tbPermissionsDTO) {
        LOG.debug("Request to save TbPermissions : {}", tbPermissionsDTO);
        TbPermissions tbPermissions = tbPermissionsMapper.toEntity(tbPermissionsDTO);
        tbPermissions = tbPermissionsRepository.save(tbPermissions);
        return tbPermissionsMapper.toDto(tbPermissions);
    }

    @Override
    public TbPermissionsDTO update(TbPermissionsDTO tbPermissionsDTO) {
        LOG.debug("Request to update TbPermissions : {}", tbPermissionsDTO);
        TbPermissions tbPermissions = tbPermissionsMapper.toEntity(tbPermissionsDTO);
        tbPermissions = tbPermissionsRepository.save(tbPermissions);
        return tbPermissionsMapper.toDto(tbPermissions);
    }

    @Override
    public Optional<TbPermissionsDTO> partialUpdate(TbPermissionsDTO tbPermissionsDTO) {
        LOG.debug("Request to partially update TbPermissions : {}", tbPermissionsDTO);

        return tbPermissionsRepository
            .findById(tbPermissionsDTO.getId())
            .map(existingTbPermissions -> {
                tbPermissionsMapper.partialUpdate(existingTbPermissions, tbPermissionsDTO);

                return existingTbPermissions;
            })
            .map(tbPermissionsRepository::save)
            .map(tbPermissionsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TbPermissionsDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all TbPermissions");
        return tbPermissionsRepository.findAll(pageable).map(tbPermissionsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TbPermissionsDTO> findOne(Long id) {
        LOG.debug("Request to get TbPermissions : {}", id);
        return tbPermissionsRepository.findById(id).map(tbPermissionsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete TbPermissions : {}", id);
        tbPermissionsRepository.deleteById(id);
    }
}
